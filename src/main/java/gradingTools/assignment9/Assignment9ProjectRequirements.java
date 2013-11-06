package gradingTools.assignment9;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Restriction;
import gradingTools.assignment6.testCases.*;
import gradingTools.assignment9.testCases.RefreshTestCase;

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
        addDueDate("10/31/2013 00:30:00", 1.05);
        addDueDate("11/02/2013 00:30:00", 1);
        addDueDate("11/05/2013 11:30:00", 0.9);
        addDueDate("10/07/2013 11:30:00", 0.75);

        // Part 1
        addFeature("Interpreter controller setter", 15, new QuestionTestCase("Does the controller call the interpreter setter?", "Controller object calls Interpreter’s setter test case"));
        addFeature("GUI controls scene", 15, new QuestionTestCase("Does the interpreter GUI control the scene?", "GUI object can control scene test case"));
        addFeature("Interpreter controller listener", 5, new QuestionTestCase("Does the controller register a listener for the GUI object", "Controller object registers as a listener for GUI object test case"));

        // Part 2
        addFeature("(1) Scene painter tagged class", 5);
        addFeature("(1) View extends component", 5);
        addFeature("(1) View is listener of shapes", 10);
        addFeature("(1) paint() called when events fire", 5);
        addFeature("(1) Overridden paint() draws everything", 25);

        // Part 2 (EC)
        addFeature("(2) Listener & Painter tagged classes", 5);
        addFeature("(2) Observable Painter extends component", 5);
        addFeature("(2) Paint listener paint method", 5);
        addFeature("(2) View classes register as listener", 20);
        addFeature("(2) Listeners notify on fired events", 10);
        addFeature("(2) Views paint objects", 25);
        addFeature("(1 & 2) Background & bridge beneath avatars", 25);

        // Part 3
        addFeature("Demo", 10);

        // Extra Credit
        addFeature("Command interpreter errors", 5, true);
        addFeature("Progress bar", 5, true);
        addFeature("Interpreter has 2+ actions", 5, true);
        addFeature("Bridge scene controller", 5, true);


        // Define the restrictions
        addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        addRestriction(new Restriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case")));
        addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        addRestriction(new Restriction("Main class in correct package.", -5, new MainClassTestCase("Assignment9", "Main method test case")));
        addRestriction(new Restriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case")));
        addRestriction(new Restriction("No OE .refresh()", -5, new RefreshTestCase()));

    }
}
