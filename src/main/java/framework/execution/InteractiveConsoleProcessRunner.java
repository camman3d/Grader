package framework.execution;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import framework.utils.GradingEnvironment;
import tools.TimedProcess;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * This runs the program in a new process.
 */
public class InteractiveConsoleProcessRunner implements Runner {

    private String entryPoint;
    private File folder;

    public InteractiveConsoleProcessRunner(Project project) throws NotRunnableException {
        try {
            entryPoint = getEntryPoint(project);
            folder = project.getBuildFolder(entryPoint);
        } catch (Exception e) {
            throw new NotRunnableException();
        }
    }

    /**
     * This figures out what class is the "entry point", or, what class has main(args)
     * @param project The project to run
     * @return The class canonical name. i.e. "foo.bar.SomeClass"
     * @throws framework.execution.NotRunnableException
     */
    private String getEntryPoint(Project project) throws NotRunnableException {
        if (project.getClassesManager().isEmpty())
            throw new NotRunnableException();

        ClassesManager manager = project.getClassesManager().get();
        for (ClassDescription description : manager.getClassDescriptions()) {
            try {
                description.getJavaClass().getMethod("main", String[].class);
                return description.getJavaClass().getCanonicalName();
            } catch (NoSuchMethodException e) {
            }
        }
        throw new NotRunnableException();
    }

    /**
     * This runs the project with no arguments
     * @param input The input is ignored.
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws framework.execution.NotRunnableException
     */
    @Override
    public RunningProject run(String input) throws NotRunnableException {
        return run(input, -1);
    }

    /**
     * This runs the project with no arguments with a timeout
     * @param input The input is ignored.
     * @param timeout The timeout, in seconds. Set to -1 for no timeout
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws framework.execution.NotRunnableException
     */
    @Override
    public RunningProject run(String input, int timeout) throws NotRunnableException {
        return run(input, new String[]{}, timeout);
    }

    /**
     * This runs the project providing input and arguments
     * @param input The input string is ignored. Instead the console input is used.
     * @param args The arguments to pass in
     * @param timeout The timeout, in seconds. Set to -1 for no timeout
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws framework.execution.NotRunnableException
     */
    @Override
    public RunningProject run(String input, String[] args, int timeout) throws NotRunnableException {
        final RunningProject runner = new RunningProject();

        try {
//            runner.start();

            // Prepare to run the process
            ProcessBuilder builder = new ProcessBuilder("java", "-cp", GradingEnvironment.get().getClasspath(), entryPoint);
            builder.directory(folder);

            // Start the process
            final TimedProcess process = new TimedProcess(builder, timeout);
            process.start();

            // Print output to the console
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            final BufferedReader br = new BufferedReader(isr);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String line = null;
                        while ((line = br.readLine()) != null)
                            System.out.println(line);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }).start();

            // Feed console input to the process
            OutputStreamWriter osw = new OutputStreamWriter(process.getOutputStream());
            final BufferedWriter bw = new BufferedWriter(osw);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean loop = true;
                    Scanner scanner = new Scanner(System.in);
                    while (loop) {
                        try {
                            process.getProcess().exitValue();
                            loop = false;
                        } catch (IllegalThreadStateException e) {

                            try {
                                if (scanner.hasNextLine()) {
                                    bw.write(scanner.nextLine());
                                    bw.newLine();
                                    bw.flush();
                                }
                                Thread.sleep(50);
                            } catch (Exception e1) {
                                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
//            runner.error();
//            runner.end();
        }
        return runner;
    }
}
