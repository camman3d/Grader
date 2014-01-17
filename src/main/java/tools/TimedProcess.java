package tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.*;

/**
 * This is a wrapper around a normal process so that we can have its execution time out.
 */
public class TimedProcess {

    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    private Process process;
    private ProcessBuilder processBuilder;
    private long timeout;

    public TimedProcess(ProcessBuilder builder, long timeout) {
        processBuilder = builder;
        this.timeout = timeout;
    }

    public Process start() throws IOException {
        process = processBuilder.start();
        return process;
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }

    public Process getProcess() {
        return process;
    }

    public int waitFor() throws InterruptedException, ExecutionException, TimeoutException {
        // Don't timeout if the timeout is -1
        if (timeout == -1)
            return process.waitFor();

        return timedCall(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return process.waitFor();
            }
        }, timeout, TimeUnit.SECONDS);
    }
    private static <T> T timedCall(Callable<T> c, long timeout, TimeUnit timeUnit)
            throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<T> task = new FutureTask<T>(c);
        THREAD_POOL.execute(task);
        return task.get(timeout, timeUnit);
    }

}
