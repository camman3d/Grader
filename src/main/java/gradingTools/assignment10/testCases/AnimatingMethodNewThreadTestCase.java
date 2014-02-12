package gradingTools.assignment10.testCases;

import com.github.antlrjavaparser.api.body.ClassOrInterfaceDeclaration;
import com.github.antlrjavaparser.api.body.MethodDeclaration;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.CompilationNavigation;
import tools.classFinder2.ClassFinder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnimatingMethodNewThreadTestCase extends BasicTestCase {

    private String tag;

    public AnimatingMethodNewThreadTestCase(String tag) {
        super(tag + " animating method starts a new thread test case");
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

        // Get the method
        List<Method> methods = classDescription.get().getTaggedMethods(tag);
        if (methods.isEmpty())
            return fail("No method tagged: " + tag);

        try {
            // Look for .start() in the code
            ClassOrInterfaceDeclaration classDef = classDescription.get().parse();
            MethodDeclaration method = CompilationNavigation.getMethod(classDef, methods.get(0).getName());
            String code = method.toString();
            if (code.contains(".start()"))
                return pass();
            return fail("Could not find .start() in the async method");
        } catch (IOException e) {
            throw new NotGradableException();
        }
    }
}
