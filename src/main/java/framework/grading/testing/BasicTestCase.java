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
}
