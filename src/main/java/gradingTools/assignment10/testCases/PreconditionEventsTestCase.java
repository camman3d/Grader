package gradingTools.assignment10.testCases;

import bus.uigen.trace.EditorGenerationStarted;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 10:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class PreconditionEventsTestCase extends BasicTestCase {
    public PreconditionEventsTestCase() {
        super("Precondition events test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getTraceableLog().getLog().isEmpty()) {
            if (!autoGrade)
                JOptionPane.showMessageDialog(null, "Please run (not launch) the program before running this check.", "Execution results needed", JOptionPane.PLAIN_MESSAGE);
            throw new NotAutomatableException();
        }



        // Search the log for events
        for (Exception e : project.getTraceableLog().getLog())
            System.out.println(e);

        return pass(autoGrade);
    }
}
