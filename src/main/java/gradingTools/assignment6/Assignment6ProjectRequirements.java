package gradingTools.assignment6;

import framework.grading.FrameworkProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import gradingTools.assignment6.testCases.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment6ProjectRequirements extends FrameworkProjectRequirements {
    public Assignment6ProjectRequirements() {

        addFeature(new Feature("Gorge and bridge", 10, new QuestionTestCase("Is there a gorge and bridge?", "Gorge and bridge test case")));
        addFeature(new Feature("Two standing areas", 10, new QuestionTestCase("Are there two standing areas", "Standing areas test case")));
        addFeature(new Feature("Avatar init in constructor", 10, new QuestionTestCase("Are the avatars initialized & positioned in their constructors?", "Avatar init test case")));
        addFeature(new Feature("Approach method", 15,
                new TaggedMethodTestCase("approach", "Approach tag test case"),
                new QuestionTestCase("Does the approach method use the occupied property?", "Approach property test case"),
                new QuestionTestCase("Does the approach method only work when the standing area is unoccupied?", "Approach when unoccupied test case")
        ));
        addFeature(new Feature("Say method", 15,
                new QuestionTestCase("Does the say method accept one parameter, which is a string?", "Say parameter test case"),
                new QuestionTestCase("Does the say method only work when both standing areas are occupied?", "Say when occupied test case"),
                new QuestionTestCase("Does the guard always speak first?", "Guard speaks first test case"),
                new QuestionTestCase("Does the dialog alternate between guard and knight?", "Alternating dialog test case")
        ));
        addFeature(new Feature("Passed method", 15,
                new TaggedMethodTestCase("passed", "Passed tag test case"),
                new QuestionTestCase("Is the passed method parameterless?", "Passed method signature test case"),
                new QuestionTestCase("Does the passed method only work when it's the guard turn to speak?", "Passed only on guard's turn test case"),
                new QuestionTestCase("Does the passed method only work when both standing areas are occupied?", "Passed when occupied test case"),
                new QuestionTestCase("When passed, does the avatar move to the other side of the bridge?", "Passed movement test case")
        ));
        addFeature(new Feature("Failed method", 15,
                new TaggedMethodTestCase("failed", "Failed tag test case"),
                new QuestionTestCase("Is the failed method parameterless?", "Failed method signature test case"),
                new QuestionTestCase("Does the failed method fail the currently speaking avatar?", "Failed turn test case"),
                new QuestionTestCase("Does the failed method only work when both standing areas are occupied?", "Failed when occupied test case"),
                new QuestionTestCase("When failed, does the avatar move into the gorge?", "Failed movement test case")
        ));
        addFeature(new Feature("Token refactoring", 10, new SingleInterfaceTestCase("Single interface test case")));

        // Define extra credit

        addFeature(new Feature("3D Gorge", 3, true, new QuestionTestCase("Are there 3D effects for the gorge/bridge?", "3D gorge test case")));
        addFeature(new Feature("Animated bridge crossing", 3, true, new QuestionTestCase("Is there animated falling?", "Animated crossing test case")));
        addFeature(new Feature("Rotated/animated falling", 3, true, new QuestionTestCase("Is there animated crossing?", "Animated falling test case")));

        // Define the restrictions

        addRestriction(new Restriction("No public variables.", -5, new EncapsulationTestCase("Encapsulation test case")));
        addRestriction(new Restriction("Interface object assignments.", -5, new InterfaceTypeTestCase("Interface type test case")));
        addRestriction(new Restriction("At least three packages.", -5, new ThreePackageTestCase("Three package test case")));
        // TODO: Method/interface check
        addRestriction(new Restriction("Main class in correct package.", -5, new MainClassTestCase("Assignment6", "Main method test case")));
        addRestriction(new Restriction("No System.exit()", -5, new SystemExitTestCase("System.exit test case")));
    }
}
