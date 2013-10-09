package grader;

import java.io.File;
import java.io.FileReader;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

public class QDoxUse {
	static JavaDocBuilder builder = new JavaDocBuilder();
	static String importsFileName = "src/grader/QDoxUse.java";
	static String importsDirName = "src/lectures/mvc/toolkit";
	static String commentsDirName = "src/lectures/documentation/comments";
	
	public static void printImportFromSources() {
		
		for (JavaSource src: builder.getSources()) {
		    String[] imports = src.getImports();

		    for ( String imp : imports )
		    {
		        System.out.println(imp);
		    }
			}
	}
	
	public static void addFile(String aFileName) {
		try {
		FileReader fileReader = new FileReader(aFileName );
		builder.addSource(fileReader);
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addDirectory(String aFolderName) {
		try {
		File file = new File(aFolderName );
		builder.addSourceTree(file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printImportFromFile(String aFileName) {
//		try {
		    addFile(aFileName);		
			printImportFromSources();

//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	public static void printImportFromDir(String aDirName) {
		try {
			addDirectory(aDirName);
			printImportFromSources();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printComments() {
		
		for (JavaSource src: builder.getSources()) {
		    JavaClass javaClasses[] = src.getClasses();
		    for (JavaClass javaClass:javaClasses ) {
		    	System.out.println(javaClass.getComment());
		    }		  
	}
	}
	public static void printCommentsFromDir(String aDirName) {
		try {
			addDirectory(aDirName);
			printComments();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
//		printImportFromFile(importsFileName);
//		printImportFromDir(importsDirName);
		printCommentsFromDir(commentsDirName);		
	}
	

}
