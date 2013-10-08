package grader.project;

import java.lang.reflect.Method;

public class AReflectionBasedProjectRunnerFactory implements ProjectRunnerFactory{

	@Override
	public Runnable createProjectRunner(String aMainClassName,
			String[][] aMainArgs, Project aProject, String[] anInputFiles,
			String[] anOutputFiles, Class aMainClass, Method aMainMethod) {
		return new AReflectionBasedProjectRunner(aMainClassName, aMainArgs, aProject, anInputFiles, anOutputFiles, aMainClass, aMainMethod);
	}

}
