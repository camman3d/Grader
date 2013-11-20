package framework.grading.testing;

import framework.project.Project;

import java.util.Arrays;
import java.util.List;

/**
 * Restrictions are aspects of the program that it "should not" have.
 * @see Feature
 */
public class Restriction extends Checkable {

    private String name;
    private double points;
    private List<TestCase> testCases;

    public Restriction(String name, double points, List<TestCase> testCases) {
        this.name = name;
        this.points = Math.min(points, -points); // Make sure we are negative
        this.testCases = testCases;
    }

    public Restriction(String name, double points, TestCase ... testCases) {
        this.name = name;
        this.points = Math.min(points, -points); // Make sure we are negative
        this.testCases = Arrays.asList(testCases);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPoints() {
        return points;
    }

    /**
     * Checks the project for restrictions. Negative points are given if violated.
     *
     * @param project The project to check
     * @param autoMode Whether or not we are "auto-grading"
     * @return The result of the restriction checks
     */
    @Override
    public CheckResult check(Project project, boolean autoMode) {
        CheckResult result = check(-points, testCases, project, autoMode);
        result.setScore(result.getScore() + points);
        return result;
    }

    @Override
    public String getSummary() {
        String spaces = "                                            ";
        String score = points < 10 ? " " + points : points + "";
        return name + spaces.substring(name.length()) + "%.1f / " + score;
    }
}
