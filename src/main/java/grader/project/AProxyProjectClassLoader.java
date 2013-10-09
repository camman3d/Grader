package grader.project;

import grader.file.FileProxy;
import grader.project.file.RootCodeFolder;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.widgets.awt.AWTContainer;

public class AProxyProjectClassLoader extends ClassLoader implements ProxyClassLoader {
//	String studentZippedDirectory;
//	String projectName;
//	ZipFile zipFile; 
//	String mainName;
//	FileProxy mainEntry;
	public static final String CLASS_MAIN = "main.finalAssignment";

	RootCodeFolder rootCodeFolder;
	String projectFolderName, binaryFolderName;
	List<Class> classesLoaded = new ArrayList();
	
	public AProxyProjectClassLoader(RootCodeFolder aRootCodeFolder) {
		init(aRootCodeFolder);
//		initProjectName();
//		try {
//			zipFile = new ZipFile(studentZippedDirectory);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		int lastSlashPos = studentZippedDirectory.lastIndexOf('/');
//		int zipPos = studentZippedDirectory.length() - ".zip".length();
//		projectName = studentZippedDirectory.substring(lastSlashPos + 1, zipPos);
		
	}
//	public ProxyStudentClassLoader(String aProjectDurectory, String aProjectName) {
//		initZipFile(aProjectDurectory);
//		projectName = aProjectName;
//
//	}
	
//	void setProjectName() {
//		ZipEntry someEntry = zipFile.entries().nextElement();
//		int slashIndex = someEntry.getName().indexOf("/");
//		projectName = someEntry.getName().substring(0, slashIndex);
//	}
	
//	void setMain() {
//		Enumeration<? extends ZipEntry> entries = zipFile.entries();
//		while (entries.hasMoreElements()) {
//			ZipEntry nextElement = entries.nextElement();
//			if ( nextElement.getName().indexOf("main") >= 0) {
//				mainEntry = nextElement;
//				mainName = mainEntry.getName();
//				return;
//			}			
//		}		
//	}
	
//
//	void setMain() {
//		Set<String> fileNames = rootCodeFolder.getEntryNames();
//		for (String name: fileNames) {
//			if ( name.indexOf("main") >= 0) {
//				mainEntry = rootCodeFolder.getFileEntry(name);
//				mainName = name;
//				return;
//			}			
//		}		
//	}
	
	void init(RootCodeFolder aRootCodeFolder) {
		rootCodeFolder = aRootCodeFolder;
		binaryFolderName = rootCodeFolder.getBinaryProjectFolderName();
		projectFolderName = rootCodeFolder.getProjectFolderName();
		try {
//			zipFile = new ZipFile(studentZippedDirectory);
//			setProjectName();
//			setMain();
//			ZipEntry someEntry = zipFile.entries().nextElement();
//			int slashIndex = someEntry.getName().indexOf("/");
//			projectName = someEntry.getName().substring(0, slashIndex);
//			ZipEntry mainDir = zipFile.getEntry(projectName + "/main");
//			someEntry = zipFile.entries().nextElement();
//			System.out.println("some emtry");
			
		
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
//	void initProjectName() {
//		int lastSlashPos = studentZippedDirectory.lastIndexOf('/');
//		int zipPos = studentZippedDirectory.length() - ".zip".length();
//		projectName = studentZippedDirectory.substring(lastSlashPos + 1, zipPos);
//	}
	
	public InputStream getResourceAsStream(String name) {
		InputStream retVal = super.getResourceAsStream(name);
		if (retVal != null)
			return retVal;
		String aFullFileName = projectFolderName + "/" + name;
		FileProxy fileProxy = rootCodeFolder.getRootFolder().getFileEntry(aFullFileName);
		InputStream inputStream = fileProxy.getInputStream();
		return inputStream;
	}
	
	public  Class findClass(String aClassName) throws ClassNotFoundException {
		try {
			byte classBytes[];
			String aFileName = aClassName.replaceAll("\\.", "/") + ".class";
//			String aFullFileName =  projectName + "/bin/" + aFileName;	
//			String aFullFileName =  rootCodeFolder.getBinaryProjectFolderName() + "/" + aFileName;	
			String aFullFileName =  binaryFolderName + "/" + aFileName;	

			FileProxy ClassFile = rootCodeFolder.getFileEntry(aFullFileName);
			InputStream inputStream = ClassFile.getInputStream();

//			ZipEntry zipEntry = zipFile.getEntry(aFullFileName);
//			InputStream inputStream = zipFile.getInputStream(zipEntry);
//	    	    FileInputStream fi = new FileInputStream(studentZippedDirectory + "/" + aClassName);
	    	    classBytes = new byte[inputStream.available()];
	    	    inputStream.read(classBytes);
	    	    Class classObject = defineClass(aClassName, classBytes, 0, classBytes.length);
//	    	    System.out.println("Class loader: " + classObject.getClassLoader());
	    	    classesLoaded.add(classObject);
	    	    return classObject;
		} catch (Exception e) {
			return null;
//			throw new ClassNotFoundException();
		}
//		return super.findClass(aFileName);
	}
	public Class loadClass(String aClassName) throws ClassNotFoundException {
//		System.out.println("Loading class:" + aClassName);
		return super.loadClass(aClassName);
	}
	public static void run(Project aProject, String mainClassName) {
		try {
			ClassLoader classLoader = new AProxyProjectClassLoader(aProject.getRootCodeFolder());
//			Thread.currentThread().setContextClassLoader(classLoader);

			Class mainClass = classLoader.loadClass(mainClassName);
			Method mainMethod = mainClass.getMethod("main", String[].class);	
			String[] strings = {"move 10 100"};
			Object[] myArgs = {strings};
//			Thread.currentThread().setContextClassLoader(classLoader);
			mainMethod.invoke(mainClass, myArgs);
			} catch (Exception e) {
				e.printStackTrace();
				
			}	
	}
	public List<Class> getClassesLoaded() {
		return classesLoaded;
	}
public static void main (String[] args) {

	
		
		ObjectEditor.setMethodAttribute(AFeature.class, "correct", AttributeNames.SHOW_BUTTON, true);
		AGradeSheet gradeSheet = new AGradeSheet();
		ObjectEditor.edit(gradeSheet);
		JFrame customFrame = new JFrame();
		ObjectEditor.editInMainContainer(gradeSheet,  AWTContainer.virtualContainer(customFrame.getContentPane()));
		customFrame.setSize(500, 400);
		customFrame.setVisible(true);
	
//		ObjectEditor.edit(gradeSheet, frame.getContentPane());

		Set<OEFrame> staticFrames = uiFrameList.getOEFrames();
		gradeSheet.setStudentName("A");
//		Project project = new AProject(AProject.PROJECT_DIRECTORY);
		Project project = new AProject(AProject.PROJECT_ZIPPED_DIRECTORY);

		run(project, CLASS_MAIN);		


	}
//		try {
//		ClassLoader classLoader = new StudentClassLoader(ZIPPED_FOLDER);
////		Thread.currentThread().setContextClassLoader(classLoader);
//
//		Class mainClass = classLoader.loadClass(CLASS_MAIN);
//		Method mainMethod = mainClass.getMethod("main", String[].class);	
//		String[] strings = {"move 10 100"};
//		Object[] myArgs = {strings};
////		Thread.currentThread().setContextClassLoader(classLoader);
//		mainMethod.invoke(mainClass, myArgs);
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//	}

}
