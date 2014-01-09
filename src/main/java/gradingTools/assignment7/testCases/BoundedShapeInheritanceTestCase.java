package gradingTools.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.ClassInheritanceChecker;
import tools.classFinder.ManualClassFinder;
import tools.classFinder.RootTagFinder;
import scala.Option;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedShapeInheritanceTestCase extends BasicTestCase {

    public BoundedShapeInheritanceTestCase() {
        super("Locatable inheritance test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Bounded Shape");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Bounded Shape");
        }
        Class<?> locatableSuperclass = classDescription.get().getJavaClass();

        // There should be at least three classes tagged "bounded shape" (bounded shape, line, image)
        Set<ClassDescription> locatables = project.getClassesManager().get().findByTag("Bounded Shape");
        if (locatables.size() < 3)
            return fail("Expected more classes tagged \"Bounded Shape\"", autoGrade);

        // Make sure that everything that is tagged "bounded shape" extend the locatable class
        int classCount = 0;
        int correctClassCount = 0;
        String notes = "";
        for (ClassDescription description : locatables) {
            classCount++;
            if (ClassInheritanceChecker.isSubclass(description.getJavaClass(), locatableSuperclass))
                correctClassCount++;
            else
                notes += "Class \"" + description.getJavaClass().getSimpleName() + "\" should extend Bounded Shape. ";
        }

        return partialPass(correctClassCount / classCount, notes, autoGrade);
    }
}
