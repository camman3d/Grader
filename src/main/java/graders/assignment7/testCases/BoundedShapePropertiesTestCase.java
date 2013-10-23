package graders.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import graders.assignment7.tools.ManualClassFinder;
import graders.assignment7.tools.RootTagFinder;
import scala.Option;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class BoundedShapePropertiesTestCase extends BasicTestCase {

    public BoundedShapePropertiesTestCase() {
        super("Bounded shape width and height properties test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Bounded Shape");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Bounded Shape");
        }

        Class<?> _class = classDescription.get().getJavaClass();
        Method[] methods = _class.getMethods();

        // There should four methods: getWidth, getHeight, setWidth, setHeight
        int methodCount = 0;
        String notes = "";
        List<String> validMethods = new ArrayList<String>(Arrays.asList("getwidth", "getheight", "setwidth", "setheight"));
        for (Method method : methods) {
            String name = method.getName().toLowerCase();
            if (validMethods.contains(name)) {
                methodCount++;
                validMethods.remove(validMethods.indexOf(name));
            }
        }

        // Check for absent methods
        for (String methodName : validMethods)
            notes += "Missing method: " + methodName + ". ";
        return partialPass(methodCount / 4, notes);
    }
}
