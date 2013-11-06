package grader.sakai.project;

import java.util.Collection;
import java.util.List;
import java.util.Set;


import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeatureList;
import grader.feedback.AutoFeedback;
import grader.feedback.ManualFeedback;
import grader.feedback.ScoreFeedback;
import grader.feedback.SourceDisplayer;
import grader.project.Project;
import grader.sakai.BulkAssignmentFolder;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;

public interface SakaiProjectDatabase {
	public BulkAssignmentFolder getBulkAssignmentFolder();
	
	public AssignmentDataFolder getAssigmentDataFolder() ;
	
	public SakaiProject getProject(String aName) ;
	public Set<String> getOnyens() ;
	
	public Collection<SakaiProject> getProjects();
	public SakaiProject runProject(String anOnyen);
	public void runProjectInteractively(String anOnyen);
	
	public void runProjectInteractively(String anOnyen, ProjectStepper projectStepper) ;
	public void displayOutput();
	public void runProjectsInteractively();

	FinalGradeRecorder getGradeRecorder();
	public GradingFeatureList getGradingFeatures();

	FeatureGradeRecorder getFeatureGradeRecorder();
	public void setGradeRecorder(FinalGradeRecorder gradeRecorder) ;

	public void setFeatureGradeRecorder(FeatureGradeRecorder featureGradeRecorder) ;
	SakaiProject runProject(String anOnyen, SakaiProject aProject);

	void resetRunningProject(SakaiProject aProject);
	public FinalGradeRecorder getTotalScoreRecorder() ;
	public void  setTotalScoreRecorder(FinalGradeRecorder newVal) ;

	public AutoFeedback getAutoFeedback() ;
	
	public ManualFeedback getManualFeedback();
	
	public ScoreFeedback getScoreFeedback() ;

	public SourceDisplayer getSourceDisplayer() ;
	void initIO();
	
		
	void recordWindows();
	
	void resetIO();
	void clearWindows();
	public List<String> getOnyenNavigationList(SakaiProjectDatabase aSakaiProjectDatabase);
	public List<String> getOnyenNavigationList();
	void nonBlockingRunProjectsInteractively();
	public String getNavigationFilter() ;

	public void setNavigationFilter(String navigationFilter) ;

	void startProjectStepper();

	public Object displayProjectStepper(ProjectStepper aProjectStepper) ;





}
