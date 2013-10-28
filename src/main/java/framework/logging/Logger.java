package framework.logging;

import framework.grading.testing.CheckResult;

import java.util.List;

/**
 * This interface defines how grade loggers should behave.
 */
public interface Logger {

    /**
     * Each implementing class will define this method, which saves or logs the data to a destination.
     * @param projectName The name of the current project
     * @param userId The ID of the user grade that is being logged
     * @param featureResults The results from grading the features
     * @param restrictionResults The results from grading the restrictions
     * @param comments Additional comments. May be empty
     */
    public void save(String projectName, String userId, List<CheckResult> featureResults,
                     List<CheckResult> restrictionResults, String comments, double gradePercentage);
}
