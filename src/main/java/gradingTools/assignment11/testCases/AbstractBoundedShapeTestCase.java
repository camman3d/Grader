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

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractBoundedShapeTestCase extends BasicTestCase {
    public AbstractBoundedShapeTestCase() {
        super("Abstract bounded shape test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> boundedShape = SpecialClassFinder.getBoundedShape(project, autoGrade);

        if (boundedShape.isEmpty())
            return fail("Could not find the bounded shape class.");

        Class<?> _class = boundedShape.get().getJavaClass();
        if (Modifier.isAbstract(_class.getModifiers()))
            return pass();
        return fail("Bounded Shape is not abstract.");
    }
}
