package gradingTools.assignment10.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import org.apache.commons.lang.StringUtils;
import scala.Option;
import tools.classFinder2.ClassFinder;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PreconditionTestCase extends BasicTestCase {

    private String tag;

    public PreconditionTestCase(String tag) {
        super(tag + " precondition test case");
        this.tag = tag;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Look for the method pre{Tag} which has no arguments and returns a boolean.
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("Bridge Scene", autoGrade);
        if (classDescription.isEmpty())
            return fail("No bridge scene class");
        Class<?> _class = classDescription.get().getJavaClass();
        String name = "pre" + StringUtils.capitalize(tag);
        try {
            Method method = _class.getMethod(name, new Class[]{});
            if (method.getReturnType() == boolean.class || method.getReturnType() == Boolean.class)
                return pass();
            else
                return partialPass(0.5, "Precondition method should return a boolean");
        } catch (NoSuchMethodException e) {
            return fail("Missing precondition method: " + name);
        }
    }
}
