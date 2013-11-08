package gradingTools.assignment1.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.ClassDescription;
import framework.project.Project;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoSplitTestCase extends BasicTestCase {

    public NoSplitTestCase() {
        super("No split() test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();

        for (ClassDescription description : project.getClassesManager().get().getClassDescriptions()) {
            try {
                // Get the comment free code
                String code = FileUtils.readFileToString(description.getSource());
                code = code.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");

                // Fail if we find a split()
                if (code.contains(".split("))
                    return fail("No .split() allowed");

            } catch (IOException e) {
                throw new NotGradableException();
            }
        }

        return pass();
    }
}
