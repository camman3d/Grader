package joshTest.assignment7;

import framework.grading.ProjectRequirements;
import framework.gui.SettingsWindow;
import framework.utils.GradingEnvironment;
import framework.wrappers.ProjectStepperDisplayerWrapper;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.checkers.AnEncapsulationChecker;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.SakaiProjectDatabase;
import gradingTools.assignment7.Assignment7ProjectRequirements;
import joshTest.ManualFeedbackIgnorer;
import framework.wrappers.ProjectDatabaseWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment7Grader {

    public static void main(String[] args) {
        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        SettingsWindow window = SettingsWindow.create();
        window.awaitBegin();

        System.out.println("Loading projects...");
        long start = System.currentTimeMillis();
//        ProjectDatabaseWrapper database = new ProjectDatabaseWrapper();
        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);
        System.out.println("Done. Time taken: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        GradingEnvironment.get().setAssignmentName("Assignment7");

        // Create the project requirements/features
//        ProjectRequirements requirements = new Assignment7ProjectRequirements();
//        database.addProjectRequirements(requirements);
        List<GradingFeature> gradingFeatures = new ArrayList<GradingFeature>();
        gradingFeatures.add(new AGradingFeature("Test feature 1", 20));
        gradingFeatures.add(new AGradingFeature("Test feature 2", 10, new AnEncapsulationChecker()));
        gradingFeatures.add(new AGradingFeature("Test feature 3", 20, true));

        database.addGradingFeatures(gradingFeatures);

        database.setProjectStepperDisplayer(new ProjectStepperDisplayerWrapper());

        database.nonBlockingRunProjectsInteractively();
    }
}
