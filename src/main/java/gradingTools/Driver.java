package gradingTools;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.gui.SettingsWindow;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;
import framework.wrappers.ProjectDatabaseWrapper;
import framework.wrappers.ProjectStepperDisplayerWrapper;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.SakaiProjectDatabase;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * This is the entry class for the grading tools that Maven will reference.
 * Use config.properties to configure what gets run.
 */
public class Driver {

    public static void main(String[] args) {

        try {
            // Load the config file
            PropertiesConfiguration configuration = new PropertiesConfiguration("./config/config.properties");

            // Get the project name
            String projectName = configuration.getString("project.name");
            GradingEnvironment.get().setAssignmentName(projectName);

            // Get the project requirements
            Class<?> _class = Class.forName(configuration.getString("project.requirements"));
            ProjectRequirements requirements = (ProjectRequirements) _class.newInstance();

            // Run the grading process
            String controller = configuration.getString("grader.controller", "GradingManager");
            if (controller.equals("GradingManager")) {

                // Run the GraderManager
                GradingManager manager = new GradingManager(projectName, requirements);
                manager.run();
            } else if (controller.equals("SakaiProjectDatabase")) {

                // Start the grading process by, first, getting the settings the running the project database
                SettingsWindow settingsWindow = SettingsWindow.create();
                settingsWindow.awaitBegin();

                ProjectDatabaseWrapper database = new ProjectDatabaseWrapper();
                database.addProjectRequirements(requirements);

                // Possibly set the stepper displayer
                boolean useFrameworkGUI = configuration.getBoolean("grader.controller.useFrameworkGUI", false);
                if (useFrameworkGUI)
                    database.setProjectStepperDisplayer(new ProjectStepperDisplayerWrapper());

                // TODO: Logging/results saving
                // TODO: Feedback

                database.runProjectsInteractively();
            }


        } catch (ConfigurationException e) {
            System.err.println("Error loading config file.");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find project requirements.");
        } catch (InstantiationException e) {
            System.err.println("Could not create project requirements.");
        } catch (IllegalAccessException e) {
            System.err.println("Could not create project requirements.");
        }
    }
}
