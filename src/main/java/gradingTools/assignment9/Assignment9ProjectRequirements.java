package gradingTools.assignment9;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Restriction;
import gradingTools.assignment6.testCases.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/28/13
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment9ProjectRequirements extends ProjectRequirements {

    public Assignment9ProjectRequirements() {

        // Add due date/times with a 30 minute grace period
//        addDueDate("10/24/2013 00:30:00", 1.05);
//        addDueDate("10/26/2013 00:30:00", 1);
//        addDueDate("10/29/2013 11:30:00", 0.9);
//        addDueDate("10/31/2013 11:30:00", 0.75);

        addFeature("Tracer test", 10, new EventTracerTestCase("asdf"));

        // Define the restrictions
        addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        addRestriction(new Restriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case")));
        addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        addRestriction(new Restriction("Main class in correct package.", -5, new MainClassTestCase("Assignment9", "Main method test case")));
        addRestriction(new Restriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case")));

        // TODO: Warning restrictions

    }
}
