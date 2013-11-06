package grader.spreadsheet;

import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.csv.AFeatureAndFinalGradeRecorderFactory;
import grader.spreadsheet.csv.AFeatureGradeRecorderFactory;
import grader.spreadsheet.csv.AFinalGradeRecorderFactory;

public class FeatureGradeRecorderSelector {
	static FeatureGradeRecorderFactory factory = new AFeatureGradeRecorderFactory();

	public static FeatureGradeRecorderFactory getFactory() {
		return factory;
	}

	public static void setFactory( FeatureGradeRecorderFactory factory) {
		FeatureGradeRecorderSelector.factory = factory;
	}
	
	public static FeatureGradeRecorder createFeatureGradeRecorder(SakaiProjectDatabase aSakaiProjectDatabase) {
		return factory.createGradeRecorder(aSakaiProjectDatabase);
	}

	
}
