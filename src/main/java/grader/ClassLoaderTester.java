package grader;

import java.lang.reflect.Method;
import java.util.Set;

import javax.swing.JFrame;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.widgets.awt.AWTContainer;

public class ClassLoaderTester {
//	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/RecursionLimit.zip";
	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";
//	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/OzArchive.zip";
//	public static final String ZIPPED_FOLDER = "D:/dewan_backup/Java/AmandaKaramFinalUpdated.zip";


	public static final String PROJECT_NAME = "Final";
//	public static final String PROJECT_NAME = "oz";

	public static final String CLASS_MAIN = "main.finalAssignment";
//	public static final String CLASS_MAIN = "simulation.AnInterpretedOzSimulation";
//	public static final String CLASS_MAIN = "shapes.AShapeImage";
	
	public static void run(String zippedFolder, String mainClassName) {
		try {
			ClassLoader classLoader = new StudentClassLoader(zippedFolder);
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
		run(ZIPPED_FOLDER, CLASS_MAIN);		
//		Set<OEFrame> newFrames = uiFrameList.getOEFramesOtherThan(staticFrames);
//		uiFrameList.dispose(newFrames);
//		gradeSheet.setStudentName("B");
//		run(ZIPPED_FOLDER, CLASS_MAIN);
//		newFrames = uiFrameList.getOEFramesOtherThan(staticFrames);
//		uiFrameList.dispose(newFrames);
//		gradeSheet.setStudentName("C");
//		run(ZIPPED_FOLDER_2, CLASS_MAIN_2);
//		newFrames = uiFrameList.getOEFramesOtherThan(staticFrames);
//		uiFrameList.dispose(newFrames);

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
