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
 * Like AReflectionBasedProjectRunner
 */
public class ReflectionRunner {

    private Project project;

    public ReflectionRunner(Project project) {
        this.project = project;
    }

    public void run(String input) throws NotRunnableException {
        run(input, new String[]{});
    }

    public void run(final String input, final String[] args) throws NotRunnableException {
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

                    // TODO: Do something with the output
//                    return outputStream.toString();
                } catch (Exception e) {
                    e.printStackTrace();
//                    throw new NotRunnableException();
                } finally {
                    System.setIn(systemIn);
                    System.setOut(systemOut);
                }
            }
        }).start();
    }

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

    private Method getMainMethod(Class<?> mainClass) throws NotRunnableException {
        try {
            return mainClass.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            throw new NotRunnableException();
        }
    }

}
