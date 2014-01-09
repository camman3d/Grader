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
public class MoveCommandConstructorTestCase extends BasicTestCase {

    public MoveCommandConstructorTestCase() {
        super("Move command object constructor test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Option<ClassDescription> classDescription = ClassFinder.get(project).findByTag("move command", autoGrade);
        if (classDescription.isEmpty())
            return fail("No move command object", autoGrade);
        Class<?> _class = classDescription.get().getJavaClass();

        // Find the avatar class and interface(s)
        Option<ClassDescription> avatarClassDescription = ClassFinder.get(project).findByTag("Avatar", autoGrade);
        if (avatarClassDescription.isEmpty())
            return fail("No single avatar class. This is needed for the constructor.", autoGrade);
        Class<?> avatarClass = avatarClassDescription.get().getJavaClass();
        List<Class<?>> avatarClasses = new ArrayList<Class<?>>(Arrays.asList(avatarClass.getInterfaces()));
        avatarClasses.add(avatarClass);

        // Try all three possible ordering of arguments with different classes.
        for (Class<?> avatar : avatarClasses) {
            if (checkForConstructor(_class, avatar, int.class, int.class))
                return pass(autoGrade);
            if (checkForConstructor(_class, avatar, Integer.class, Integer.class))
                return pass(autoGrade);
            if (checkForConstructor(_class, int.class, avatar, int.class))
                return pass(autoGrade);
            if (checkForConstructor(_class, Integer.class, avatar, Integer.class))
                return pass(autoGrade);
            if (checkForConstructor(_class, int.class, int.class, avatar))
                return pass(autoGrade);
            if (checkForConstructor(_class, Integer.class, Integer.class, avatar))
                return pass(autoGrade);
        }
        return fail("No constructor taking 1 avatar and 2 ints.", autoGrade);
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
