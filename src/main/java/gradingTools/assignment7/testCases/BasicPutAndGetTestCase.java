package gradingTools.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.classFinder.ManualClassFinder;
import tools.classFinder.RootTagFinder;
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
public class BasicPutAndGetTestCase extends BasicTestCase {

    public BasicPutAndGetTestCase() {
        super("Basic put and get test case");
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

            // Call it saving something
            putMethod.invoke(table, "test1", "Test1");

            // Get it back
            String s = (String) getMethod.invoke(table, "test1");
            if (s.equals("Test1"))
                return pass();
            return fail("Basic put and get didn't work");
        } catch (InvocationTargetException e) {
            // This means the method threw an error
            return fail("Basic put/get throws an error");
        } catch (Exception e) {
            throw new NotGradableException();
//            if (autoGrade)
//                throw new NotAutomatableException();
//            return ask();
        }
    }

//    private TestCaseResult ask() {
//        int result = JOptionPane.showConfirmDialog(null, "Does put and get save and return values?", "Put method", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if (result == 0)
//            return pass();
//        else
//            return fail("Put method does not save new keys and values.");
//    }
}
