package testObjects;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.Checkable;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

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
    public TestCaseResult test(Project project, boolean autoGrade) {
        return new TestCaseResult(percentage, notes, name);
    }

    public Checkable getChecker() {
        return checkable;
    }
}
