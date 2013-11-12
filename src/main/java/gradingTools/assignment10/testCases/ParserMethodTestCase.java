package gradingTools.assignment10.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;
import util.annotations.Tags;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParserMethodTestCase extends BasicTestCase {

    private String tag;

    public ParserMethodTestCase(String tag) {
        super(tag + " creates cmd object test case");
        this.tag = tag;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Get the command interpreter
        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("Command Interpreter", autoGrade);
        if (classDescription.isEmpty())
            return fail("Looking for method in command interpreter, but the class was not found.");

        List<Method> methods = classDescription.get().getTaggedMethods(tag);
        if (methods.isEmpty())
            return fail("Couldn't find the " + tag + " method");

        if (Runnable.class.isAssignableFrom(methods.get(0).getReturnType()))
            return pass();
        else
            return fail(tag + " method should return a Runnable");

    }
}
