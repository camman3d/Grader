package grader.project.source;

import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.ClassesManager;
import grader.project.Project;
import grader.project.view.AClassViewManager;

public class SourceCodeExtractor {
public static final boolean SEPARATE_SRC_BIN = true;
	public static final int ESTIMATED_SOURCES_LENGTH = 20;
//	public static List<SourceAndObjectClassDescription> makeSourceCodeDescriptions(String aProjectDirectory, boolean aSeparateSrcBin) {
//		String anActualProjectDirectory = aProjectDirectory;
//		if (aSeparateSrcBin)
//			anActualProjectDirectory += "/src";
//		List<SourceAndObjectClassDescription> sources = new ArrayList(ESTIMATED_SOURCES_LENGTH);
//		File projectFoider = new File(anActualProjectDirectory);
//		makeClassDescriptions(sources, projectFoider, projectFoider);
//
//		return sources;
//	}
//	public static void makeClassDescriptions( List<SourceAndObjectClassDescription> aSources, File aFolder, File aProjectFolder) {
//		String[] fileNames = aFolder.list();
//		File[] files = aFolder.listFiles();
//		for (File aFile:files) {
//			if (aFile.getName().endsWith(".java")) {
//				String relativeName = Common.getRelativeName(aProjectFolder.getAbsolutePath(), aFile.getAbsolutePath());
//				String className = Common.toClassName(relativeName);
//				StringBuffer text = Common.toText(aFile.getAbsolutePath());
//				SourceAndObjectClassDescription classDescription = new ASourceAndObjectClassDescription(className, text);
//				aSources.add(classDescription);				
//			}
//	    }
//
//		
//		for (File aFile:files) {
//				if (aFile.isDirectory()) {
//					makeClassDescriptions(aSources, aFile, aProjectFolder);
//			    }
//		}
//
//	}

	public static void main (String[] args) {
//		Project project = new AProject(PROJECT_DIRECTORY);
		ClassesManager classesManager = new AClassesManager();		
		classesManager.makeClassDescriptions(AProject.PROJECT_DIRECTORY, true);		
		ClassesTextManager classesSourceManager = new AClassesTextManager(new AClassViewManager(classesManager));
		classesSourceManager.initializeAllSourcesText();

		
	}
}
