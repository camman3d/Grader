package graders;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.*;
import framework.gui.GradingWindow;
import framework.gui.SettingsWindow;
import framework.logging.JsonWritableResults;
import framework.project.Project;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/2/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {
    public static void main(String[] args) throws IOException {

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
