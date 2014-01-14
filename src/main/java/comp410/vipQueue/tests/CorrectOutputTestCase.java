package comp410.vipQueue.tests;

import framework.execution.NotRunnableException;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/13/14
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class CorrectOutputTestCase extends BasicTestCase {

    public CorrectOutputTestCase() {
        super("Correct output test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {

            String output = project.launch("1\n2\n3\n4\n5\n", 2000).await();
            if (output.contains("->25->16->9->4->1->1->2->3->4->5"))
                return pass();
            else
                return fail("Invalid output");
        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }
}
