package gradingTools.example.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.ClassesManager;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 8:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonInterfaceTestCase extends BasicTestCase {

    public CommonInterfaceTestCase() {
        super("Common interface test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // You can get the classes and information about them from the project using the ClassesManager
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        ClassesManager manager = project.getClassesManager().get();

        // Here we are looking for an interface, so we'll use reflection to do that
        Class<?> theInterface = null;
        for (ClassDescription description : manager.getClassDescriptions()) {
            if (description.getJavaClass().isInterface())
                theInterface = description.getJavaClass();
        }

        // Then we can do checks based on the result. We want to see if there are at least two classes that implement
        // the interface we found.
        if (theInterface == null)
            return fail("Couldn't find an interface", autoGrade);
        double classCount = 0;
        for (ClassDescription description : manager.getClassDescriptions()) {
            Class<?> _class = description.getJavaClass();
            if (!_class.isInterface() && theInterface.isAssignableFrom(_class))
                classCount++;
        }

        // You can even give partial credit
        if (classCount >= 2)
            return pass(autoGrade);
        return partialPass(0.5, "Only found one class that implemented the interface", autoGrade);
    }
}
