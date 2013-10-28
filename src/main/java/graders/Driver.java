package graders;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.*;
import framework.gui.GradingWindow;
import framework.gui.SettingsWindow;
import framework.logging.JsonWritableResults;
import framework.project.Project;
import graders.assignment7.Assignment7ProjectRequirements;
import graders.assignment8.Assignment8ProjectRequirements;
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

        ProjectRequirements requirements = new Assignment8ProjectRequirements();
        GradingManager manager = new GradingManager("Assignment8", requirements);
        manager.run();
    }
}
