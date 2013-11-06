package framework.grading.testing;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents the result of doing some "check" on the project.
 */
@JsonIgnoreProperties({"status"})
public class CheckResult implements Describable {

    public enum CheckStatus {Successful, NotGraded, Failed}

    // Values that we will want later
    private double score;
    private List<TestCaseResult> results;
    private String notes;
    private Gradable target;

    // Values for grading, not needed later
    private CheckStatus status;
    private double pointWeight;

    /**
     * Use this constructor before processing any test results.
     * If you are going to change the values, use the setters.
     * @param pointWeight How many points each test case is worth.
     * @param target The gradable feature/restriction
     */
    public CheckResult(double pointWeight, Gradable target) {
        this.pointWeight = pointWeight;
        this.target = target;
        score = 0;
        results = new ArrayList<TestCaseResult>();
        notes = "";
        status = CheckStatus.NotGraded;
    }

    /**
     * Use this constructor to create custom results
     * @param score The score to set
     * @param notes General notes
     * @param status The final status
     * @param target The gradable feature/restriction
     */
    public CheckResult(double score, String notes, CheckStatus status, Gradable target) {
        this.score = score;
        this.notes = notes;
        this.status = status;
        this.target = target;

        results = new ArrayList<TestCaseResult>();
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
     * @return The detailed results from the test cases.
     */
    public List<TestCaseResult> getResults() {
        return results;
    }

    public void setResults(List<TestCaseResult> results) {
        this.results = results;
    }

    /**
     * @return The gradable Feature/Restriction
     */
    public Gradable getTarget() {
        return target;
    }

    public void setTarget(Gradable target) {
        this.target = target;
    }

    /**
     * Saves a result and updates the score based on that result.
     * @param result The result to save and process
     */
    public void save(TestCaseResult result) {
        score += result.getPercentage() * pointWeight;
        results.add(result);
    }

    @Override
    public String getSummary() {
        String summary = "";
        for (TestCaseResult result : results) {
            if (!result.getNotes().isEmpty())
                summary += "\n * From test case \"" + result.getName() + "\": " + result.getNotes();
        }
        if (!notes.isEmpty())
            summary += "\n * Other notes: " + notes;
        if (summary.isEmpty())
            return summary;
        else
            return "Notes about " + target.getName() + ":" + summary;
    }

}
