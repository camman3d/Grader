package grader.spreadsheet;

import grader.sakai.project.SakaiProjectDatabase;

public interface FinalGradeRecorderFactory {
	FinalGradeRecorder createGradeRecorder(SakaiProjectDatabase aSakaiProjectDatabase);

}
