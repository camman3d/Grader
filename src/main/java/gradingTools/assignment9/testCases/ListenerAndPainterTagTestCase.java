package gradingTools.assignment9.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListenerAndPainterTagTestCase extends BasicTestCase {
    public ListenerAndPainterTagTestCase() {
        super("Paint Listener and Observable Painter tag test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<ClassDescription> class1 = project.getClassesManager().get().findByTag("Paint Listener");
        Set<ClassDescription> class2 = project.getClassesManager().get().findByTag("Observable Painter");
        if (class1.isEmpty()) {
            if (class2.isEmpty())
                return fail("Neither paint listener nor observable painter are tagged.");
            else
                return partialPass(0.5, "Paint listener is not tagged.");
        }
        if (class2.isEmpty())
            return partialPass(0.5, "Observable painter is not tagged.");
        return pass();
    }
}
