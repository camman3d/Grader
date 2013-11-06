package framework.grading.testing;

/**
 * All test cases should extend this class.
 * Subclasses will implement the {@link TestCase#test(framework.project.Project, boolean)} method.
 * This method should call and return one of the following helper functions:
 * <ul>
 *     <li>{@link framework.grading.testing.BasicTestCase#pass()}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#pass(String)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#partialPass(double, String)}</li>
 *     <li>{@link framework.grading.testing.BasicTestCase#fail(String)}</li>
 * </ul>
 *
 * An example:
 * <pre>
 * {@code
 * return partialPass(0.5, "Only got half of the points");
 * }
 * </pre>
 */
public abstract class BasicTestCase implements TestCase {

    protected Checkable checkable;
    protected String name;

    public BasicTestCase(String name) {
        this.name = name;
    }

    @Override
    public void setCheckable(Checkable checkable) {
        this.checkable = checkable;
    }

    @Override
    public String getName() {
        return name;
    }

    protected TestCaseResult partialPass(double percentage) {
        return new TestCaseResult(percentage, name);
    }

    protected TestCaseResult partialPass(double percentage, String notes) {
        return new TestCaseResult(percentage, notes, name);
    }

    protected TestCaseResult pass() {
        return new TestCaseResult(true, name);
    }

    protected TestCaseResult pass(String notes) {
        return new TestCaseResult(true, notes, name);
    }

    protected TestCaseResult fail(String notes) {
        return new TestCaseResult(false, notes, name);
    }
}
