package gradingTools;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import gradingTools.assignment9.Assignment9ProjectRequirements;

import java.io.IOException;

/**
 * This is the entry class for the grading tools that Maven will reference.
 */
public class Driver {

    public static void main(String[] args) throws IOException {

        ProjectRequirements requirements = new Assignment9ProjectRequirements();
        GradingManager manager = new GradingManager("Assignment9", requirements);
        manager.run();

    }
}
