package framework.execution;

import framework.project.Project;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/6/13
 * Time: 8:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestartingProcess extends RunningProcess {

    private static final int RestartTime = 250;

    public RestartingProcess(Project project) throws NotRunnableException {
        super(project);
    }

    public void ensureRunningState() throws NotRunnableException {
        if (hasEnded()) {
            try {
                process = builder.start();
                processOut = new Scanner(process.getInputStream());
                processErr = new Scanner(process.getErrorStream());
                processIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                startOutputWatcher();
                Thread.sleep(RestartTime);
            } catch (IOException e) {
                throw new NotRunnableException();
            } catch (InterruptedException e) {
                throw new NotRunnableException();
            }
        }
    }

    @Override
    public void writeLine(String input) throws NotRunnableException {
        ensureRunningState();
        super.writeLine(input);
    }
}
