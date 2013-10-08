package graders.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/8/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainClassTestCase extends BasicTestCase {

    private String className;

    public MainClassTestCase(String className, String name) {
        super(name);
        this.className = className;
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {
            // See if the main method is in this class
            Class<?> _class = description.getJavaClass();
            try {
                _class.getMethod("main", String[].class);

                // Now make sure that the package and class names are right
                String packageName = _class.getPackage().getName();
                if (_class.getSimpleName().equals(className) && packageName.equals("main"))
                    return pass();
                else
                    return fail("Main method is in class: " + _class.getSimpleName() + " and package: " + packageName);
            } catch (NoSuchMethodException e) {}
        }
        throw new NotAutomatableException();
    }
}
