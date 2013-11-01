package gradingTools;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import gradingTools.assignment9.Assignment9ProjectRequirements;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/2/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {
    public static void main(String[] args) throws IOException {

        ProjectRequirements requirements = new Assignment9ProjectRequirements();
        GradingManager manager = new GradingManager("Assignment9", requirements);
        manager.run();
    }
}
