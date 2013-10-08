package grader.project.source;

import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.AProxyBasedClassesManager;
import grader.project.ClassesManager;
import grader.project.view.AClassViewManager;

public class ProxyBasedSourceCodeExtractor {


	public static void main (String[] args) {
		ClassesManager classesManager = new AProxyBasedClassesManager();		
		classesManager.makeClassDescriptions(AProject.PROJECT_DIRECTORY, true);		
		ClassesTextManager classesSourceManager = new AClassesTextManager(new AClassViewManager(classesManager));
		classesSourceManager.initializeAllSourcesText();

		
	}
}
