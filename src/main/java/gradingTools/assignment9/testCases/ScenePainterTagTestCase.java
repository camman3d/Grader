package gradingTools.assignment9.testCases;

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
public class ScenePainterTagTestCase extends BasicTestCase {
    public ScenePainterTagTestCase() {
        super("Scene painter tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        if (project.getClassesManager().get().findByTag("Inheriting Bridge Scene Painter").isEmpty())
            return fail("No class found with tag \"Inheriting Bridge Scene Painter\"", autoGrade);
        else
            return pass(autoGrade);
    }
}
