package framework.logging.recorder;

import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.logging.loggers.Logger;
import framework.utils.GraderSettings;
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
public class ConglomerateRecorder implements FeatureGradeRecorder {

    // Static singleton boilerplate code

    private static ConglomerateRecorder ourInstance = new ConglomerateRecorder();

    public static ConglomerateRecorder getInstance() {
        return ourInstance;
    }

    // Actual definition

    private ProjectRequirements projectRequirements;
    private RecordingSession recordingSession = null;
    private List<Logger> loggers;

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
        save(featureName, score, "");
    }

    public void save(String featureName, double score, String notes) {
        for (CheckResult r : recordingSession.getFeatureResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setScore(score);
                r.setNotes(notes);
                return;
            }
        for (CheckResult r : recordingSession.getRestrictionResults())
            if (r.getTarget().getName().equals(featureName)) {
                r.setScore(score);
                r.setNotes(notes);
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
    }

    public void ensureSession(String onyen) {
        if (recordingSession == null)
            newSession(onyen);
    }

    /*
        The following was added so that the ConglomerateRecorder can word as the recorder within ASakaiProjectDatabase.
        To use it there, just do:
            FeatureGradeRecorderSelector.setFactory(new ConglomerateRecorderFactory());
        Before creating the project database.
     */

    // Feature score setters
    @Override
    public void setGrade(String aStudentName, String anOnyen, String aFeature, double aScore) {
        ensureSession(anOnyen);
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

    // Final score setters
    @Override
    public void setGrade(String aStudentName, String anOnyen, double aScore) {
        ensureSession(anOnyen);
        finish();
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
}
