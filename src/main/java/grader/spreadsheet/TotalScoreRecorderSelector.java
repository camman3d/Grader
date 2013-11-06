package grader.spreadsheet;

import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.csv.AFeatureAndFinalGradeRecorderFactory;
import grader.spreadsheet.csv.AFeatureGradeRecorderFactory;

public class TotalScoreRecorderSelector {
//	static FinalGradeRecorderFactory factory = new AFeatureAndFinalGradeRecorderFactory();
	static FinalGradeRecorderFactory factory = new AFeatureGradeRecorderFactory();


	public static FinalGradeRecorderFactory getFactory() {
		return factory;
	}

	public static void setFactory(FinalGradeRecorderFactory factory) {
		TotalScoreRecorderSelector.factory = factory;
	}
	
	public static FinalGradeRecorder createFinalGradeRecorder(SakaiProjectDatabase aSakaiProjectDatabase) {
		return factory.createGradeRecorder(aSakaiProjectDatabase);
	}

	
}
