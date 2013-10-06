package framework.execution;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import utils.GradingEnvironment;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class RunningProcess {

    protected ProcessBuilder builder;
    protected Process process;
    protected Scanner processOut;
    protected Scanner processErr;
    protected BufferedWriter processIn;

    private String output;

    public RunningProcess(Project project) throws NotRunnableException {
        try {

            // Setup the process builder
            String entryPoint = getEntryPoint(project);
            builder = new ProcessBuilder("java", "-cp", GradingEnvironment.get().getClasspath(), entryPoint);
            builder.directory(project.getBuildFolder(entryPoint));

            // Start the process
            process = builder.start();
            processOut = new Scanner(process.getInputStream());
            processErr = new Scanner(process.getErrorStream());
            processIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            // Handle output
            output = "";
            startOutputWatcher();

            // TODO: Handle errors
        } catch (Exception e) {
            throw new NotRunnableException();
        }
    }

    protected void startOutputWatcher() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (processOut.hasNextLine())
                    output += (output.isEmpty() ? "" : "\n") + processOut.nextLine();
            }
        }).start();
    }

    private String getEntryPoint(Project project) throws NotRunnableException {
        if (project.getClassesManager().isEmpty())
            throw new NotRunnableException();

        ClassesManager manager = (ClassesManager) project.getClassesManager().get();
        for (ClassDescription description : manager.getClassDescriptions()) {
            try {
                description.getJavaClass().getMethod("main", String[].class);
                return description.getJavaClass().getCanonicalName();
            } catch (NoSuchMethodException e) {
            }
        }
        throw new NotRunnableException();
    }

    public boolean hasEnded() {
        try {
            process.exitValue();
            return true;
        } catch (IllegalThreadStateException e) {
            return false;
        }
    }

    public void writeLine(String input) throws NotRunnableException {
        try {
            processIn.write(input);
            processIn.newLine();
            processIn.flush();
        } catch (IOException e) {
            System.out.println("Error writing to process");
            throw new NotRunnableException();
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String read() {
        String out = output;
        output = "";
        return out;
    }

    public void stop() {
        process.destroy();
    }

}
