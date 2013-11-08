package framework.execution;

/**
 * The interface for different runners to use
 */
public interface Runner {

    public RunningProject run(String input) throws NotRunnableException;

    public RunningProject run(String input, int timeout) throws NotRunnableException;

    public RunningProject run(String input, String[] args, int timeout) throws NotRunnableException;

}
