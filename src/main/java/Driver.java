import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.BasicTestCase;
import framework.grading.testing.Feature;
import framework.grading.testing.TestCase;
import framework.grading.testing.TestCaseResult;
import framework.gui.GradingWindow;
import framework.gui.SettingsWindow;
import framework.project.Project;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/2/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {
    public static void main(String[] args) {

        ProjectRequirements requirements = new ProjectRequirements();

        requirements.addFeature(new Feature("Be cool", 10, Arrays.asList((TestCase) new BasicTestCase("Class checker") {
            @Override
            public TestCaseResult test(Project project) {
                if (project.getClassesManager().isDefined())
                    return pass();
                else
                    return fail("No classes!");
            }
        })));

        GradingManager manager = new GradingManager("Assignment1", requirements);
        manager.run();
    }
}
