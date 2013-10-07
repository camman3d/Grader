package framework.grading.testing;

import framework.project.Project;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
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
     * @return The result of the restriction checks
     */
    @Override
    public CheckResult check(Project project) {
        CheckResult result = check(-points, testCases, project);
        result.setScore(result.getScore() + points);
        return result;
    }

    @Override
    public String getSummary() {
        String spaces = "                                 ";
        String score = points < 10 ? " " + points : points + "";
        return name + spaces.substring(name.length()) + "%.1f / " + score;
    }
}
