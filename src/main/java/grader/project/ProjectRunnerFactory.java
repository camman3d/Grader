package grader.project;

import java.lang.reflect.Method;

public interface ProjectRunnerFactory {

    public Runnable createProjectRunner(String aMainClassName, String[][] aMainArgs, Project aProject, String[] anInputFiles, String[] anOutputFiles, Class aMainClass, Method aMainMethod);

}
