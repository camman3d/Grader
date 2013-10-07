package framework.execution;

import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RunningProject {

    private Semaphore runningState = new Semaphore(1);
    private String output;
    private NotRunnableException exception;

    public RunningProject() {
        exception = null;
        output = null;
    }

    public void start() throws InterruptedException {
        runningState.acquire();
    }

    public void end() {
        runningState.release();
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void error() {
        this.exception = new NotRunnableException();
    }

    public String await() throws NotRunnableException {
        if (exception != null)
            throw exception;
        try {
            runningState.acquire();
        } catch (InterruptedException e) {
            throw new NotRunnableException();
        }
        return output;
    }

}
