package examples;

import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;
import examples.checkers.featureCheckers.ErrorFeatureChecker;
import examples.checkers.featureCheckers.FailingFeatureChecker;
import examples.checkers.featureCheckers.PassingFeatureChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * This demonstrates using the "grader" grading system
 */
public class GraderExample {

    public static void main(String[] args) {

        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        // Create a project database, which will contain the project grading criteria
        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);
        List<GradingFeature> features = new ArrayList<GradingFeature>() {{
            add(new AGradingFeature("Test feature 1", 20, new PassingFeatureChecker()));
            add(new AGradingFeature("Test feature 2", 15, new FailingFeatureChecker()));
            add(new AGradingFeature("Test feature 3", 10, new ErrorFeatureChecker()));
        }};
        database.addGradingFeatures(features);

        // The project database is responsible for running the grading process
        database.nonBlockingRunProjectsInteractively();
    }
}
