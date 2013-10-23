package joshTest;

import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;
import joshTest.gui.JoshStepperForm;
import org.junit.Before;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {

    public static void main(String[] args) {

//        JoshStepperForm.create(null);

        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        System.out.println("Loading projects...");
        long start = System.currentTimeMillis();
        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);
        System.out.println("Done. Time taken: " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        database.addGradingFeatures(Arrays.asList(
                (GradingFeature) new AGradingFeature("Test feature 1", 20),
                new AGradingFeature("Test feature 2", 15),
                new AGradingFeature("Test feature 3", 10)
        ));

//        JoshProjectStepperDisplayer displayer = new JoshProjectStepperDisplayer();
//        database.setProjectStepperDisplayer(displayer);
//        database.setManualFeedback(new JoshManualFeedback());

//        database.runProjectsInteractively();
        database.nonBlockingRunProjectsInteractively();
    }
}