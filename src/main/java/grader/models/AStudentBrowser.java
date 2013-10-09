package grader.models;

import javax.swing.JTextArea;

import util.annotations.Column;
import util.annotations.ComponentHeight;
import util.annotations.ComponentWidth;
import util.annotations.Label;
import util.annotations.PreferredWidgetClass;
import util.annotations.Row;
import bus.uigen.ObjectEditor;
// the idea is to show the ungraded and graded stdents and tow show how the ideal UI should be created
public class AStudentBrowser {	
	String allStudents = "onyen1 onyen2 onyen3 onyen5 onyen10";
	String ungradedStudents = "onyen4 onyen5";
	@Row(1)
	@Label("Ungraded: ")
//	@PreferredWidgetClass(JTextArea.class)
	public String getUngradedStudents() {
		return ungradedStudents;
	}
	public void setUngradedStudents(String ungradedStudentId) {
		this.ungradedStudents = ungradedStudentId;
	}
	@Row(2)
	@Label("All: ")
	@PreferredWidgetClass(JTextArea.class)
	@ComponentWidth(50)	
	@ComponentHeight(50)
	public String getAllStudents() {
		return allStudents;
	}
	public void setAllStudents(String newVal) {
		this.allStudents = newVal;
	}	
	@Row(0)
	@Column(0)
	@Label(">")
	public void next() {
		
	}
	@Row(0)
	@Column(1)
    public void repeat() {
		
	}
	@Row(0)
	@Column(2)
    public void open() {
		System.out.println("opened");
		
	}
	public static void main (String[] args) {
		ObjectEditor.edit(new AStudentBrowser());
	}

	
	

	
	
	

}
