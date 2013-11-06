package grader.demo.main;

import grader.spreadsheet.csv.ASakaiFeatureGradeSheetMerger;
import grader.spreadsheet.csv.SakaiFeatureGradeSheetMerger;

public class AGradeFilesMergerMain {
	static final String[] sourceFolders = { "C:/Users/dewan/Downloads/GraderData/Assignment 11"};
	static final String finalFile =  "C:/Users/dewan/Downloads/bulk_download/Assignment 11/grades.csv";
	public static void main (String[] args) {
		SakaiFeatureGradeSheetMerger merger = new ASakaiFeatureGradeSheetMerger(sourceFolders, finalFile);
		merger.merge();
		
	}

}
