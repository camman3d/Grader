package grader.models;

import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;

import java.io.File;

import javax.swing.JFileChooser;

import util.annotations.ComponentWidth;
import util.annotations.PropertyNames;
import util.annotations.StructurePattern;
import util.annotations.StructurePatternNames;
import util.misc.ASuffixFileFilter;
import util.models.AListenableVector;
import util.models.ListenableVector;
import bus.uigen.ObjectEditor;
@StructurePattern(StructurePatternNames.BEAN_PATTERN)
@PropertyNames({"StudentName", "StudentId", "Features", "Comment"})
// grade sheet takes as input data the names and max scores associated with various features
public class AGradeSheet {	
	String studentName = "";
	String studentId = "";
	ListenableVector<GradingFeature> features;
	String comment = "None";
	JFileChooser fc = new JFileChooser();
	Object[][] featureDescriptions = {{"Tokens", 20}, {"Scanner", 50}};	
	public static ListenableVector<GradingFeature> toFeatures(Object[][] featureDescriptions) {
		ListenableVector<GradingFeature> retVal = new AListenableVector();
		for (Object[] featureDescription: featureDescriptions) {
			retVal.add(new AGradingFeature((String) featureDescription[0], 10)) ;
			
		}
		return retVal;
	}
	
	public AGradeSheet() {
		File file = new File("D:/dewan_backup/Java");
		fc.setCurrentDirectory(file);
		fc.setFileFilter(new ASuffixFileFilter(".zip"));
		features = toFeatures(featureDescriptions);
	}
	
//	@Row(0)
	public String getStudentName() {
		return studentName;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public void setStudentName(String aStudentName) {
		this.studentName = aStudentName;	
	}
//	@Row(1)
	public ListenableVector<GradingFeature> getFeatures() {
		return features;
	}	
	public void setFeatures(ListenableVector<GradingFeature> somefeatures) {
		this.features = somefeatures;
		
	}
//	@Row(2)
	@ComponentWidth(300)
	public String getComment() {
		return comment;
	}	
	public void setComment(String aComment) {
		this.comment = aComment;
	}
	
	public static void main (String[] args) {
		ObjectEditor.edit(new AGradeSheet());
	}

	

	
	
	

}
