package graders.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import graders.assignment7.tools.ManualClassFinder;
import graders.assignment7.tools.RootTagFinder;
import scala.Option;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetNullTestCase extends BasicTestCase {

    public GetNullTestCase() {
        super("Get null key test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Table");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Table");
        }

        try {
            Class<?> _class = classDescription.get().getJavaClass();
            Object table = _class.newInstance();

            // Get the put and get methods
            Method putMethod = _class.getMethod("put", String.class, Object.class);
            Method getMethod = _class.getMethod("get", String.class);

            putMethod.invoke(table, "a", "b");
            Object r = getMethod.invoke(table, new Object[]{null});

            if (r == null)
                return pass();
            else
                return fail("Getting w/ null shouldn't return anything.");
        } catch (InvocationTargetException e) {
            // This means the method threw an error
            return fail("Getting w/ null throws an error");
        } catch (Exception e) {
            throw new NotGradableException();
        }
    }

//    private TestCaseResult ask() {
//        int result = JOptionPane.showConfirmDialog(null, "Does the put method save nothing when the key/value is null?", "Put method", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if (result == 0)
//            return pass();
//        else
//            return fail("Put method should save nothing when key/value is null.");
//    }
}
