package grader.demo.main;

import java.util.ArrayList;
import java.util.List;

import bus.uigen.ObjectEditor;
import bus.uigen.attributes.AttributeNames;
import bus.uigen.viewgroups.AVectorNavigator;

import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.checkers.AnEncapsulationChecker;
import grader.checkers.FeatureChecker;
import grader.project.AProject;
import grader.sakai.ASakaiBulkAssignmentFolder;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.SakaiProjectDatabase;

public class ADemoSakaiGradableAssignment extends ASakaiProjectDatabase {

	public ADemoSakaiGradableAssignment(String aBulkAssignmentsFolderName, String anAssignmentsDataFolderName) {
		super(aBulkAssignmentsFolderName, anAssignmentsDataFolderName);
		addFeatures();
		
	}
	
	public void addFeatures() {
//		addEncapsulationFeature();
//		List<GradingFeature> aGradingFeatures = new ArrayList();
//		aGradingFeatures.add(createEncapsulationFeature());
//		aGradingFeatures.add(createStyleFeature());
		addGradingFeatures(createGradingFeatures());
	}
	
	public List<GradingFeature> createGradingFeatures() {
		List<GradingFeature> aGradingFeatures = new ArrayList();
		aGradingFeatures.add(createEncapsulationFeature());
		aGradingFeatures.add(createStyleFeature());
		return aGradingFeatures;
	}
	
	public GradingFeature createEncapsulationFeature() {
		FeatureChecker featureChecker = new AnEncapsulationChecker();

		GradingFeature aGradingFeature = new AGradingFeature("Encapsulation", 10, featureChecker);
		return aGradingFeature;
//		featureChecker.init(aGradingFeature);
//		aGradingFeature.init(featureChecker );
	}
	
	public GradingFeature createStyleFeature() {

		GradingFeature aGradingFeature = new AGradingFeature("Style", 10);
		return aGradingFeature;
//		featureChecker.init(aGradingFeature);
//		aGradingFeature.init(featureChecker );
	}
	static String bulk = "D:/My Dropbox/Dropbox/GraderUser/new graderData/bulk_download";
	static String graderData =	"D:/My Dropbox/Dropbox/GraderUser/new graderData/GraderData";
	
//	static String bulk = "D:/My Dropbox/GraderUser/new graderData/bulk_download";
//	static String graderData =	"D:/My Dropbox/GraderUser/new graderData/GraderData";
//	
	public static void main (String[] args) {
		SakaiProjectDatabase projectDatabase = 
				new ADemoSakaiGradableAssignment(bulk, graderData);
		ObjectEditor.setMethodAttribute(AGradingFeature.class, "autoGrade", AttributeNames.SHOW_BUTTON, true);
		ObjectEditor.setAttribute(AGradingFeature.class, AttributeNames.SHOW_UNBOUND_BUTTONS, new Boolean(true) );


//		projectDatabase.runProjectInteractively("mkcolema");
		projectDatabase.runProjectsInteractively();

	}

}
