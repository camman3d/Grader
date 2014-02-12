package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import framework.project.Project;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import tools.classFinder2.MethodDescription;

import java.util.*;

/**
 * This is the fundamental container which holds all the features and restrictions on which programs are graded.
 */
public class FrameworkProjectRequirements implements ProjectRequirements {

    private List<Feature> features;
    private List<Restriction> restrictions;

    private List<DueDate> dueDates = new ArrayList<DueDate>();

    private Set<String> neededClasses = new HashSet<String>();
    private Map<String, Set<MethodDescription>> neededMethods = new HashMap<String, Set<MethodDescription>>();

    /**
     * It's important that there is a nullary constructor because this needs to be able to be simply instantiated via
     * reflection.
     */
    public FrameworkProjectRequirements() {
        features = new ArrayList<Feature>();
        restrictions = new ArrayList<Restriction>();
    }

    // Feature adding methods

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

    public void addFeature(String name, String description, double points, TestCase ... testCases) {
        addFeature(new Feature(name, description, points, testCases));
    }

    public void addFeature(String name, String description, double points, boolean extraCredit, TestCase ... testCases) {
        addFeature(new Feature(name, description, points, extraCredit, testCases));
    }

    // Restriction adding methods

    public void addRestriction(Restriction restriction) {
        restrictions.add(restriction);
    }

    public void addRestriction(String name, double points, TestCase ... testCases) {
        addRestriction(new Restriction(name, points, testCases));
    }

    public void addRestriction(String name, String description, double points, TestCase ... testCases) {
        addRestriction(new Restriction(name, description, points, testCases));
    }

    public void addRestriction(String name, double points, List<TestCase> testCases) {
        addRestriction(new Restriction(name, points, testCases));
    }

    // Due date adding methods

    public void addDueDate(DateTime dateTime, double percentage) {
        dueDates.add(new DueDate(dateTime, percentage));
    }

    public void addDueDate(String dateTime, double percentage) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        DateTime dt = formatter.parseDateTime(dateTime);
        addDueDate(dt, percentage);
    }

    // Getters

    public List<Feature> getFeatures() {
        return features;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    // Grading methods

    /**
     * Given a project, this checks all the features against it.
     * @param project The project to check, or grade.
     * @return A list of {@link CheckResult} corresponding to the features
     */
    public List<CheckResult> checkFeatures(Project project) {
        List<CheckResult> results = new LinkedList<CheckResult>();
        for (Feature feature : features)
            results.add(feature.check(project));
        return results;
    }

    /**
     * Given a project, this checks all the restrictions against it.
     * @param project The project to check, or grade.
     * @return A list of {@link CheckResult} corresponding to the restrictions
     */
    public List<CheckResult> checkRestrictions(Project project) {
        List<CheckResult> results = new LinkedList<CheckResult>();
        for (Restriction restriction : restrictions)
            results.add(restriction.check(project));
        return results;
    }

    /**
     * Given a due date, this figures out what score modifier (a percentage) should be given.
     * @param dateTime The submission time of the project
     * @return A score modifier percentage
     */
    public double checkDueDate(DateTime dateTime) {
        if (dueDates.isEmpty())
            return 1;
        double percentage = 0;
        for (DueDate dueDate : dueDates) {
            if (dueDate.getCutoffDate().isAfter(dateTime))
                percentage = Math.max(percentage, dueDate.getPercentage());
        }
        return percentage;
    }

    @Override
    public void registerNeededClass(String className) {
        neededClasses.add(className);
    }

    @Override
    public void registerNeededMethod(String className, MethodDescription methodDescription) {
        if (neededMethods.containsKey(className))
            neededMethods.get(className).add(methodDescription);
        else {
            Set<MethodDescription> methods = new HashSet<MethodDescription>();
            methods.add(methodDescription);
            neededMethods.put(className, methods);
        }
    }

    @Override
    public void registerNeededMethods(String className, MethodDescription... methodDescriptions) {
        for (MethodDescription methodDescription : methodDescriptions)
            registerNeededMethod(className, methodDescription);
    }

    @Override
    public Set<String> getNeededClasses() {
        return neededClasses;
    }

    @Override
    public Map<String, Set<MethodDescription>> getNeededMethods() {
        return neededMethods;
    }
}
