package graders.assignment6;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import graders.assignment6.testCases.EncapsulationTestCase;
import graders.assignment6.testCases.ManualTestCase;
import graders.assignment6.testCases.TaggedMethodTestCase;
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

        requirements.addFeature(new Feature("Gorge and bridge", 10, new ManualTestCase("Gorge and bridge test case")));
        requirements.addFeature(new Feature("Two standing areas", 10, new ManualTestCase("Standing areas test case")));
        requirements.addFeature(new Feature("Avatar init in constructor", 10, new ManualTestCase("Avatar init test case")));
        requirements.addFeature(new Feature("Approach method", 15,
                new TaggedMethodTestCase("approach", "Approach tag test case")));
        requirements.addFeature(new Feature("Say method", 15, new ManualTestCase("Say method test case")));
        requirements.addFeature(new Feature("Passed method", 15,
                new TaggedMethodTestCase("passed", "Passed tag test case")));
        requirements.addFeature(new Feature("Failed method", 15,
                new TaggedMethodTestCase("failed", "Failed tag test case")));
        requirements.addFeature(new Feature("Token refactoring", 10, new ManualTestCase("Token refactoring test case")));

        // Define extra credit

        requirements.addFeature(new Feature("3D Gorge", 3, true, new ManualTestCase("3D gorge test case")));
        requirements.addFeature(new Feature("Animated bridge crossing", 3, true, new ManualTestCase("Animated crossing test case")));
        requirements.addFeature(new Feature("Rotated/animated falling", 3, true, new ManualTestCase("Animated falling test case")));

        // Define the restrictions

        requirements.addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        requirements.addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        // TODO: Method/interface check
        // TODO: Packaging check
        // TODO: System.exit check

        // Now start the grading process

        GradingManager manager = new GradingManager("Assignment6", requirements);
        manager.run();
    }
}
