package gradingTools.assignment8.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;
import util.models.PropertyListenerRegisterer;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/29/13
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShapesRegisterListenerTestCase extends BasicTestCase {


    public ShapesRegisterListenerTestCase() {
        super("Shapes register event listeners test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Look for the classes that implement or interfaces that extend PropertyListenerRegisterer
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        ClassesManager manager = project.getClassesManager().get();
        int count = 0;
        for (ClassDescription description : manager.getClassDescriptions()) {
            if (PropertyListenerRegisterer.class.isAssignableFrom(description.getJavaClass()))
                count++;
        }

        // There should be at least two cases
        if (count >= 2)
            return pass();
        else
            return fail("All atomic shapes should extend/implement PropertyListenerRegisterer");
    }
}
