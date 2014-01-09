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

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PutExistingTestCase extends BasicTestCase {

    public PutExistingTestCase() {
        super("Put existing key test case");
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

            // Call it saving something
            method.invoke(table, "test1", "Test1");
            method.invoke(table, "test1", "Test2");

            // It's hard to check exactly how it worked. This is making an assumption on the implementation
            // Get an array property
            Field[] fields = _class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(ArrayList.class)) {
                    int size = ((ArrayList) field.get(table)).size();
                    if (size == 1)
                        pass(autoGrade);
                    fail("There should only be one item in the array list", autoGrade);
                }
            }

            // So, they didn't do an array list. We'll have to ask
            if (autoGrade)
                throw new NotAutomatableException();
            return ask();
        } catch (Exception e) {
            if (autoGrade)
                throw new NotAutomatableException();
            return ask();
        }
    }

    private TestCaseResult ask() {
        int result = JOptionPane.showConfirmDialog(null, "Does the put method overwrite values with an existing key?", "Put method", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == 0)
            return pass(false);
        else
            return fail("Put method does not overwrite values with existing keys.", false);
    }
}
