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
public class Feature extends Checkable {

    private String name;
    private double points;
    private boolean extraCredit;
    private List<TestCase> testCases;

    public Feature(String name, double points, List<TestCase> testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = false;
        this.testCases = testCases;
    }

    public Feature(String name, double points, boolean extraCredit, List<TestCase> testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = extraCredit;
        this.testCases = testCases;
    }

    public Feature(String name, double points, TestCase ... testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = false;
        this.testCases = Arrays.asList(testCases);
    }

    public Feature(String name, double points, boolean extraCredit, TestCase ... testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = extraCredit;
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

    public boolean isExtraCredit() {
        return extraCredit;
    }

    @Override
    public CheckResult check(Project project, boolean autoMode) {
        return check(points, testCases, project, autoMode);
    }

    @Override
    public String getSummary() {
        String spaces = "                                 ";
        String score = points < 10 ? " " + points : points + "";
        String ec = extraCredit ? " (Extra credit)" : "";
        return name + spaces.substring(name.length()) + "%.1f / " + score + ec;
    }
}
