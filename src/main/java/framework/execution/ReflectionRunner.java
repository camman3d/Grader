package framework.execution;

import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import util.misc.TeePrintStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Runs a project in the same JVM using reflection to invoke the main method.
 * @see {@link grader.project.AReflectionBasedProjectRunner}
 */
public class ReflectionRunner implements Runner {

    private Project project;

    public ReflectionRunner(Project project) {
        this.project = project;
    }

    /**
     * Runs the project with no arguments
     * @param input The input to provide
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws NotRunnableException
     */
    @Override
    public RunningProject run(String input) throws NotRunnableException {
        return run(input, new String[]{}, -1);
    }

    @Override
    public RunningProject run(String input, int timeout) throws NotRunnableException {
        // TODO: Add timeout
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This runs the project with no arguments
     * @param input Input to use as System.in
     * @param args Arguments for the program
     * @return A RunningProject object which you can use for synchronization and acquiring output
     * @throws NotRunnableException
     */
    @Override
    public RunningProject run(final String input, final String[] args, int timeout) throws NotRunnableException {
        final RunningProject runner = new RunningProject();
        try {
            runner.start();
        } catch (InterruptedException e) {
            throw new NotRunnableException();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream systemIn = System.in;
                PrintStream systemOut = new PrintStream(System.out);

                try {

                    // Create the input/output streams
                    InputStream stdin = new ByteArrayInputStream(input.getBytes());
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    PrintStream stdout = new TeePrintStream(outputStream, System.out);
                    System.setIn(stdin);
                    System.setOut(stdout);

                    // Get the main method and invoke it
                    Class mainClass = getMainClass();
                    Object[] mainArgs = new Object[]{args};
                    Method mainMethod = getMainMethod(mainClass);
                    mainMethod.invoke(mainClass, mainArgs);

                    // Close the Tee stream
                    stdin.close();
                    stdout.close();

                    // Do something with the output
                    runner.setOutput(outputStream.toString());
                    runner.end();
                } catch (Exception e) {
                    runner.error();
                    runner.end();
                } finally {
                    System.setIn(systemIn);
                    System.setOut(systemOut);
                }
            }
        }).start();
        return runner;
    }

    /**
     * Finds which class has the main method. This finds the first class with main
     * @return The Class containing main
     * @throws NotRunnableException
     */
    private Class<?> getMainClass() throws NotRunnableException {
        if (project.getClassesManager().isDefined()) {
            ClassesManager manager = (ClassesManager) project.getClassesManager().get();
            for (ClassDescription classDescription : manager.getClassDescriptions()) {
                Class<?> _class = classDescription.getJavaClass();
                try {
                    Method method = _class.getMethod("main", String[].class);
                    if (Modifier.isStatic(method.getModifiers()))
                        return _class;
                } catch (NoSuchMethodException e) {
                }
            }
            throw new NotRunnableException();
        } else
            throw new NotRunnableException();
    }

    /**
     * This gets the main method giving a class. This also transforms the exception to a NotRunnableException
     * @param mainClass The class containing main
     * @return The main {@link Method}
     * @throws NotRunnableException
     */
    private Method getMainMethod(Class<?> mainClass) throws NotRunnableException {
        try {
            return mainClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            throw new NotRunnableException();
        }
    }

}
