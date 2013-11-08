package gradingTools;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import gradingTools.assignment1.Assignment1ProjectRequirements;
import gradingTools.assignment2.Assignment2ProjectRequirements;
import gradingTools.assignment9.Assignment9ProjectRequirements;

import java.io.IOException;

/**
 * This is the entry class for the grading tools that Maven will reference.
 */
public class Driver {

    public static void main(String[] args) throws IOException {

        ProjectRequirements requirements = new Assignment2ProjectRequirements();
        GradingManager manager = new GradingManager("Assignment2", requirements);
        manager.run();

    }
}
