package gradingTools.example.testCases;

import framework.execution.NotRunnableException;
import framework.execution.RunningProject;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

public class ForwardPrinterTestCase extends BasicTestCase {

    public ForwardPrinterTestCase() {
        super("Forward printer test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        try {

            RunningProject runningProject = project.launch("Hello world\n");
            String output = runningProject.await();
            if (output.contains("Hello world"))
                return pass();
            else
                return fail("Did not print out normally");

        } catch (NotRunnableException e) {
            throw new NotGradableException();
        }
    }
}
