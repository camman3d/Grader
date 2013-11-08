package gradingTools.assignment2.testCases;

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
public class WordTokensTestCase extends BasicTestCase {

    public WordTokensTestCase() {
        super("Word tokens test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {
            FlexibleProgramRunner runner = new FlexibleProgramRunner(project, "this is 789 test");

            // Check that the program
            String output = runner.runWithSpaces();
            if (isValidOutput(output))
                return pass();
            output = runner.runNoSpaces();
            if (isValidOutput(output))
                return pass();
            return fail("Program should work with word tokens.");

        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }

    private String[] outputParts = new String[] {"this", "is", "789", "test"};

    private boolean isValidOutput(String output) {
        boolean result = true;
        for (String part : outputParts)
            result = result && output.contains(part);
        return result;
    }
}
