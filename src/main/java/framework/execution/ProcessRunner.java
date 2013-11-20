package framework.execution;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import framework.utils.GradingEnvironment;
import tools.TimedProcess;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * This runs the program in a new process.
 */
public class ProcessRunner implements Runner {

    private String entryPoint;
    private File folder;

    public ProcessRunner(Project project) throws NotRunnableException {
        try {
            entryPoint = getEntryPoint(project);
            folder = project.getBuildFolder(entryPoint);
        } catch (Exception e) {
            throw new NotRunnableException();
        }
    }

    /**
     * This figures out what class is the "entry point", or, what class has main(args)
     *
     * @param project The project to run
     * @return The class canonical name. i.e. "foo.bar.SomeClass"
     * @throws NotRunnableException
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
                // Move along
            }
        }
        throw new NotRunnableException();
    }

    /**
     * This runs the project with no arguments
     *
     * @param input The input to use as standard in for the process
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws NotRunnableException
     */
    @Override
    public RunningProject run(String input) throws NotRunnableException {
        return run(input, -1);
    }

    /**
     * This runs the project with no arguments with a timeout
     *
     * @param input   The input to use as standard in for the process
     * @param timeout The timeout, in seconds. Set to -1 for no timeout
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws NotRunnableException
     */
    @Override
    public RunningProject run(String input, int timeout) throws NotRunnableException {
        return run(input, new String[]{}, timeout);
    }

    /**
     * This runs the project providing input and arguments
     *
     * @param input   The input to use as standard in for the process
     * @param args    The arguments to pass in
     * @param timeout The timeout, in seconds. Set to -1 for no timeout
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws NotRunnableException
     */
    @Override
    public RunningProject run(String input, String[] args, int timeout) throws NotRunnableException {
        final RunningProject runner = new RunningProject();

        try {
            runner.start();

            // Prepare to run the process
            ProcessBuilder builder = new ProcessBuilder("java", "-cp", GradingEnvironment.get().getClasspath(), entryPoint);
            builder.directory(folder);
            System.out.println("Running process: java -cp \"" + GradingEnvironment.get().getClasspath() + "\" " + entryPoint);
            System.out.println("Running in folder: " + folder.getAbsolutePath());

            // Start the process
            TimedProcess process = new TimedProcess(builder, timeout);
            process.start();

            // Print output to the console
            InputStream processOut = process.getInputStream();
            final Scanner scanner = new Scanner(processOut);
            final Semaphore outputSemaphore = new Semaphore(1);
            outputSemaphore.acquire();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        System.out.println(line);
                        runner.appendOutput(line);
                    }
                    outputSemaphore.release();
                }
            }).start();

            // Write to the process
            OutputStreamWriter processIn = new OutputStreamWriter(process.getOutputStream());
            processIn.write(input);
            processIn.flush();

            // Wait for it to finish
            try {
                process.waitFor();
            } catch (Exception e) {
                runner.appendOutput("*** TIMEOUT ***");
                outputSemaphore.release();
                System.out.println("*** Timed out waiting for process to finish ***");
            }

            // Wait for the output to finish
            outputSemaphore.acquire();
            runner.end();

        } catch (Exception e) {
            runner.error();
            runner.end();
        }
        return runner;
    }
}
