package gradingTools.assignment11;

import framework.grading.FrameworkProjectRequirements;
import framework.wrappers.framework.grading.ExtendedProjectRequirements;
import gradingTools.assignment11.checkers.AbstractClassesChecker;
import gradingTools.assignment11.testCases.PassAndFailTestCase;

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
        addFeature("Abstract classes", 3, new AbstractClassesChecker());

        // New commands (17)
        addFeature("Passed & failed", 2, new PassAndFailTestCase());

//        addFeature("Passed failed approach cmd obj", 5);
//        addFeature("Command list cmd obj", 5);
//        addFeature("Repeat command", 5);
//
//        //Parsing (70)
//        addFeature("Method for all 8 nonterminals", 10);
//        addFeature("Recursive cmd list commands", 40);
//        addFeature("Recursive repeat commands", 20);
//
//        // Animation (20)
//        addFeature("Synchronized avatar methods", 10);
//        addFeature("Coordinated animation", 10);
//
//        // Generics (10)
//        addFeature("Generic table", 10);
//
//        // Extra Credit
//        addFeature("Lockstep animation", 3, true);
//        addFeature("Signed number parsing", 2, true);
//        addFeature("Extended grammar parsing", 15, true);
//        addFeature("Exceptions", 10, true);
//        addFeature("Undo/redo", 5, true);

    }
}
