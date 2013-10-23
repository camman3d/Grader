package joshTest.assignment7;

import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;
import joshTest.assignment7.checkers.BoundedShapeExtendsLocatableChecker;

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
        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        System.out.println("Loading projects...");
        long start = System.currentTimeMillis();
        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);
        System.out.println("Done. Time taken: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        database.addGradingFeatures(Arrays.asList(
                (GradingFeature) new AGradingFeature("Bounded shape extends locatable", 20, new BoundedShapeExtendsLocatableChecker()),
                new AGradingFeature("Test feature 2", 15),
                new AGradingFeature("Test feature 3", 10)
        ));

        database.nonBlockingRunProjectsInteractively();
    }
}
