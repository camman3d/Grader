package gradingTools.assignment1.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TwoMethodTestCase extends BasicTestCase {

    public TwoMethodTestCase() {
        super("Two method test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        int methodCount = 0;
        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            methodCount += description.getJavaClass().getMethods().length;
        }

        if (methodCount >= 2)
            return pass();
        return fail("There should be at least two meaningful methods.");
    }
}
