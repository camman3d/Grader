package framework.updates;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/21/14
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UpdatePatch {
    public void patch();
    public int getOrder();
}
