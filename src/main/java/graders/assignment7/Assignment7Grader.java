package graders.assignment7;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import framework.grading.testing.Restriction;
import graders.assignment6.testCases.*;
import graders.assignment7.testCases.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/14/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment7Grader {

    public static void main(String[] args) {

        ProjectRequirements requirements = new ProjectRequirements();

        // Define the features

        requirements.addFeature("Locatable class and interface", 15,
                new LocatableTagTestCase(),
                new LocatablePropertiesTestCase(),
                new LocatableInheritanceTestCase()
                // TODO: More refactoring?
                );

        requirements.addFeature("Bounded shape class and interface", 15,
                new BoundedShapeTagTestCase(),
                new BoundedShapePropertiesTestCase(),
                new BoundedShapeInheritanceTestCase(),
                new BoundedShapeExtendsLocatableTestCase()
                // TODO: More refactoring?
                );

        requirements.addFeature("Table class tagged", 5, new TableTagTestCase());
        requirements.addFeature("Put method", 15,
//                new PutNewTestCase(),
//                new PutExistingTestCase(),
//                new PutNullTestCase(),

                new QuestionTestCase("When adding an item w/ a new key, is it added?", "Put new test case"),
                new QuestionTestCase("When adding an item w/ an existing key, is it replaced?", "Put existing test case"),
                new QuestionTestCase("When adding an item w/ a null key, does nothing happen?", "Put null test case"),
                new QuestionTestCase("Is the put method demoed?", "Put method demo test case")
                );
        requirements.addFeature("Get method", 15,
                new QuestionTestCase("When getting an item w/ a key, is it return?", "Get value test case"),
                new QuestionTestCase("When getting an item w/ a non-existent key, is null returned?", "Get non-existent test case"),
                new QuestionTestCase("Is the get method demoed?", "Get method demo test case")
                );

        // Command interpreter

        requirements.addFeature("Command interpreter tag", 5, new CmdIntTagTestCase());
        requirements.addFeature("Editable property", 5, new CmdIntEditablePropTestCase());
        requirements.addFeature("Command interpretation", 25,
                new QuestionTestCase("In the command interpreter, Does the command get scanned?", "Command interpreter scanning test case"),
                new QuestionTestCase("Does the command interpreter correctly identify 'move' and 'say' commands?", "Command interpreter move/say test case"),
                new QuestionTestCase("Does the command interpreter invoke the move or say method?", "Command interpreter method invoke test case"),
                new QuestionTestCase("Is the command interpreter demoed?", "Command interpreter demo test case"));

        // Extra credit
        requirements.addFeature("Move supports signs and is tagged", 5, true, new ManualTestCase("Signed move test case"));
        requirements.addFeature("Interpreter detects errors and is tagged", 5, true, new ManualTestCase("Error resilient test case"));
        requirements.addFeature("Nice Demo", 5, true, new ManualTestCase("Nice demo test case"));

        // Define the restrictions

        requirements.addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        requirements.addRestriction(new Restriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case")));
        requirements.addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        // TODO: Method/interface check
        requirements.addRestriction(new Restriction("Main class in correct package.", -5, new MainClassTestCase("Assignment7", "Main method test case")));
        requirements.addRestriction(new Restriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case")));

        // Now start the grading process

        GradingManager manager = new GradingManager("Assignment7", requirements);
        manager.run();

    }

}
