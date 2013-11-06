package grader.demo.scores;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeature;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;
import grader.spreadsheet.csv.ASakaiCSVFeatureGradeManager;

public class ATracingFeatureScoreRecorder extends ASakaiCSVFeatureGradeManager  {

	public ATracingFeatureScoreRecorder(
			SakaiProjectDatabase aSakaiProjectDatabase) {
		super(aSakaiProjectDatabase);
		// TODO Auto-generated constructor stub
	}

	
	
	public void setGrade(String aStudentName, String anOnyen, String aFeature, double aScore) {
		super.setGrade(aStudentName, anOnyen, aFeature, aScore);
		System.out.println("setting grade for " + anOnyen + " " + aFeature + " " + aScore);
		
	}
	public double getGrade(String aStudentName, String anOnyen, String aFeature) {
		return super.getGrade(aStudentName, anOnyen, aFeature);
	}
	@Override
	public void setGrade(String aStudentName, String anOnyen, double aScore) {
		super.setGrade(aStudentName, anOnyen, aScore);
		System.out.println("Trapped the set grade call for:" + anOnyen + "," + aScore);
	}
	@Override
	public double getGrade(String aStudentName, String anOnyen) {
		System.out.println("Trapped the get grade call for:" + anOnyen );

		return super.getGrade(aStudentName, anOnyen);
	}
	

}
