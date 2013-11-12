package framework.logging;

import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.logging.Logger;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Integrate feedback here
 */
public class ConglomerateRecorder implements FeatureGradeRecorder, Logger {

    private List<Logger> loggers = new ArrayList<Logger>();
    private List<FeatureGradeRecorder> recorders = new ArrayList<FeatureGradeRecorder>();
    private ProjectRequirements requirements;

    public ConglomerateRecorder(ProjectRequirements requirements) {
        this.requirements = requirements;
    }

    public void addLogger(Logger logger) {
        loggers.add(logger);
    }

    public void addRecorder(FeatureGradeRecorder recorder) {
        recorders.add(recorder);
    }

    /**
     * This save the grade for a particular feature
     * @param aStudentName The student's name
     * @param anOnyen The student's onyen
     * @param aFeature The name of the feature
     * @param aScore The score to be set
     */
    @Override
    public void setGrade(String aStudentName, String anOnyen, String aFeature, double aScore) {
        for (FeatureGradeRecorder recorder : recorders)
            recorder.setGrade(aStudentName, anOnyen, aFeature, aScore);
        // TODO: Call the loggers
    }

    /**
     * @deprecated The conglomerate recorder is to be used for saving only. This will always return zero.
     */
    @Override
    @Deprecated
    public double getGrade(String aStudentName, String anOnyen, String aFeature) {
        return 0;
    }

    /**
     * Sets the final grade
     * @param aStudentName The student's name
     * @param anOnyen The student's onyen
     * @param aScore The final score to be set
     */
    @Override
    public void setGrade(String aStudentName, String anOnyen, double aScore) {
        for (FeatureGradeRecorder recorder : recorders)
            recorder.setGrade(aStudentName, anOnyen, aScore);
        // TODO: Call the loggers
    }

    /**
     * @deprecated The conglomerate recorder is to be used for saving only. This will always return zero.
     */
    @Override
    @Deprecated
    public double getGrade(String aStudentName, String anOnyen) {
        return 0;
    }

    public SakaiProjectDatabase getSakaiProjectDatabase() {
        return aSakaiProjectDatabase;
    }

    public void setaSakaiProjectDatabase(SakaiProjectDatabase aSakaiProjectDatabase) {
        this.aSakaiProjectDatabase = aSakaiProjectDatabase;
    }

    @Override
    public void save(String projectName, String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments, double gradePercentage) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
