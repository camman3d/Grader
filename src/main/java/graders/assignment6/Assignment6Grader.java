package graders.assignment6;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import graders.assignment6.testCases.EncapsulationTestCase;
import graders.assignment6.testCases.ManualTestCase;
import graders.assignment6.testCases.ThreePackageTestCase;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment6Grader {
    public static void main(String[] args) {

        ProjectRequirements requirements = new ProjectRequirements();

        // Define the features

        // Scene Enhancements
        requirements.addFeature(new Feature("Gorge and bridge", 10, new ManualTestCase("Gorge and bridge test case")));
        requirements.addFeature(new Feature("Two standing areas", 10, new ManualTestCase("Standing areas test case")));
        requirements.addFeature(new Feature("Avatar init in constructor", 10, new ManualTestCase("Avatar init test case")));

        // Define the restrictions

        requirements.addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        requirements.addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));

        // Now start the grading process

        GradingManager manager = new GradingManager("Assignment6", requirements);
        manager.run();
    }
}
