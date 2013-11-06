package grader.spreadsheet.csv;

import grader.assignment.GradingFeature;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FinalGradeRecorder;

import java.util.List;

public class AFeatureAndFinalGradeRecorder implements FinalGradeRecorder {
	FinalGradeRecorder finalGradeRecorder, featureGradeRecorder;
	public AFeatureAndFinalGradeRecorder(SakaiProjectDatabase aProjectDatabase) {
		finalGradeRecorder = aProjectDatabase.getGradeRecorder();
		featureGradeRecorder = aProjectDatabase.getFeatureGradeRecorder();	
	}
	@Override
	public void setGrade(String aStudentName, String anOnyen, double aScore) {
		finalGradeRecorder.setGrade(aStudentName, anOnyen, aScore);
		featureGradeRecorder.setGrade(aStudentName, anOnyen, aScore);
	}
	@Override
	public double getGrade(String aStudentName, String anOnyen) {
		return finalGradeRecorder.getGrade(aStudentName, anOnyen);
	}
	

}
