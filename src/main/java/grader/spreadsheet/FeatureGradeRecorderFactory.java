package grader.spreadsheet;

import grader.sakai.project.SakaiProjectDatabase;

public interface FeatureGradeRecorderFactory extends FinalGradeRecorderFactory{
	FeatureGradeRecorder createGradeRecorder(SakaiProjectDatabase aSakaiProjectDatabase);

}
