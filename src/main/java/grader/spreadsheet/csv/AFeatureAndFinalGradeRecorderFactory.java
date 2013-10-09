package grader.spreadsheet.csv;

import grader.assignment.GradingFeature;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.FinalGradeRecorderFactory;

import java.util.List;

public class AFeatureAndFinalGradeRecorderFactory implements FinalGradeRecorderFactory {

	@Override
	public FinalGradeRecorder createGradeRecorder(
			SakaiProjectDatabase aSakaiProjectDatabase) {
		return new AFeatureAndFinalGradeRecorder(aSakaiProjectDatabase);
	}

	

}
