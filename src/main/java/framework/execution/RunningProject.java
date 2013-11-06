package framework.execution;

import java.util.concurrent.Semaphore;

/**
 * This is a wrapper for a running project independent of the method of execution.
 * This provides support for synchronization via semaphores and output manipulation.
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

    public void appendOutput(String output) {
        this.output += output;
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
