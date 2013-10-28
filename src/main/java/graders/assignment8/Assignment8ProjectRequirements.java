package graders.assignment8;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import graders.assignment6.testCases.*;
import graders.assignment8.testCases.CannedTestCase1;
import graders.assignment8.testCases.CannedTestCase2;
import graders.assignment8.testCases.CannedTestCase3;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/28/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment8ProjectRequirements extends ProjectRequirements {

    public Assignment8ProjectRequirements() {

        // Add due date/times with a 30 minute grace period
        addDueDate("10/24/2013 00:30:00", 1.05);
        addDueDate("10/26/2013 00:30:00", 1);
        addDueDate("10/29/2013 11:30:00", 0.9);
        addDueDate("10/31/2013 11:30:00", 0.75);

        // TODO: Add test cases

        addFeature(new Feature("Test 1", 20, new CannedTestCase1()));
        addFeature(new Feature("Test 2", 30, new CannedTestCase2()));
        addFeature(new Feature("Test 3", 40, new CannedTestCase3()));

        // Define the restrictions

        addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        addRestriction(new Restriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case")));
        addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        addRestriction(new Restriction("Main class in correct package.", -5, new MainClassTestCase("Assignment7", "Main method test case")));
        addRestriction(new Restriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case")));

        // TODO: Warning restrictions

    }
}
