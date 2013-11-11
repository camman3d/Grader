package gradingTools.assignment3.testCases;

import framework.grading.testing.BasicTestCase;
import framework.grading.testing.NotAutomatableException;
import framework.grading.testing.NotGradableException;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/8/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScannerBeanWordOutputTestCase extends BasicTestCase {
    public ScannerBeanWordOutputTestCase() {
        super("Scanner bean word output test case");
    }

    @Override
    public TestCaseResult test(Project project, boolean autoGrade) throws NotAutomatableException, NotGradableException {
        if (project.getClassesManager().isEmpty())
            throw new NotGradableException();


        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
