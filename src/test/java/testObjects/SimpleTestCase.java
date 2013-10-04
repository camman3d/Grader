package testObjects;

import grading.testing.BasicTestCase;
import grading.testing.Checkable;
import grading.testing.TestCaseResult;
import project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTestCase extends BasicTestCase {

    private String notes;
    private double percentage;

    public SimpleTestCase(String name) {
        this(1, "", name);
    }

    public SimpleTestCase(double percentage, String notes, String name) {
        super(name);
        this.percentage = percentage;
        this.notes = notes;
    }

    @Override
    public TestCaseResult test(Project project) {
        return new TestCaseResult(percentage, notes, name);
    }

    public Checkable getChecker() {
        return checkable;
    }
}
