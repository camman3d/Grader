package gradingTools.assignment8.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleViewTagTestCase extends BasicTestCase {
    public ConsoleViewTagTestCase() {
        super("Console view tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        if (project.getClassesManager().get().findByTag("Console Scene View").isEmpty())
            return fail("No class found with tag \"Console Scene View\"", autoGrade);
        else
            return pass(autoGrade);
    }
}
