package grader.documents;


import grader.project.AClassesManager;
import grader.project.AProject;
import grader.project.ClassesManager;
import grader.project.Project;
import grader.project.source.AClassesTextManager;
import grader.project.source.ClassesTextManager;
import grader.project.view.AClassViewManager;

import java.io.IOException;

import util.misc.Common;

public class AWordDocumentDisplayer implements DocumentDisplayer {
	public static final String WORD_14 = "C:\\Program Files\\Microsoft Office\\Office14\\WINWORD";
	public static final String WORD_12 = "C:\\Program Files\\Microsoft Office\\Office12\\WINWORD";
	public static final int DEFAULT_WORD_NO = 14;
	public  String wordPath;
//	ClassesTextManager classesSourcesManager;
	int officeNo = 14;
	public AWordDocumentDisplayer() {
//		classesSourcesManager = aClassesSourcesManager;
		setWordPath();
	}
	public AWordDocumentDisplayer( int anOfficeNo) {
//		classesSourcesManager = aClassesSourcesManager;
		officeNo = anOfficeNo;
		setWordPath();
	}
	
	void setWordPath() {
//		wordPath = "C:\\Program Files\\Microsoft Office\\Office" + officeNo + "\\WINWORD";
		wordPath = "C:/Program Files/Microsoft Office/Office" + officeNo + "/WINWORD";

	}
	
	public void displayFile(String aFileName) {
		 String windowsName= Common.toWindowsFileName(aFileName);

		String[] command = {wordPath, windowsName};
		Common.exec(command);
	}
	
	
//	public  void displayAllSources() {
//		String[] command = {wordPath, AClassesTextManager.SOURCE_FILE_NAME};
//		Common.exec(command);
////		Common.exec(wordPath + " " + AClassesTextManager.SOURCE_FILE_NAME);
//		
//	}

//	public static void main (String[] args) {
////		Project project = new AProject();
////		ClassesManager classesManager = new AClassesManager();
////		classesManager.makeClassDescriptions(project.getProjectFolder(), true);
//		ClassesManager classesManager = new AClassesManager();		
//		classesManager.makeClassDescriptions(SourceCodeExtractor.PROJECT_DIRECTORY, true);		
//		ClassesTextManager classesSourceManager = new AClassesTextManager(new AClassViewManager(classesManager));
//		classesSourceManager.initializeAllSourcesText();
////		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
////		classesSourceManager.initializeAllSourcesText();
////		classesSourceManager.writeAllSourcesText();	
//		WordSourceCodeDisplayer wordSourceCodeDisplayer = new AWordSourceCodeDisplayer(classesSourceManager);
//		wordSourceCodeDisplayer.displayAllSources();
////		try {
////			Process proc = Runtime.getRuntime().exec(WORD_14 + " " + AClassesSourceManager.SOURCE_FILE_NAME);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//	}
	
	public static void main (String[] args) {
		Project project = new AProject(AProject.PROJECT_DIRECTORY);
//		ClassesManager classesManager = new AClassesManager();
//		classesManager.makeClassDescriptions(project.getProjectFolder(), true);
		
//		ClassesManager classesManager = new AClassesManager();		
//		classesManager.makeClassDescriptions(SourceCodeExtractor.PROJECT_DIRECTORY, true);		
//		ClassesTextManager classesSourceManager = new AClassesTextManager(new AClassViewManager(classesManager));
//		classesSourceManager.initializeAllSourcesText();
//		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
//		classesSourceManager.initializeAllSourcesText();
//		classesSourceManager.writeAllSourcesText();	
//		WordSourceCodeDisplayer wordSourceCodeDisplayer = new AWordSourceCodeDisplayer(project.getClassesTextManager());
//		wordSourceCodeDisplayer.displayAllSources();
//		try {
//			Process proc = Runtime.getRuntime().exec(WORD_14 + " " + AClassesSourceManager.SOURCE_FILE_NAME);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	

}
