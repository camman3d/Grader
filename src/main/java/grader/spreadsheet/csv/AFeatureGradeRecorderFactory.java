package grader.spreadsheet.csv;

import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FeatureGradeRecorderFactory;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.FinalGradeRecorderFactory;

public class AFeatureGradeRecorderFactory implements FeatureGradeRecorderFactory{

//	@Override
//	public FinalGradeRecorder createFinalGradeRecorder(
//			SakaiProjectDatabase aSakaiProjectDatabase) {
//		return new ASakaiCSVFinalGradeManager(aSakaiProjectDatabase);
//	}

	@Override
	public FeatureGradeRecorder createGradeRecorder(
			SakaiProjectDatabase aSakaiProjectDatabase) {
		return new ASakaiCSVFeatureGradeManager(aSakaiProjectDatabase);
	}
}
