package grader.project;

import java.util.List;

import com.thoughtworks.qdox.JavaDocBuilder;

import grader.project.file.RootCodeFolder;
import grader.project.source.ClassesTextManager;
import grader.project.view.ClassViewManager;
import grader.sakai.project.SakaiProjectDatabase;

public interface Project {
	
	public ClassesManager getClassesManager() ;
	public void setClassesManager(ProxyBasedClassesManager aClassesManager) ;
	public ClassViewManager getClassViewManager() ;
	public void setClassViewManager(ClassViewManager aClassViewManager) ;
	public ClassesTextManager getClassesTextManager() ;
	public void setClassesTextManager(ClassesTextManager aClassesTextManager) ;
	
	public void setProjectFolder(String aProjectFolder) ;
	public String getProjectFolderName() ;
	public String getSourceProjectFolderName() ;
//	public String getMixedCaseSourceProjectFolderName() ;
	public String getBinaryProjectFolderName() ;
	public List<Class> getImplicitlyLoadedClasses();


	
	public RootCodeFolder getRootCodeFolder() ;
	public ProxyClassLoader getClassLoader();
//	public  Thread run (String mainClassName, String[] args);
	public  Thread run(String mainClassName, String[][] args, String[] anInputFiles, String[] anOutputFiles);

	String getOutputFolder();
	void setOutputFolder(String outputFolder);
	public void maybeMakeClassDescriptions();
	
	
	public void setHasBeenRun(boolean newVal) ;
	public void setCanBeRun(boolean newVal);
	public boolean canBeRun();
	public boolean hasBeenRun();
//	public void displaySource(SakaiProjectDatabase aSakaiProjectDatabase);
//	public void displayOutputInWord();
	public String getOutputFileName();
	String getSourceFileName();
	boolean runChecked();
//	boolean setRunParameters(String aMainClassName, String[] anArgs,
//			String anInputFile, String anOutputFile);
	public boolean setRunParameters(String aMainClassName, String anArgs[][], String[] anInputFiles, String[] anOutputFiles, MainClassFinder aMainClassFinder);

	Thread runProject();
	JavaDocBuilder getJavaDocBuilder();






}
