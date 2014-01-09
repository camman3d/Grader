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
public class PutNullTestCase extends BasicTestCase {

    public PutNullTestCase() {
        super("Put null key/value test case");
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
            // Get the put method
            Class<?> _class = classDescription.get().getJavaClass();
            Method method = _class.getMethod("put", String.class, Object.class);
            Object table = _class.newInstance();

            // Call it saving null a key and value
            method.invoke(table, null, "Test1");
            method.invoke(table, "test1", null);

            // If we got here then it didn't blow up, meaning it worked
            return pass(autoGrade);
        } catch (InvocationTargetException e) {
            // This means the method threw an error
            return fail("Putting with keys & values of null throws an error.", autoGrade);
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
