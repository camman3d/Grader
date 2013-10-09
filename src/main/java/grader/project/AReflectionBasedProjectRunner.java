package grader.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import util.misc.TeePrintStream;

import bus.uigen.OEFrame;
import bus.uigen.uiFrameList;

public class AReflectionBasedProjectRunner implements Runnable {
	String projectName;
	String mainClassName;
	String[][] mainArgs;
//	ProxyClassLoader proxyClassLoader;
	Project project;
	String[] outputFiles;
	String[] inputFiles;
	Method mainMethod;
	Class mainClass;
	
	public AReflectionBasedProjectRunner(String aMainClassName, String[][] aMainArgs, Project aProject, String[] anInputFiles, String[] anOutputFiles, Class aMainClass, Method aMainMethod) {
		projectName = aProject.getProjectFolderName();
		mainClassName = aMainClassName;
		mainArgs = aMainArgs;
		project = aProject;
//		proxyClassLoader = project.getClassLoader();
		outputFiles = anOutputFiles;
		inputFiles = anInputFiles;
		mainMethod = aMainMethod;
		mainClass = aMainClass;
	}
	
//	public  void run () {
//		JFrame frame = new JFrame();
//		frame.setSize(100, 100);
//		frame.setVisible(true);
//		
////		frame.setVisible(false);
//		System.out.println("terminated");
//		
//	}
	public static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";
	public  void run () {
		try {

//			Object[] args = {mainArgs};
//			Object[] args = {};
//			Thread.currentThread().setContextClassLoader(classLoader);
			

			    InputStream stdin = null;
			    PrintStream stdout = null;
//			    InputStream originalIn = System.in;
			    PrintStream originalOut = System.out;
			    if (inputFiles.length == 0) {
			    	inputFiles = new String[1];
			    }
			    
			    if (outputFiles.length == 0) {
			    	outputFiles = new String [] {project.getOutputFileName()};
			    }
			    if (mainArgs.length == 0) {
			    	mainArgs = new String [1][];
			    	

			    }
			    
			    
			   for (int i = 0; i < inputFiles.length; i++) {
				   
				  String inputFile = inputFiles[i];
				  String outputFile = outputFiles[i];
				  Object[] args = {mainArgs[i]};
			   if (inputFile != null) {
				    stdin = new FileInputStream(inputFile);

			   }
			   if (outputFile != null) {
				    stdout = new TeePrintStream(new FileOutputStream(outputFile), originalOut);

			   }
			   if (stdin != null) {
				    System.setIn(stdin);

			   }
			   if (stdout != null) {
				   System.setOut(stdout);
			   }
			   try {

			mainMethod.invoke(mainClass, args);
			if (outputFile != null) {
				stdout.close();
			}
			if (inputFile != null) {
				stdin.close();
			}
			   } catch (Exception e) {
				   System.out.println("Could not successfully run:" +projectName + "with input file:" + inputFile);
//					project.setCanBeRun(false);
					e.printStackTrace();
			   }
			project.setHasBeenRun(true);

			System.out.println("terminated main method");
			   }
			


			} catch (Exception e) {
				System.out.println("Could not run:" +projectName);
				project.setCanBeRun(false);
				e.printStackTrace();
				
			}	
	}

}
