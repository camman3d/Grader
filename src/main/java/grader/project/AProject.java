package grader.project;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.qdox.JavaDocBuilder;

import grader.file.RootFolderProxy;
import grader.file.filesystem.AFileSystemRootFolderProxy;
import grader.file.zipfile.AZippedRootFolderProxy;
import grader.project.file.RootCodeFolder;
import grader.project.file.java.AJavaRootCodeFolder;
import grader.project.source.AClassesTextManager;
import grader.project.source.ClassesTextManager;
import grader.project.view.AClassViewManager;
import grader.project.view.ClassViewManager;
import grader.sakai.StudentCodingAssignment;

public class AProject implements Project {
	public static final String ZIP_SUFFIX = ".zip";
	public static final String DEFAULT_PROJECT_FOLDER = ".";
	public static final String DEFAULT_GRADING_FOLDER = "C:/Users/dewan/Downloads/GraderData";
	public static final String DEFAULT_OUTPUT_FILE_PREFIX = "output";
	public static final String DEFAULT_OUTPUT_FILE_SUFFIX = ".txt";
	


	public static final String DEFAULT_OUTPUT_FILE_NAME = DEFAULT_OUTPUT_FILE_PREFIX + DEFAULT_OUTPUT_FILE_SUFFIX;


	String projectFolderName = DEFAULT_PROJECT_FOLDER;
	
	String gradingProjectFolderName = DEFAULT_GRADING_FOLDER;
	
	ProxyBasedClassesManager classesManager;
	ClassViewManager classViewManager;
	ClassesTextManager classesTextManager;
	boolean zippedFolder;
	RootCodeFolder rootCodeFolder;
	RootFolderProxy rootFolder;
	ProxyClassLoader proxyClassLoader;
	ClassLoader oldClassLoader;
	String outputFolder = ".";
	String sourceFileName, outputFileName;
	String sourceSuffix = ClassesTextManager.DEFAULT_SOURCES_FILE_SUFFIX;
	String outputSuffix = DEFAULT_OUTPUT_FILE_SUFFIX;
	boolean hasBeenRun, canBeRun = true;
	 JavaDocBuilder javaDocBuilder;
	 MainClassFinder mainClassFinder;

	

		//	public static final String PROJECT_DIRECTORY = ".";
	//	public static final String PROJECT_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated/Final";
		public static final String PROJECT_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated/Final";
		public static final String PROJECT_ZIPPED_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";

		//	public static final String PROJECT_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";
	public AProject(String aProjectFolder, String anOutputFolder, boolean aZippedFolder) {
		init(aProjectFolder, anOutputFolder, aZippedFolder);
		
	}
	public AProject (StudentCodingAssignment aStudentCodingAssignment) {
		init(aStudentCodingAssignment.getProjectFolder(), aStudentCodingAssignment.getFeedbackFolder().getAbsoluteName());
	}
	public AProject (StudentCodingAssignment aStudentCodingAssignment, String aSourceSuffix, String anOutputSuffix) {
		sourceSuffix = aSourceSuffix;
		outputSuffix = anOutputSuffix;
		init(aStudentCodingAssignment.getProjectFolder(), aStudentCodingAssignment.getFeedbackFolder().getAbsoluteName());
	}	
	
	public String toString() {
		return "(" + projectFolderName + "," + outputFolder + ")";
	}
//	public AProject (RootFolderProxy aRootFolder, String anOutputFolder) {
//		init(aRootFolder, anOutputFolder);
//	}
	public AProject (RootFolderProxy aRootFolder, String anOutputFolder) {
		init(aRootFolder, anOutputFolder);
	}
	@Override
	public String getOutputFolder() {
		return outputFolder;
	}
	@Override
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
//	protected DocumentDisplayer wordSourceCodeDisplayer = new AWordDocumentDisplayer(); ;

//	public void displaySource(SakaiProjectDatabase aSakaiProjectDatabase) {
//		maybeMakeClassDescriptions();
////		 DocumentDisplayerRegistry.display(sourceFileName);
//		aSakaiProjectDatabase.getSourceDisplayer().displaySource(null);
//
//		
//	}
	
//	public void displayOutputInWord() {
//		maybeMakeClassDescriptions();
//		 System.out.println("Displaying output from:" + outputFileName);
//		 String windowsName= Common.toWindowsFileName(outputFileName);
////		 Common.toCanonicalFileName(aFileName);
//
//		wordSourceCodeDisplayer.displayFile(windowsName);
////		wordSourceCodeDisplayer.displayAllSources();
//		
//	}
	
	protected MainClassFinder createMainClassFinder() {
		return new AMainClassFinder();
	}
	
	public void init(String aProjectFolder, String anOutputFolder, boolean aZippedFolder) {
//		projectFolderName = aProjectFolder;
//		outputFolder = anOutputFolder;
		
//		zippedFolder = aZippedFolder;

		if (aZippedFolder) {
			rootFolder = new AZippedRootFolderProxy(aProjectFolder);
		} else {
			rootFolder = new AFileSystemRootFolderProxy(aProjectFolder);
		}
		init (rootFolder,  anOutputFolder);
		
//		rootCodeFolder = new AJavaRootCodeFolder(rootFolder, separateSrcBin);
////		 classesManager = new AClassesManager();
//			 classesManager = new AProxyBasedClassesManager();	
//			 proxyClassLoader = new AProxyProjectClassLoader(rootCodeFolder);
//			 oldClassLoader = Thread.currentThread().getContextClassLoader();
//				Thread.currentThread().setContextClassLoader((ClassLoader) proxyClassLoader);
//
//		classesManager.makeClassDescriptions(this);
//
//		 classViewManager = new AClassViewManager(classesManager);
////		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
//		 classesTextManager = new AClassesTextManager(classViewManager);
//
//		 classesTextManager.initializeAllSourcesText();
//		 classesTextManager.writeAllSourcesText(outputFolder  + "/" + classesTextManager.SOURCES_FILE_NAME);		
		
	}
	
	public void init(RootFolderProxy aRootFolder, String anOutputFolder) {
		rootFolder = aRootFolder;
		projectFolderName = aRootFolder.getAbsoluteName();
		outputFolder = anOutputFolder;
		
//		zippedFolder = aZippedFolder;

		rootCodeFolder = new AJavaRootCodeFolder(rootFolder);
		if (rootCodeFolder.hasValidBinaryFolder())
		 proxyClassLoader = new AProxyProjectClassLoader(rootCodeFolder);
//		 sourceFileName = outputFolder  + "/" + classesTextManager.SOURCES_FILE_NAME;	
//		 outputFileName = outputFolder  + "/" + DEFAULT_OUTPUT_FILE_NAME;
		 
		 sourceFileName = createFullSourceFileName();	
		 outputFileName = createFullOutputFileName();
		 

//		 classesManager = new AClassesManager();
			
			 classesManager = new AProxyBasedClassesManager();
			 mainClassFinder = createMainClassFinder();
//			 oldClassLoader = Thread.currentThread().getContextClassLoader();
//				Thread.currentThread().setContextClassLoader((ClassLoader) proxyClassLoader);

//		classesManager.makeClassDescriptions(this);

//		 classViewManager = new AClassViewManager(classesManager);
////		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
//		 classesTextManager = new AClassesTextManager(classViewManager);
//
//		 classesTextManager.initializeAllSourcesText();
//		 sourceFileName = outputFolder  + "/" + classesTextManager.SOURCES_FILE_NAME;	
//		 classesTextManager.writeAllSourcesText(sourceFileName);		
		
	}
	
	public String createLocalSourceFileName() {
		return classesTextManager.DEFAULT_SOURCES_FILE_PREFIX + sourceSuffix;	
	}
	public String createLocalOutputFileName() {
		return DEFAULT_OUTPUT_FILE_PREFIX + outputSuffix;	
	}
	
	public String createFullSourceFileName() {
		return outputFolder  + "/" + createLocalSourceFileName();	
	}
	
	public String createFullOutputFileName() {
		return outputFolder  + "/" +createLocalOutputFileName();	
	}
	
	boolean madeClassDescriptions;
	List<Class> classesImplicitlyLoaded;
	
	public List<Class> getImplicitlyLoadedClasses() {
		return classesImplicitlyLoaded;
	}
	
	public void maybeMakeClassDescriptions() {
//		if (!hasBeenRun)
//			return;
		if (!runChecked && !hasBeenRun)
			return;
		
		
		if (madeClassDescriptions)
			return;
		classesManager.makeClassDescriptions(this);
		classViewManager = new AClassViewManager(classesManager);
//		ClassesTextManager classesSourceManager = new AClassesTextManager(classesManager);
		 classesTextManager = new AClassesTextManager(classViewManager);

		 classesTextManager.initializeAllSourcesText();
//		 sourceFileName = outputFolder  + "/" + classesTextManager.SOURCES_FILE_NAME;	
//		 outputFileName = outputFolder  + "/" + DEFAULT_OUTPUT_FILE_NAME;
		 System.out.println("Write sources to:" + sourceFileName);
		 classesTextManager.writeAllSourcesText(sourceFileName);
			madeClassDescriptions = true;

		
		

	}
	
	public String getOutputFileName() {
		return outputFileName;
	}
	
	public boolean hasBeenRun() {
		return hasBeenRun;
	}
	
	public void setHasBeenRun(boolean newVal) {		
		hasBeenRun = newVal;
		runChecked = true;
		if (hasBeenRun) {
			classesImplicitlyLoaded = new ArrayList(proxyClassLoader.getClassesLoaded());
		}
	}
	
	protected Class mainClass;
	protected Method mainMethod;
	protected String mainClassName;
	String[][] args;
	protected String[] inputFiles;
	protected String[] outputFiles;
	boolean runChecked;
	@Override()
	public boolean setRunParameters(String aMainClassName, String anArgs[][], String[] anInputFiles, String[] anOutputFiles, MainClassFinder aMainClassFinder){
		
		args = anArgs;
		try {
			mainClassName = aMainClassName;
		 mainClass = proxyClassLoader.loadClass(mainClassName);
		 inputFiles = anInputFiles;
		 outputFiles = anOutputFiles;
		 if (mainClass == null) {
			 mainClass = mainClassFinder.mainClass(rootCodeFolder, proxyClassLoader, mainClassName);
		 }
		   if (mainClass == null) {
				System.out.println("Missing main class:" + mainClassName + " for student:" + getProjectFolderName());
				setCanBeRun(false);

				return false;

		   }

		   
			 mainMethod = mainClass.getMethod("main", String[].class);
			if (mainMethod == null) {
				System.out.println("Missing main method:" +  "main");
				setCanBeRun(false);
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("cannot  run:" + getProjectFolderName());
			setCanBeRun(false);
			e.printStackTrace();
			return false;
			
		}
	}
	@Override
	public  Thread runProject () {
		try {
//			  mainClass = proxyClassLoader.loadClass(mainClassName);
//			   if (mainClass == null) {
//					System.out.println("Missing main class:" + mainClassName + " for student:" + getProjectFolderName());
//					setCanBeRun(false);
	//
//					return null;
	//
//			   }
//			   
//				 mainMethod = mainClass.getMethod("main", String[].class);
//				if (mainMethod == null) {
//					System.out.println("Missing main method:" +  "main");
//					setCanBeRun(false);
//					return null;
//				}
			if (!canBeRun()) return null;
//			Runnable runnable = new AReflectionBasedProjectRunner(mainClassName, args, this, inputFiles, outputFiles, mainClass, mainMethod);
			Runnable runnable = ProjectRunnerSelector.createProjectRunner(mainClassName, args, this, inputFiles, outputFiles, mainClass, mainMethod);

			Thread retVal = new Thread(runnable);

			retVal.start();
			System.out.println("started:" + retVal);

			return retVal;
			} catch (Exception e) {
				System.out.println("Could not run:" + getProjectFolderName());
				setCanBeRun(false);
				e.printStackTrace();
				return null;
				
			}
//			try {
////				if (rootCodeFolder.)
////				ClassLoader classLoader = new AProxyProjectClassLoader(aProject.getRootCodeFolder());
////				Thread.currentThread().setContextClassLoader(classLoader);
	//
//			   Class mainClass = proxyClassLoader.loadClass(mainClassName);
//				Method mainMethod = mainClass.getMethod("main", String[].class);	
////				String[] strings = {"move 10 100"};
//				Object[] mainArgs = {args};
////				Thread.currentThread().setContextClassLoader(classLoader);
//				mainMethod.invoke(mainClass, mainArgs);
//				} catch (Exception e) {
//					System.out.println("Could not run:" + rootFolder.getAbsoluteName());
//					e.printStackTrace();
//					
//				}	
		
	}

	public  Thread run (String aMainClassName, String[][] anArgs, String[] anInputFiles, String[] anOutputFiles) {
//		try {
//		  mainClass = proxyClassLoader.loadClass(mainClassName);
//		   if (mainClass == null) {
//				System.out.println("Missing main class:" + mainClassName + " for student:" + getProjectFolderName());
//				setCanBeRun(false);
//
//				return null;
//
//		   }
//		   
//			 mainMethod = mainClass.getMethod("main", String[].class);
//			if (mainMethod == null) {
//				System.out.println("Missing main method:" +  "main");
//				setCanBeRun(false);
//				return null;
//			}
		setRunParameters(aMainClassName, anArgs, anInputFiles, anOutputFiles, mainClassFinder);
		return runProject();
//		if (!canBeRun()) return null;
//		Runnable runnable = new AProjectRunner(mainClassName, args, this, anInputFile, anOutputFile, mainClass, mainMethod);
//		Thread retVal = new Thread(runnable);
//		retVal.start();
//		return retVal;
//		} catch (Exception e) {
//			System.out.println("Could not run:" + getProjectFolderName());
//			setCanBeRun(false);
//			e.printStackTrace();
//			return null;
//			
//		}
//		try {
////			if (rootCodeFolder.)
////			ClassLoader classLoader = new AProxyProjectClassLoader(aProject.getRootCodeFolder());
////			Thread.currentThread().setContextClassLoader(classLoader);
//
//		   Class mainClass = proxyClassLoader.loadClass(mainClassName);
//			Method mainMethod = mainClass.getMethod("main", String[].class);	
////			String[] strings = {"move 10 100"};
//			Object[] mainArgs = {args};
////			Thread.currentThread().setContextClassLoader(classLoader);
//			mainMethod.invoke(mainClass, mainArgs);
//			} catch (Exception e) {
//				System.out.println("Could not run:" + rootFolder.getAbsoluteName());
//				e.printStackTrace();
//				
//			}	
	}
	

	
	public AProject(String aProjectFolder, String anOutputFolder) {
		init(aProjectFolder, anOutputFolder, aProjectFolder.endsWith(ZIP_SUFFIX));
		
	}
	
	public AProject(String aProjectFolder) {
		init(aProjectFolder, outputFolder, aProjectFolder.endsWith(ZIP_SUFFIX));
		
	}
	
//	public  String getProjectFolder() {
//		return projectFolder;
//	}
	
	public ProxyClassLoader getClassLoader() {
//		maybeMakeClassDescriptions();

		return proxyClassLoader;
	}
	public ProxyBasedClassesManager getClassesManager() {
		maybeMakeClassDescriptions();

		return classesManager;
	}
	public void setClassesManager(ProxyBasedClassesManager aClassesManager) {
		this.classesManager = aClassesManager;
	}
	public ClassViewManager getClassViewManager() {
		maybeMakeClassDescriptions();
		return classViewManager;
	}
	public void setClassViewManager(ClassViewManager aClassViewManager) {
		this.classViewManager = aClassViewManager;
	}
	public ClassesTextManager getClassesTextManager() {
		maybeMakeClassDescriptions();

		return classesTextManager;
	}
	public void setClassesTextManager(ClassesTextManager aClassesTextManager) {
		this.classesTextManager = aClassesTextManager;
	}
	
	public void setProjectFolder(String aProjectFolder) {
		this.projectFolderName = aProjectFolder;
	}
	public String getProjectFolderName() {
		return projectFolderName;
	}
	
	public RootCodeFolder getRootCodeFolder() {
		return rootCodeFolder;
	}
	
	
	
	

	@Override
	public String getSourceProjectFolderName() {
		return rootCodeFolder.getSourceProjectFolderName();
	}
	
	@Override
	public String getSourceFileName() {
		return sourceFileName;
	}

	@Override
	public String getBinaryProjectFolderName() {
		// TODO Auto-generated method stub
		return rootCodeFolder.getBinaryProjectFolderName();
	}
	@Override
	public boolean runChecked() {
		return runChecked;
	}
	@Override
	public void setCanBeRun(boolean newVal) {
		runChecked = true;
		canBeRun = newVal;
		
	}
	@Override
	public boolean canBeRun() {
		// TODO Auto-generated method stub
		return canBeRun;
	}
	@Override
	public JavaDocBuilder getJavaDocBuilder() {
		if (javaDocBuilder == null) {
			javaDocBuilder = new JavaDocBuilder();
		}
		return javaDocBuilder;
	}
//	@Override
//	public String getMixedCaseSourceProjectFolderName() {
//		 return sourceFolderName;
//	}
//	
	
	
	public static void main (String[] args) {
	
//		ObjectEditor.setMethodAttribute(AFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
//		AGradeSheet gradeSheet = new AGradeSheet();
//		ObjectEditor.edit(gradeSheet);
//		JFrame customFrame = new JFrame();
//		ObjectEditor.editInMainContainer(gradeSheet,  AWTContainer.virtualContainer(customFrame.getContentPane()));
//		customFrame.setSize(500, 400);
//		customFrame.setVisible(true);
	
//		ObjectEditor.edit(gradeSheet, frame.getContentPane());
//		Project project1 = new AProject(AProject.PROJECT_DIRECTORY);
		Project project1 = new AProject(AProject.PROJECT_ZIPPED_DIRECTORY, "results");

		
//		Set<OEFrame> staticFrames = uiFrameList.getOEFrames();
//		gradeSheet.setStudentName("A");
		String[][] strings = {{"move 10 100"}};
		String[] inputFiles = {null};
		
		
		project1.run("main.finalAssignment", strings, inputFiles, inputFiles);
	}
//		Project project2 = new AProject(AProject.PROJECT_ZIPPED_DIRECTORY);
	
	



		
	

}
