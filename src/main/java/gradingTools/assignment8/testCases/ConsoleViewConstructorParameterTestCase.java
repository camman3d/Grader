package gradingTools.assignment8.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import tools.classFinder.ManualClassFinder;
import tools.classFinder.RootTagFinder;
import scala.Option;

import java.lang.reflect.Constructor;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/30/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleViewConstructorParameterTestCase extends BasicTestCase {
    public ConsoleViewConstructorParameterTestCase() {
        super("Console view constructor parameter test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Console Scene View");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Console Scene View");
        }

        // Get the constructors and look for one with parameters
        Constructor<?>[] constructors = classDescription.get().getJavaClass().getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length > 0)
                return pass();
        }
        return fail("There should be a constructor that accepts parameters.");
    }
}
