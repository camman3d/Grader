package grading.testing;

import project.Project;

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

    protected Feature(String name, double points, List<TestCase> testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = false;
        this.testCases = testCases;
    }

    protected Feature(String name, double points, boolean extraCredit, List<TestCase> testCases) {
        this.name = name;
        this.points = points;
        this.extraCredit = extraCredit;
        this.testCases = testCases;
    }

    public String getName() {
        return name;
    }

    public double getPoints() {
        return points;
    }

    public boolean isExtraCredit() {
        return extraCredit;
    }

    @Override
    public CheckResult check(Project project) {
        return check(points, testCases, project);
    }
}
