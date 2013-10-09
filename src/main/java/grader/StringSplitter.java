package grader;

import javax.swing.JComboBox;


public class StringSplitter {
	JComboBox<Object> foo;
	public static void main (String[] args ) {
		String s = "HELLO WORLD";	
		String nullString = null;
		boolean equals = nullString.equals(s);
		String[] newStrings = s.split("ffff");
//		System.out.println(newStrings);
	}

}
