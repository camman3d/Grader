package framework.grading.testing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckResult {

    public enum CheckStatus {Successful, NotGraded, Failed}

    private double score;
    private List<TestCaseResult> results;
    private String notes;
    private double pointWeight;
    private CheckStatus status;

    /**
     * Use this constructor before processing any test results.
     * If you are going to change the values, use the setters.
     * @param pointWeight How many points each test case is worth.
     */
    public CheckResult(double pointWeight) {
        this.pointWeight = pointWeight;
        this.score = 0;
        results = new ArrayList<TestCaseResult>();
        notes = "";
        status = CheckStatus.NotGraded;
    }

    /**
     * Use this constructor to create "failed" results
     * @param score The score to set
     * @param notes General notes
     * @param status The final status
     */
    public CheckResult(double score, String notes, CheckStatus status) {
        this.score = score;
        this.notes = notes;
        this.status = status;
    }

    /**
     * @return The total score
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the total score
     * @param score The score to set
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * @return General notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets general notes about the check
     * @param notes The notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return The grading status
     */
    public CheckStatus getStatus() {
        return status;
    }

    /**
     * Sets the grading status.
     * @param status The new status
     */
    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    /**
     * @return The summary of the results from the test cases
     */
    public String getResultNotes() {
        String summary = "";
        for (TestCaseResult result : results) {
            if (!result.getNotes().isEmpty())
                summary += " --- From test case \"" + result.getName() + "\" ---\n" + result.getNotes() + "\n\n";
        }
        return summary;
    }

    /**
     * @return The detailed results from the test cases.
     */
    public List<TestCaseResult> getResults() {
        return results;
    }

    /**
     * Saves a result and updates the score based on that result.
     * @param result The result to save and process
     */
    public void save(TestCaseResult result) {
        score += result.getPercentage() * pointWeight;
        results.add(result);
    }
}
