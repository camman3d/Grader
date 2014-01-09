package gradingTools.assignment10.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import scala.Option;
import tools.classFinder2.ClassFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class SayCommandConstructorTestCase extends BasicTestCase {

    public SayCommandConstructorTestCase() {
        super("Say command object constructor test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("say command", autoGrade);
        if (classDescription.isEmpty())
            return fail("No say command object", autoGrade);
        Class<?> _class = classDescription.get().getJavaClass();

        // Find the avatar class and interface(s)
        Option<ClassDescription> sceneClassDescription = ClassFinder.get(project).findByTag("Bridge Scene", autoGrade);
        if (sceneClassDescription.isEmpty())
            return fail("No bridge scene class. This is needed for the constructor.", autoGrade);
        Class<?> sceneClass = sceneClassDescription.get().getJavaClass();
        List<Class<?>> sceneClasses = new ArrayList<Class<?>>(Arrays.asList(sceneClass.getInterfaces()));
        sceneClasses.add(sceneClass);

        // Try both possible ordering of arguments with different classes.
        for (Class<?> scene : sceneClasses) {
            if (checkForConstructor(_class, scene, String.class))
                return pass(autoGrade);
            if (checkForConstructor(_class, String.class, scene))
                return pass(autoGrade);
        }
        return fail("No constructor taking a scene and a string.", autoGrade);
    }

    private boolean checkForConstructor(Class<?> _class, Class<?> ... argTypes) {
        try {
            _class.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }
}
