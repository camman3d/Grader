package grader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class StudentClassLoader extends ClassLoader {
	String studentZippedDirectory;
	String projectName;
	ZipFile zipFile; 
	String mainName;
	ZipEntry mainEntry;
	
	public StudentClassLoader(String aZippedDirectory) {
		initZipFile(aZippedDirectory);
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
	public StudentClassLoader(String aZippedDirectory, String aProjectName) {
		initZipFile(aZippedDirectory);
		projectName = aProjectName;

	}
	
	void setProjectName() {
		ZipEntry someEntry = zipFile.entries().nextElement();
		int slashIndex = someEntry.getName().indexOf("/");
		projectName = someEntry.getName().substring(0, slashIndex);
	}
	
	void setMain() {
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry nextElement = entries.nextElement();
			if ( nextElement.getName().indexOf("main") >= 0) {
				mainEntry = nextElement;
				mainName = mainEntry.getName();
				return;
			}			
		}		
	}
	
	void initZipFile(String aZippedDirectory) {
		studentZippedDirectory = aZippedDirectory;
		try {
			zipFile = new ZipFile(studentZippedDirectory);
			setProjectName();
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
		ZipEntry zipEntry = zipFile.getEntry(projectName + "/" + name);
		try {
		return zipFile.getInputStream(zipEntry);
		} catch (Exception e) {
			return null;
		}		
	}
	
	protected  Class findClass(String aClassName) throws ClassNotFoundException {
		try {
			byte classBytes[];
			String aFileName = aClassName.replaceAll("\\.", "/") + ".class";
			String aFullFileName =  projectName + "/bin/" + aFileName;	
			ZipEntry zipEntry = zipFile.getEntry(aFullFileName);
			InputStream inputStream = zipFile.getInputStream(zipEntry);
//	    	    FileInputStream fi = new FileInputStream(studentZippedDirectory + "/" + aClassName);
	    	    classBytes = new byte[inputStream.available()];
	    	    inputStream.read(classBytes);
	    	    Class classObject = defineClass(aClassName, classBytes, 0, classBytes.length);
//	    	    System.out.println("Class loader: " + classObject.getClassLoader());
	    	    return classObject;
		} catch (Exception e) {
			throw new ClassNotFoundException();
		}
//		return super.findClass(aFileName);
	}
	public Class loadClass(String aFileName) throws ClassNotFoundException {
//		System.out.println("Loading class:" + aFileName);
		return super.loadClass(aFileName);
	}
	

}
