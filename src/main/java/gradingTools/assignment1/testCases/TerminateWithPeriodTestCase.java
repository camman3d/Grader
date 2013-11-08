package gradingTools.assignment1.testCases;

import framework.execution.NotRunnableException;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import gradingTools.assignment1.FlexibleProgramRunner;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class TerminateWithPeriodTestCase extends BasicTestCase {

    public TerminateWithPeriodTestCase() {
        super("Terminates with period test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {
            // First check that no period keeps it running
            FlexibleProgramRunner runner1 = new FlexibleProgramRunner(project, "19 28", false);
            String output = runner1.runWithSpaces();
            String output2 = runner1.runNoSpaces();
            if (!output.contains("*** TIMEOUT ***") && !output2.contains("*** TIMEOUT ***"))
                return fail("Program terminates prematurely");

            // Check that a period terminates it
            FlexibleProgramRunner runner2 = new FlexibleProgramRunner(project, "19 28");

            output = runner2.runWithSpaces();
            if (!output.contains("*** TIMEOUT ***"))
                return pass();
            output = runner2.runNoSpaces();
            if (!output.contains("*** TIMEOUT ***"))
                return pass();
            return fail("Program should terminate with period.");

        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }
}
