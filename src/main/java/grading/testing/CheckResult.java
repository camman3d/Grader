package grading.testing;

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

    private double score;
    private List<TestCaseResult> results;
    private double pointWeight;

    /**
     * Use this constructor before processing any test results.
     * @param pointWeight How many points each test case is worth.
     */
    public CheckResult(double pointWeight) {
        this.pointWeight = pointWeight;
        this.score = 0;
        results = new ArrayList<TestCaseResult>();
    }

    /**
     * Only use this constructor if you are recreating the result from an existing one.
     * @param score The final score
     * @param results The test results
     */
    public CheckResult(double score, List<TestCaseResult> results) {
        this.score = score;
        this.results = results;
        pointWeight = 0;
    }

    /**
     * @return The total score
     */
    public double getScore() {
        return score;
    }

    /**
     * @return The summary of the results from the test cases
     */
    public String getNoteSummary() {
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
