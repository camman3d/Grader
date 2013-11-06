package framework.execution;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import framework.utils.GradingEnvironment;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessRunner implements Runner {

//    protected ProcessBuilder builder;
//    protected Process process;
//    protected Scanner processOut;
//    protected Scanner processErr;
//    protected BufferedWriter processIn;

    private String entryPoint;
    private File folder;
    private boolean finished;

    public ProcessRunner(Project project) throws NotRunnableException {
        try {
            entryPoint = getEntryPoint(project);
            folder = project.getBuildFolder(entryPoint);
        } catch (Exception e) {
            throw new NotRunnableException();
        }
    }

//    protected void startOutputWatcher() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (processOut.hasNextLine())
//                    output += (output.isEmpty() ? "" : "\n") + processOut.nextLine();
//            }
//        }).start();
//    }

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

//    public boolean hasEnded() {
//        try {
//            process.exitValue();
//            return true;
//        } catch (IllegalThreadStateException e) {
//            return false;
//        }
//    }

//    public void writeLine(String input) throws NotRunnableException {
//        try {
//            processIn.write(input);
//            processIn.newLine();
//            processIn.flush();
//        } catch (IOException e) {
//            System.out.println("Error writing to process");
//            throw new NotRunnableException();
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }

//    public String read() {
//        String out = output;
//        output = "";
//        return out;
//    }

//    public void stop() {
//        process.destroy();
//    }

    @Override
    public RunningProject run(String input) throws NotRunnableException {
        return run(input, new String[]{});
    }

    @Override
    public RunningProject run(String input, String[] args) throws NotRunnableException {
        final RunningProject runner = new RunningProject();

        try {
            runner.start();

            // Prepare to run the process
            ProcessBuilder builder = new ProcessBuilder("java", "-cp", GradingEnvironment.get().getClasspath(), entryPoint);
            builder.directory(folder);

            // Start the process
            Process process = builder.start();

            // Print output to the console
            final InputStream processOut = process.getInputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(processOut);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        System.out.println(line);
                        runner.appendOutput(line);
                    }
                }
            }).start();

            // Write to the process
            OutputStreamWriter processIn = new OutputStreamWriter(process.getOutputStream());
            processIn.write(input);

            // Wait for it to finish
            // TODO: Add a possible timeout
            process.waitFor();

            // Write the output
//            StringWriter writer = new StringWriter();
//            IOUtils.copy(process.getInputStream(), writer);
//            runner.setOutput(writer.toString());
            runner.end();

        } catch (Exception e) {
            runner.error();
            runner.end();
        }
        return runner;
    }
}
