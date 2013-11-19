package gradingTools.assignment11.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import gradingTools.assignment11.tools.SpecialClassFinder;
import scala.Option;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractLocatableTestCase extends BasicTestCase {
    public AbstractLocatableTestCase() {
        super("Abstract locatable test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> locatable = SpecialClassFinder.getLocatable(project, autoGrade);
        if (locatable.isEmpty())
            return fail("Could not find the locatable class.");

        Class<?> _class = locatable.get().getJavaClass();
        if (Modifier.isAbstract(_class.getModifiers()))
            return pass();
        return fail("Locatable is not abstract.");
    }
}
