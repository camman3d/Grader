package grader.assignment.testObjects;

import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.feedback.AutoFeedback;
import grader.feedback.ManualFeedback;
import grader.feedback.ScoreFeedback;
import grader.feedback.SourceDisplayer;
import grader.sakai.BulkAssignmentFolder;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.ProjectStepper;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class GoodFauxProjectDatabase implements SakaiProjectDatabase {

    private List<GradingFeature> gradingFeatures;

    public GoodFauxProjectDatabase(String path) {
        // TODO: Set up the root folder
        gradingFeatures = new ArrayList<GradingFeature>();
    }

    public void addGradingFeature(GradingFeature feature) {
        gradingFeatures.add(feature);
    }




    @Override
    public BulkAssignmentFolder getBulkAssignmentFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AssignmentDataFolder getAssigmentDataFolder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SakaiProject getProject(String aName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Set<String> getOnyens() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<SakaiProject> getProjects() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SakaiProject runProject(String anOnyen) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runProjectInteractively(String anOnyen) {

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runProjectInteractively(String anOnyen, ProjectStepper projectStepper) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void displayOutput() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void runProjectsInteractively() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FinalGradeRecorder getGradeRecorder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GradingFeatureList getGradingFeatures() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FeatureGradeRecorder getFeatureGradeRecorder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setGradeRecorder(FinalGradeRecorder gradeRecorder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setFeatureGradeRecorder(FeatureGradeRecorder featureGradeRecorder) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SakaiProject runProject(String anOnyen, SakaiProject aProject) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resetRunningProject(SakaiProject aProject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FinalGradeRecorder getTotalScoreRecorder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setTotalScoreRecorder(FinalGradeRecorder newVal) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AutoFeedback getAutoFeedback() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ManualFeedback getManualFeedback() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ScoreFeedback getScoreFeedback() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SourceDisplayer getSourceDisplayer() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
