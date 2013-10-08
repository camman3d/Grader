package grader.sakai.project;

import grader.project.AProject;
import grader.sakai.StudentAssignment;
import grader.sakai.StudentCodingAssignment;

import java.util.List;

public class ASakaiProject extends AProject implements SakaiProject{
	StudentCodingAssignment studentAssignment;
	public ASakaiProject (StudentCodingAssignment aStudentCodingAssignment, String aSourceSuffix, String anOutputSuffix) {
		super (aStudentCodingAssignment, aSourceSuffix, anOutputSuffix);
		studentAssignment = aStudentCodingAssignment;
		List<String> documents = studentAssignment.getDocuments();
		documents.remove(getOutputFileName());
		documents.remove(getSourceFileName());
	}
	
	public StudentAssignment getStudentAssignment() {
		return studentAssignment;
	}
	public void displaySource(SakaiProjectDatabase aSakaiProjectDatabase) {
		maybeMakeClassDescriptions();
//		 DocumentDisplayerRegistry.display(sourceFileName);
		aSakaiProjectDatabase.getSourceDisplayer().displaySource(this);

		
	}
    
	
	

}
