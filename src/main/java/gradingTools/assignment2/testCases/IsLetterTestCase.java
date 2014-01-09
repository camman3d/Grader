package gradingTools.assignment2.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class IsLetterTestCase extends BasicTestCase {
    public IsLetterTestCase() {
        super("Custom isLetter method test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            try {
                Method m1 = description.getJavaClass().getMethod("isLetter", char.class);
                if (m1.getReturnType() == boolean.class)
                    return pass(autoGrade);
            } catch (NoSuchMethodException e) {
                // Move along
            }
        }
        return fail("No boolean isLetter(char) method found.", autoGrade);
    }
}
