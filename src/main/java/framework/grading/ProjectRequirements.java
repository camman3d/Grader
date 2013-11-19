package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import framework.project.Project;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectRequirements {

    // Methods for adding features
    public void addFeature(Feature feature);

    public void addFeature(String name, double points, List<TestCase> testCases);

    public void addFeature(String name, double points, boolean extraCredit, List<TestCase> testCases);

    public void addFeature(String name, double points, TestCase... testCases);

    public void addFeature(String name, double points, boolean extraCredit, TestCase... testCases);

    // Methods for adding restrictions
    public void addRestriction(Restriction restriction);

    public void addRestriction(String name, double points, TestCase... testCases);

    public void addRestriction(String name, double points, List<TestCase> testCases);

    // Methods for adding due dates
    public void addDueDate(DateTime dateTime, double percentage);

    public void addDueDate(String dateTime, double percentage);

    // Getters
    public List<Feature> getFeatures();

    public List<Restriction> getRestrictions();

    // Grading methods
    public List<CheckResult> checkFeatures(Project project);

    public List<CheckResult> checkRestrictions(Project project);

    public double checkDueDate(DateTime dateTime);

}
