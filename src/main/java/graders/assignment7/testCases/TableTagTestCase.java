package graders.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TableTagTestCase extends BasicTestCase {

    public TableTagTestCase() {
        super("Table tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // There should be one class tagged with "Table"
        if (project.getClassesManager().get().findByTag("Table").size() == 1)
            return pass();
        else
            return fail("There should be one class tagged with \"Table\".");
    }
}
