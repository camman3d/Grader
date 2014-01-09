package gradingTools.assignment3.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/8/13
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class NamingConventionsTestCase extends BasicTestCase {
    public NamingConventionsTestCase() {
        super("Naming conventions test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Check each class
        double checkCount = 0;
        double checkPass = 0;
        String notes = "";
        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            Class<?> _class = description.getJavaClass();

            // Check the class name is uppercase
            checkCount++;
            if (Character.isUpperCase(_class.getSimpleName().charAt(0)))
                checkPass++;
            else
                notes += "Class \"" + _class.getSimpleName() + "\" should be capitalized";

            // Check each method is lowercase
            for (Method method : description.getJavaClass().getDeclaredMethods()) {
                checkCount++;
                if (Character.isLowerCase(method.getName().charAt(0)))
                    checkPass++;
                else
                    notes += "Method \"" + method.getName() + "\" should be lowercase";
            }

            // Check is property is lowercase if not final
            for (Field field : description.getJavaClass().getDeclaredFields()) {
                if (!Modifier.isFinal(field.getModifiers())) {
                    checkCount++;
                    if (Character.isLowerCase(field.getName().charAt(0)))
                        checkPass++;
                    else
                        notes += "Field \"" + field.getName() + "\" should be lowercase";
                }
            }
        }

        return partialPass(checkPass / checkCount, notes, autoGrade);
    }
}
