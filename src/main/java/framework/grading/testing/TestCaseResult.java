package framework.grading.testing;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseResult {

    private String notes;
    private double percentage;
    private String name;

    /**
     * @param percentage The percentage (0 to 1), meaning how much of the test case they passed
     * @param name       The name of the test case
     */
    public TestCaseResult(double percentage, String name) {
        this.percentage = Math.min(Math.max(percentage, 0), 1);
        notes = "";
        this.name = name;
    }

    /**
     * @param percentage The percentage (0 to 1), meaning how much of the test case they passed
     * @param notes      Notes about the result
     * @param name       The name of the test case
     */
    public TestCaseResult(double percentage, String notes, String name) {
        this.percentage = Math.min(Math.max(percentage, 0), 1);
        this.notes = notes;
        this.name = name;
    }

    /**
     * @param passed Whether the user passed the test case
     * @param name   The name of the test case
     */
    public TestCaseResult(boolean passed, String name) {
        this.percentage = passed ? 1 : 0;
        notes = "";
        this.name = name;
    }

    /**
     * @param passed Whether the user passed the test case
     * @param notes  Notes about the result
     * @param name   The name of the test case
     */
    public TestCaseResult(boolean passed, String notes, String name) {
        this.percentage = passed ? 1 : 0;
        this.notes = notes;
        this.name = name;
    }

    /**
     * @return The percentage of the test case that passed
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * @return Any notes about the result
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @return The name of the test case
     */
    public String getName() {
        return name;
    }
}
