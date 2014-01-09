package gradingTools.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/8/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class SingleInterfaceTestCase extends BasicTestCase {

    public SingleInterfaceTestCase(String name) {
        super(name);
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        // Look at all the classes
        String notes = "";
        int classCount = 0;
        int badCount = 0;
        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {
            Class<?> _class = description.getJavaClass();
            if (!_class.isInterface()) {
                Class<?>[] interfaces = _class.getInterfaces();
                classCount++;
                if (interfaces.length > 1) {
                    badCount++;
                    notes += (notes.isEmpty() ? "" : "\n") + "Class has multiple interfaces: " + _class.getSimpleName();
                }
            }
        }

        double score = 1 - (badCount / classCount);
        return partialPass(score, notes, autoGrade);
    }
}
