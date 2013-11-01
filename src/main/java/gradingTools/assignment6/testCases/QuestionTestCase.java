package gradingTools.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/8/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionTestCase extends BasicTestCase {

    private String question;

    public QuestionTestCase(String question, String name) {
        super(name);
        this.question = question;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException {
        // Determine is we are running in auto mode
        if (autoGrade)
            throw new NotAutomatableException();

        int result = JOptionPane.showConfirmDialog(null, question, name, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == 0)
            return pass();
        else {
            String notes = JOptionPane.showInputDialog(null, "Why not?", "Explanation needed", JOptionPane.QUESTION_MESSAGE);
            return fail(notes);
        }
    }
}
