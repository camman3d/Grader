package grader;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import bus.uigen.OEFrame;
import bus.uigen.ObjectEditor;
import bus.uigen.uiFrameList;
import bus.uigen.attributes.AttributeNames;

public class TracingClassLoaderTester {
	
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
	
	public TracingClassLoaderTester(ClassLoader classLoader) {
	}
	

	public static void main (String[] args) {
		try {
//		ClassLoader classLoader = new TracingClassLoader();
//		Class mainClass = classLoader.loadClass("grader.StringUser");
//		Method mainMethod = mainClass.getMethod("main", String[].class);
//		String[] strings = {"move 10 100"};
//		Object[] myArgs = {strings};
//		mainMethod.invoke(mainClass, myArgs);
			String[] myArgs = {};
		ModifiedStringUser.main(myArgs);

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
