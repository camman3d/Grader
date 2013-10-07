package framework.execution;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Runner {

    public RunningProject run(String input) throws NotRunnableException;

    public RunningProject run(String input, String[] args) throws NotRunnableException;

}
