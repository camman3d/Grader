package gradingTools.assignment11;

import gradingTools.assignment11.checkers.AdditionalCommandObjectsChecker;
import gradingTools.assignment11.checkers.CommandListCommandObjectChecker;
import gradingTools.assignment11.checkers.ParserMethodChecker;
import gradingTools.assignment11.checkers.RepeatCommandObjectChecker;
import gradingTools.assignment11.testCases.AbstractAncestorTokenTestCase;
import gradingTools.assignment11.testCases.AbstractBoundedShapeTestCase;
import gradingTools.assignment11.testCases.AbstractLocatableTestCase;
import gradingTools.assignment6.testCases.ManualTestCase;
import gradingTools.assignment6.testCases.QuestionTestCase;
import wrappers.framework.grading.ExtendedProjectRequirements;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment11ProjectRequirements extends ExtendedProjectRequirements {

    public Assignment11ProjectRequirements() {
        addDueDate("11/16/2013 00:30:00", 1.05);
        addDueDate("11/19/2013 00:30:00", 1);
        addDueDate("11/21/2013 00:30:00", 0.9);
        addDueDate("11/23/2013 00:30:00", 0.75);

        // Abstract classes (3)
        addFeature("Abstract classes", 3,
                new AbstractLocatableTestCase(),
                new AbstractBoundedShapeTestCase(),
                new AbstractAncestorTokenTestCase());

        // New commands (17)
        addFeature("Passed & failed", 2);
        addFeature("Passed failed approach cmd obj", 5, new AdditionalCommandObjectsChecker());
        addFeature("Command list cmd obj", 5, new CommandListCommandObjectChecker());
        addFeature("Repeat command", 5, new RepeatCommandObjectChecker());

        //Parsing (70)
        addFeature("Methods for all 8 nonterminals", 10, new ParserMethodChecker());
        addFeature("Recursive cmd list commands", 40,
                new QuestionTestCase("Does the command list parser recursively parse?", "Recursive command list test case"),
                new QuestionTestCase("Test the command interpreter. Do command lists work properly?", "Command list functionality test case"));
        addFeature("Recursive repeat commands", 20,
                new QuestionTestCase("Does the repeat command parser recursively parse?", "Recursive repeat command test case"),
                new QuestionTestCase("Test the command interpreter. Do repeat commands work properly?", "Command list functionality test case"));

        // Animation (20)
        addFeature("Synchronized avatar methods", 10);
        addFeature("Coordinated animation", 10);

        // Generics (10)
        addFeature("Generic table", 10);

        // Extra Credit
        addFeature("Lockstep animation", 3, true);
        addFeature("Signed number parsing", 2, true);
        addFeature("Extended grammar parsing", 15, true);
        addFeature("Exceptions", 10, true);
        addFeature("Undo/redo", 5, true);

    }
}
