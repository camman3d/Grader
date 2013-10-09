package grader;

//import org.apache.commons.cli.CommandLine;

import bus.uigen.ObjectEditor;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
/*
 * writing garbage
 */
public class ModifiedStringUser {
	public static void main(String[] args) throws Exception {
		String s = "HELLO WORLD";
		System.out.println(s);
		String[] newStrings = s.split("ffff");
		System.out.println(newStrings);
		System.out.println((5 * 3));
		ObjectEditor.edit("hello world");
		System.out.println(String.class.getField("hiddenValue").getName());
	}
	
	

}
