package examples;

import framework.wrappers.ProjectStepperDisplayerWrapper;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;
import examples.checkers.featureCheckers.ErrorFeatureChecker;
import examples.checkers.featureCheckers.FailingFeatureChecker;
import examples.checkers.featureCheckers.PassingFeatureChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * This demonstrates how the GUI from the "framework" system works with the "grader" system
 */
public class GraderWithNewGUIExample {

    public static void main(String[] args) {

        // Do everything as normal
        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);
        List<GradingFeature> features = new ArrayList<GradingFeature>() {{
            add(new AGradingFeature("Test feature 1", 20, new PassingFeatureChecker()));
            add(new AGradingFeature("Test feature 2", 15, new FailingFeatureChecker()));
            add(new AGradingFeature("Test feature 3", 10, new ErrorFeatureChecker()));
        }};
        database.addGradingFeatures(features);

        // Just set the project stepper displayer
        database.setProjectStepperDisplayer(new ProjectStepperDisplayerWrapper());

        // And go!
        database.nonBlockingRunProjectsInteractively();
    }
}
