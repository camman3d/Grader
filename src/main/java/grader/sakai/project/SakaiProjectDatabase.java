package grader.sakai.project;

import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeatureList;
import grader.feedback.AutoFeedback;
import grader.feedback.ManualFeedback;
import grader.feedback.ScoreFeedback;
import grader.feedback.SourceDisplayer;
import grader.sakai.BulkAssignmentFolder;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;

import java.util.Collection;
import java.util.Set;

public interface SakaiProjectDatabase {
    public BulkAssignmentFolder getBulkAssignmentFolder();

    public AssignmentDataFolder getAssigmentDataFolder();

    public SakaiProject getProject(String aName);

    public Set<String> getOnyens();

    public Collection<SakaiProject> getProjects();

    public SakaiProject runProject(String anOnyen);

    public void runProjectInteractively(String anOnyen);

    public void runProjectInteractively(String anOnyen, ProjectStepper projectStepper);

    public void displayOutput();

    public void runProjectsInteractively();

    FinalGradeRecorder getGradeRecorder();

    public GradingFeatureList getGradingFeatures();

    FeatureGradeRecorder getFeatureGradeRecorder();

    public void setGradeRecorder(FinalGradeRecorder gradeRecorder);

    public void setFeatureGradeRecorder(FeatureGradeRecorder featureGradeRecorder);

    SakaiProject runProject(String anOnyen, SakaiProject aProject);

    void resetRunningProject(SakaiProject aProject);

    public FinalGradeRecorder getTotalScoreRecorder();

    public void setTotalScoreRecorder(FinalGradeRecorder newVal);

    public AutoFeedback getAutoFeedback();

    public ManualFeedback getManualFeedback();

    public ScoreFeedback getScoreFeedback();

    public SourceDisplayer getSourceDisplayer();


}
