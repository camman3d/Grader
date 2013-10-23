package graders.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import graders.assignment7.tools.CachedProjectClassFinder;
import graders.assignment7.tools.ManualClassFinder;
import graders.assignment7.tools.RootTagFinder;
import scala.Option;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/22/13
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class CmdIntEditablePropTestCase extends BasicTestCase {


    public CmdIntEditablePropTestCase() {
        super("Command interpreter editable property test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        // There should be a setter (editable) for the command
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Command Interpreter");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Command Interpreter");
        }

        Class<?> _class = classDescription.get().getJavaClass();
        Method[] methods = _class.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set"))
                return pass();
        }
        return fail("Couldn't find an editable property");
    }
}
