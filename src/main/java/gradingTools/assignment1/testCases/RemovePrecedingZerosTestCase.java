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
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class RemovePrecedingZerosTestCase extends BasicTestCase {

    public RemovePrecedingZerosTestCase() {
        super("Removes preceding zeros test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {
            FlexibleProgramRunner runner = new FlexibleProgramRunner(project, "11 0023 45");

            String output = runner.runWithSpaces();
            if (output.matches("[^0]23"))
                return pass();
            output = runner.runNoSpaces();
            if (output.matches("[^0]23"))
                return pass();
            return fail("Program should remove preceding zeros.");

        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }

    private boolean isValidOutput(String output) {
        String regex = ".*([^0]|^)23.*";
        for (String line : output.split("\n"))
            if (line.matches(regex))
                return true;
        return false;
    }
}
