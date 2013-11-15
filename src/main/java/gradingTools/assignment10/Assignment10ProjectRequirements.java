package gradingTools.assignment10;

import framework.grading.ProjectRequirements;
import gradingTools.assignment10.testCases.*;
import gradingTools.assignment6.testCases.ManualTestCase;
import gradingTools.assignment6.testCases.QuestionTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 9:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment10ProjectRequirements extends ProjectRequirements {

    public Assignment10ProjectRequirements() {

        // Add due date/times with a 30 minute grace period
        addDueDate("11/07/2013 00:30:00", 1.05);
        addDueDate("11/09/2013 00:30:00", 1);
        addDueDate("11/12/2013 11:30:00", 0.9);
        addDueDate("11/14/2013 11:30:00", 0.75);

        // Precondition Methods (24 pts)
        addFeature("Precondition methods", 12,
                new PreconditionTestCase("approach"),
                new PreconditionTestCase("say"),
                new PreconditionTestCase("passed"),
                new PreconditionTestCase("failed"));
        addFeature("Console view shows precond events", 12, new QuestionTestCase("Do all four precondition events show up in the console view?", "Console view shows precondition events test case"));
//        addFeature("Console view shows precond events", 12, new PreconditionEventsTestCase());

        // Command Objects (21 pts)
        addFeature("Say & move cmd objects", 5,
                new CommandImplementsRunnableTestCase("move command"),
                new CommandImplementsRunnableTestCase("say command"));
        addFeature("Move cmd constructor", 3, new MoveCommandConstructorTestCase());
        addFeature("Say cmd constructor", 3, new SayCommandConstructorTestCase());
        addFeature("Say and move parsers", 5,
                new ParserMethodTestCase("say parser"),
                new ParserMethodTestCase("move parser"));
        addFeature("Command object invoked", 5, new SayMoveCommandInvokedTestCase());

        // Asynchronous Animations (55 pts)
        addFeature("Animating methods", 5,
                new AnimatingMethodTestCase("asynchronous Arthur"),
                new AnimatingMethodTestCase("asynchronous Galahad"),
                new AnimatingMethodTestCase("asynchronous Lancelot"),
                new AnimatingMethodTestCase("asynchronous Robin"));
        addFeature("Methods start new threads", 10,
                new AnimatingMethodNewThreadTestCase("asynchronous Arthur"),
                new AnimatingMethodNewThreadTestCase("asynchronous Galahad"),
                new AnimatingMethodNewThreadTestCase("asynchronous Lancelot"),
                new AnimatingMethodNewThreadTestCase("asynchronous Robin"));
        addFeature("Animating command classes", 20, new QuestionTestCase("Does each async animation method use an animating command class (Runnable)?", "Animating command class"));
        addFeature("Animator classes", 20, new QuestionTestCase("Are there animator classes with an animating method?", "Animator classes with method test case"));

        // Extra Credit
        addFeature("Guard animation", 5, true,
                new AnimatingMethodTestCase("asynchronous guard"),
                new AnimatingMethodNewThreadTestCase("asynchronous guard"));
        addFeature("Precondition buttons", 10, true, new QuestionTestCase("Are there buttons that are dynamically enabled/disabled based on the preconditions?", "Precondition buttons test case"));
        addFeature("Awesome demo", 5, true, new ManualTestCase("Awesome demo test case"));
    }
}
