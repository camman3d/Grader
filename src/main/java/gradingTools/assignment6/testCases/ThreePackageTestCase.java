package gradingTools.assignment6.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class ThreePackageTestCase extends BasicTestCase {
    public ThreePackageTestCase(String name) {
        super(name);
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<String> packages = new HashSet<String>();
        Set<ClassDescription> descriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : descriptions) {
            // Get the package
            packages.add(description.getJavaClass().getPackage().getName());
        }

        if (packages.size() >= 3)
            return pass(autoGrade);
        else
            return fail("You only have " + packages.size() + " packages.", autoGrade);
    }
}
