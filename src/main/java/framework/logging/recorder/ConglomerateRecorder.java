package framework.logging.recorder;

import framework.grading.FrameworkProjectRequirements;
import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCaseResult;
import framework.logging.loggers.Logger;
import framework.utils.GraderSettings;
import grader.assignment.GradingFeature;
import grader.feedback.AutoFeedback;
import grader.feedback.ManualFeedback;
import grader.spreadsheet.FeatureGradeRecorder;
import tools.DirectoryUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConglomerateRecorder implements FeatureGradeRecorder, AutoFeedback, ManualFeedback {

    // Static singleton boilerplate code

    private static ConglomerateRecorder ourInstance = new ConglomerateRecorder();

    public static ConglomerateRecorder getInstance() {
        return ourInstance;
    }

    // Actual definition

    private ProjectRequirements projectRequirements;
    private RecordingSession recordingSession = null;
    private List<Logger> loggers;
    private String featureComments;
    private List<TestCaseResult> featureResults;

    private ConglomerateRecorder() {
        loggers = new ArrayList<Logger>();
    }

    public ProjectRequirements getProjectRequirements() {
        return projectRequirements;
    }

    public void setProjectRequirements(ProjectRequirements projectRequirements) {
        this.projectRequirements = projectRequirements;
    }

    public void addLogger(Logger logger) {
        loggers.add(logger);
    }

    // Recording methods

    public void save(List<CheckResult> results) {
        for (CheckResult result : results)
            save(result);
    }

    public void save(CheckResult result) {
        for (CheckResult r : recordingSession.getFeatureResults())
            if (r.getTarget() == result.getTarget()) {
                r.setScore(result.getScore());
                r.setNotes(result.getNotes());
                r.setResults(result.getResults());
                return;
            }
        for (CheckResult r : recordingSession.getRestrictionResults())
            if (r.getTarget() == result.getTarget()) {
                r.setScore(result.getScore());
                r.setNotes(result.getNotes());
                r.setResults(result.getResults());
                return;
            }
    }

    public void save(String comments) {
        recordingSession.setComments(comments);
    }

    public void save(double gradePercentage) {
        recordingSession.setLatePenalty(gradePercentage);
    }

    public void save(String featureName, double score) {
        for (CheckResult r : recordingSession.getFeatureResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setScore(score);
                return;
            }
        for (CheckResult r : recordingSession.getRestrictionResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setScore(score);
                return;
            }
    }

    public void save(String featureName, String notes) {
        for (CheckResult r : recordingSession.getFeatureResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setNotes(notes);
                return;
            }
        for (CheckResult r : recordingSession.getRestrictionResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setNotes(notes);
                return;
            }
    }

    public void save(String featureName, List<TestCaseResult> results) {
        for (CheckResult r : recordingSession.getFeatureResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setResults(results);
                return;
            }
        for (CheckResult r : recordingSession.getRestrictionResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setResults(results);
                return;
            }
    }

    // Session methods

    public void newSession(final String onyen) {

        // Get the user id from the onyen
        String userId = DirectoryUtils.find(new File(GraderSettings.get().get("path")), new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().contains("(" + onyen + ")");
            }
        }).get().getName();

        // Create empty results. Don't worry, they'll be filled in later
        List<CheckResult> featureResults = new ArrayList<CheckResult>();
        for (Feature feature : projectRequirements.getFeatures())
            featureResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, feature));
        List<CheckResult> restrictionResults = new ArrayList<CheckResult>();
        for (Restriction restriction : projectRequirements.getRestrictions())
            featureResults.add(new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, restriction));

        recordingSession = new RecordingSession(userId, featureResults, restrictionResults, "", 1);

    }

    public void finish() {
        for (Logger logger : loggers)
            logger.save(recordingSession);
        recordingSession = null;
        featureComments = "";
    }

    /*
        The following was added so that the ConglomerateRecorder can work as the recorder within ASakaiProjectDatabase.
        To use it there, just do:
            FeatureGradeRecorderSelector.setFactory(new ConglomerateRecorderFactory());
        Before creating the project database.
     */

    /**
     * Feature score setter.
     * This is needed so that when setScore or pureSetScore are called it comes here.
     */
    @Override
    public void setGrade(String aStudentName, String anOnyen, String aFeature, double aScore) {
        checkSession(anOnyen);
        save(aFeature, aScore);
    }

    /**
     * The ConglomerateRecorder is for recording only. This will return 0 always.
     * @deprecated Don't use this. Write only
     */
    @Override
    @Deprecated
    public double getGrade(String aStudentName, String anOnyen, String aFeature) {
        return 0;
    }

    /**
     * This method is supposed to save the total score, but the conglomerate recorder only saves and writes things out
     * when the {@link #finish()} method is called. The {@link wrappers.grader.sakai.project.ProjectStepperDisplayerWrapper} calls
     * finish so it's ok that this is empty.
     */
    @Override
    public void setGrade(String aStudentName, String anOnyen, double aScore) {
        checkSession(anOnyen);
    }

    /**
     * The ConglomerateRecorder is for recording only. This will return 0 always.
     * @deprecated Don't use this. Write only
     */
    @Override
    @Deprecated
    public double getGrade(String aStudentName, String anOnyen) {
        return 0;
    }

    private void checkSession(String onyen) {
        if (recordingSession == null)
            newSession(onyen);
        else if (!recordingSession.getUserId().contains("(" + onyen + ")")) {
            finish();
            newSession(onyen);
        }
    }

    /*
        The following was added so that auto feedback gets mixed into the score.
     */

    @Override
    public void recordAutoGrade(GradingFeature aGradingFeature, grader.checkers.CheckResult result) {
        System.out.println("TODO: recordAutoGrade");
    }

    /*
        The following was added so that manual feedback is used.
     */

    @Override
    public void comment(GradingFeature aGradingFeature) {
        // Instead of asking the user, pull it from a variable which is updated.
        save(aGradingFeature.getFeature(), featureComments);
        if (featureResults != null)
            save(aGradingFeature.getFeature(), featureResults);
    }

    public void setFeatureComments(String comments) {
        featureComments = comments;
    }

    public void setFeatureResults(List<TestCaseResult> results) {
        featureResults = results;
    }
}
