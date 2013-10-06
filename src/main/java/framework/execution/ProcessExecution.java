package framework.execution;

import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessExecution {

    private static final int DefaultTimeout = 50;
    private RestartingProcess process;

    public ProcessExecution(Project project) throws NotRunnableException {
        process = new RestartingProcess(project);
    }

    public String clearOutput() {
        return process.read();
    }

    public String processInputLine(String input) throws NotRunnableException {
        process.writeLine(input);
        try {
            Thread.sleep(DefaultTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return process.read();
    }

    public void stop() {
        process.stop();
    }

}
