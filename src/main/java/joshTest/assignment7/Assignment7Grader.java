package joshTest.assignment7;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Restriction;
import framework.gui.SettingsWindow;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.checkers.AProjectTracer;
import grader.sakai.project.ASakaiProjectDatabase;
import graders.assignment6.testCases.*;
import graders.assignment7.Assignment7ProjectRequirements;
import graders.assignment7.testCases.*;
import joshTest.ManualFeedbackIgnorer;
import joshTest.assignment7.checkers.BoundedShapeExtendsLocatableChecker;
import joshTest.wrappers.ProjectDatabaseWrapper;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 10:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment7Grader {

    public static void main(String[] args) {
//        String bulkFolder = "/Users/josh/Downloads/a7";
//        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        SettingsWindow window = SettingsWindow.create();
        window.awaitBegin();

        System.out.println("Loading projects...");
        long start = System.currentTimeMillis();
        ProjectDatabaseWrapper database = new ProjectDatabaseWrapper();
        System.out.println("Done. Time taken: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        GradingEnvironment.get().setAssignmentName("Assignment7");

        // Create the project requirements/features
        ProjectRequirements requirements = new Assignment7ProjectRequirements();
        database.addProjectRequirements(requirements);

        // Keep the note text editor from popping up
        database.setManualFeedback(new ManualFeedbackIgnorer());

//        database.addGradingFeatures(Arrays.asList(
//                (GradingFeature) new AGradingFeature("Tracer", 20, new AProjectTracer())
//        ));

        database.nonBlockingRunProjectsInteractively();
    }
}
