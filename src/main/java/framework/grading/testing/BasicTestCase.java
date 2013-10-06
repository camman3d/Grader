package framework.grading.testing;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 9:59 AM
 * To change this template use File | Settings | File Templates.
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
