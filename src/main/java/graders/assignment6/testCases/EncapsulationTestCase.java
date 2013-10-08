package graders.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class EncapsulationTestCase extends BasicTestCase {
    public EncapsulationTestCase(String name) {
        super(name);
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        String notes = "";
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        int numVariables = 0;
        int numPublicVariables = 0;
        for (ClassDescription classDescription : classDescriptions) {
            Class<?> _class = classDescription.getJavaClass();
            Field[] fields = _class.getDeclaredFields();
            numVariables += fields.length;
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) {
                    notes += (notes.isEmpty() ? "" : "\n") + "Public variable: " + _class.getSimpleName() + "." + field.getName();
                }
            }
        }

        double fractionPublicVariables = numVariables == 0? 0: (double) numPublicVariables/numVariables;
        double fractionalScore = 1 - fractionPublicVariables;
        return partialPass(fractionalScore, notes);
    }
}
