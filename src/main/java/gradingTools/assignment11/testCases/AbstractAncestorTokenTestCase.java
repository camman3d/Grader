package gradingTools.assignment11.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;

import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractAncestorTokenTestCase extends BasicTestCase {


    public AbstractAncestorTokenTestCase() {
        super("Abstract ancestor token test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> description = ClassFinder.get(project).findByTag("ancestor token", autoGrade);
        if (description.isEmpty())
            return fail("No ancestor token class", autoGrade);

        Class<?> _class = description.get().getJavaClass();
        if (Modifier.isAbstract(_class.getModifiers()))
            return pass(autoGrade);
        return fail("Ancestor token class is not abstract", autoGrade);
    }
}
