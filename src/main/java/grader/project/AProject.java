package grader.project;

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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AProject implements Project {

    public static final String ZIP_SUFFIX = ".zip";
    public static final String DEFAULT_PROJECT_FOLDER = ".";
    public static final String DEFAULT_GRADING_FOLDER = "C:/Users/dewan/Downloads/GraderData";
    public static final String DEFAULT_OUTPUT_FILE_PREFIX = "output";
    public static final String DEFAULT_OUTPUT_FILE_SUFFIX = ".txt";
    public static final String DEFAULT_OUTPUT_FILE_NAME = DEFAULT_OUTPUT_FILE_PREFIX + DEFAULT_OUTPUT_FILE_SUFFIX;
    public static final String PROJECT_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated/Final";
    public static final String PROJECT_ZIPPED_DIRECTORY = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";

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
    String[][] args;
    boolean runChecked;

    protected Class mainClass;
    protected Method mainMethod;
    protected String mainClassName;
    protected String[] inputFiles;
    protected String[] outputFiles;

    public AProject(String aProjectFolder, String anOutputFolder, boolean aZippedFolder) {
        init(aProjectFolder, anOutputFolder, aZippedFolder);
    }

    public AProject(StudentCodingAssignment aStudentCodingAssignment) {
        init(aStudentCodingAssignment.getProjectFolder(), aStudentCodingAssignment.getFeedbackFolder().getAbsoluteName());
    }

    public AProject(StudentCodingAssignment aStudentCodingAssignment, String aSourceSuffix, String anOutputSuffix) {
        sourceSuffix = aSourceSuffix;
        outputSuffix = anOutputSuffix;
        init(aStudentCodingAssignment.getProjectFolder(), aStudentCodingAssignment.getFeedbackFolder().getAbsoluteName());
    }

    public String toString() {
        return "(" + projectFolderName + "," + outputFolder + ")";
    }

    public AProject(RootFolderProxy aRootFolder, String anOutputFolder) {
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

    protected MainClassFinder createMainClassFinder() {
        return new AMainClassFinder();
    }

    public void init(String aProjectFolder, String anOutputFolder, boolean aZippedFolder) {
        if (aZippedFolder) {
            rootFolder = new AZippedRootFolderProxy(aProjectFolder);
        } else {
            rootFolder = new AFileSystemRootFolderProxy(aProjectFolder);
        }
        init(rootFolder, anOutputFolder);
    }

    public void init(RootFolderProxy aRootFolder, String anOutputFolder) {
        rootFolder = aRootFolder;
        projectFolderName = aRootFolder.getAbsoluteName();
        outputFolder = anOutputFolder;
        rootCodeFolder = new AJavaRootCodeFolder(rootFolder);
        if (rootCodeFolder.hasValidBinaryFolder())
            proxyClassLoader = new AProxyProjectClassLoader(rootCodeFolder);
        sourceFileName = createFullSourceFileName();
        outputFileName = createFullOutputFileName();
        classesManager = new AProxyBasedClassesManager();
        mainClassFinder = createMainClassFinder();
    }

    public String createLocalSourceFileName() {
        return classesTextManager.DEFAULT_SOURCES_FILE_PREFIX + sourceSuffix;
    }

    public String createLocalOutputFileName() {
        return DEFAULT_OUTPUT_FILE_PREFIX + outputSuffix;
    }

    public String createFullSourceFileName() {
        return outputFolder + "/" + createLocalSourceFileName();
    }

    public String createFullOutputFileName() {
        return outputFolder + "/" + createLocalOutputFileName();
    }

    boolean madeClassDescriptions;
    List<Class> classesImplicitlyLoaded;

    public List<Class> getImplicitlyLoadedClasses() {
        return classesImplicitlyLoaded;
    }

    public void maybeMakeClassDescriptions() {
        if (!runChecked && !hasBeenRun)
            return;

        if (madeClassDescriptions)
            return;
        classesManager.makeClassDescriptions(this);
        classViewManager = new AClassViewManager(classesManager);
        classesTextManager = new AClassesTextManager(classViewManager);
        classesTextManager.initializeAllSourcesText();
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

    @Override()
    public boolean setRunParameters(String aMainClassName, String anArgs[][], String[] anInputFiles, String[] anOutputFiles, MainClassFinder aMainClassFinder) {
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
                System.out.println("Missing main method:" + "main");
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
    public Thread runProject() {
        try {
            if (!canBeRun()) return null;
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
    }

    public Thread run(String aMainClassName, String[][] anArgs, String[] anInputFiles, String[] anOutputFiles) {
        setRunParameters(aMainClassName, anArgs, anInputFiles, anOutputFiles, mainClassFinder);
        return runProject();
    }


    public AProject(String aProjectFolder, String anOutputFolder) {
        init(aProjectFolder, anOutputFolder, aProjectFolder.endsWith(ZIP_SUFFIX));

    }

    public AProject(String aProjectFolder) {
        init(aProjectFolder, outputFolder, aProjectFolder.endsWith(ZIP_SUFFIX));

    }

    public ProxyClassLoader getClassLoader() {
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
        return canBeRun;
    }

    @Override
    public JavaDocBuilder getJavaDocBuilder() {
        if (javaDocBuilder == null) {
            javaDocBuilder = new JavaDocBuilder();
        }
        return javaDocBuilder;
    }

}
