package gradingTools.assignment9.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RefreshTestCase extends BasicTestCase {

    public RefreshTestCase() {
        super("No .refresh() test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        Set<ClassDescription> classDescriptions = project.getClassesManager().get().getClassDescriptions();
        for (ClassDescription description : classDescriptions) {
            try {
                List<String> lines = FileUtils.readLines(description.getSource());
                for (String line : lines) {
                    if (line.trim().endsWith(".refresh();"))
                        return fail("Object editor refresh() call found in " + description.getJavaClass().getSimpleName());
                }
            } catch (IOException e) {}
        }
        return pass();
    }
}
