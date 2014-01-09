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

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedShapeExtendsLocatableTestCase extends BasicTestCase {

    public BoundedShapeExtendsLocatableTestCase() {
        super("Bounded shape extends locatable test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Get the bounded shape "root" class
        Option<ClassDescription> boundedShapeDescription = new RootTagFinder(project).findClass("Bounded Shape");
        if (boundedShapeDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            boundedShapeDescription = ManualClassFinder.find(project, "Bounded Shape");
        }

        // Get the locatable "root" class
        Option<ClassDescription> locatableDescription = new RootTagFinder(project).findClass("Locatable");
        if (locatableDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            locatableDescription = ManualClassFinder.find(project, "Locatable");
        }

        Class<?> boundedClass = boundedShapeDescription.get().getJavaClass();
        Class<?> locatableClass = locatableDescription.get().getJavaClass();

        if (ClassInheritanceChecker.isSubclass(boundedClass, locatableClass))
            return pass(autoGrade);
        else
            return fail("Bounded shape should extend Locatable", autoGrade);
    }
}
