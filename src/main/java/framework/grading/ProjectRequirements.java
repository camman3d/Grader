package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import framework.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectRequirements {

    private List<Feature> features;
    private List<Restriction> restrictions;

    public ProjectRequirements() {
        features = new ArrayList<Feature>();
        restrictions = new ArrayList<Restriction>();
    }

    public ProjectRequirements(List<Feature> features, List<Restriction> restrictions) {
        this.features = features;
        this.restrictions = restrictions;
    }

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void addFeature(String name, double points, List<TestCase> testCases) {
        addFeature(new Feature(name, points, testCases));
    }

    public void addFeature(String name, double points, boolean extraCredit, List<TestCase> testCases) {
        addFeature(new Feature(name, points, extraCredit, testCases));
    }

    public void addFeature(String name, double points, TestCase ... testCases) {
        addFeature(new Feature(name, points, testCases));
    }

    public void addFeature(String name, double points, boolean extraCredit, TestCase ... testCases) {
        addFeature(new Feature(name, points, extraCredit, testCases));
    }

    public void addRestriction(Restriction restriction) {
        restrictions.add(restriction);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public List<CheckResult> checkFeatures(Project project) {
        List<CheckResult> results = new LinkedList<CheckResult>();
        for (Feature feature : features)
            results.add(feature.check(project));
        return results;
    }

    public List<CheckResult> checkRestrictions(Project project) {
        List<CheckResult> results = new LinkedList<CheckResult>();
        for (Restriction restriction : restrictions)
            results.add(restriction.check(project));
        return results;
    }

}
