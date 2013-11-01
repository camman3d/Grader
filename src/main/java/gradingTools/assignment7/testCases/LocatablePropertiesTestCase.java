package gradingTools.assignment7.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import gradingTools.assignment7.tools.ManualClassFinder;
import gradingTools.assignment7.tools.RootTagFinder;
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
public class LocatablePropertiesTestCase extends BasicTestCase {

    public LocatablePropertiesTestCase() {
        super("Locatable x and y properties test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {

        // Make sure we can get the class description
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();
        Option<ClassDescription> classDescription = new RootTagFinder(project).findClass("Locatable");
        if (classDescription.isEmpty()) {
            if (autoGrade)
                throw new NotAutomatableException();
            classDescription = ManualClassFinder.find(project, "Locatable");
        }

        Class<?> _class = classDescription.get().getJavaClass();
        Method[] methods = _class.getMethods();

        // There should four methods: getX, getY, setX, and setY
        int methodCount = 0;
        String notes = "";
        List<String> validMethods = new ArrayList<String>(Arrays.asList("getx", "gety"));
        for (Method method : methods) {
            String name = method.getName().toLowerCase();
            if (validMethods.contains(name)) {
                methodCount++;
                int index = validMethods.indexOf(name);
                validMethods.remove(index);
            }
        }

        // Check for absent methods
        for (String methodName : validMethods)
            notes += "Missing method: " + methodName + ". ";
        return partialPass(methodCount / 4, notes);
    }
}
