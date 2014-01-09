package gradingTools.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/22/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class CmdIntTagTestCase extends BasicTestCase {


    public CmdIntTagTestCase() {
        super("Command interpreter tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<ClassDescription> classes = project.getClassesManager().get().findByTag("Command Interpreter");
        if (classes.size() == 1)
            return pass(autoGrade);
        return fail("There should be one class tagged \"Command Interpreter\"", autoGrade);
    }
}
