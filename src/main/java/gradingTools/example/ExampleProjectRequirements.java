package gradingTools.example;

import framework.grading.FrameworkProjectRequirements;
import gradingTools.example.testCases.*;

/**
 * This is an example on how to define requirements and test cases for an assignment. You just extend the
 * {@link FrameworkProjectRequirements} class, define a nullary constructor (no arguments), and within it add due dates,
 * features, and restrictions by calling the respective methods:
 * <ul>
 *     <li>addDueDate</li>
 *     <li>addFeature</li>
 *     <li>addRestriction</li>
 * </ul>
 *
 * These project requirements are to be used with the "Test Data/Example Bulk" folder.
 */
public class ExampleProjectRequirements extends FrameworkProjectRequirements {

    public ExampleProjectRequirements() {

        // This defines the due dates. What this is saying is, if a project is turned in on or before the specified time
        // then its score is multiplied by the defined percentage.
        addDueDate("10/10/2013 16:30:00", 1.1);
        addDueDate("10/15/2013 16:30:00", 1);
        addDueDate("10/17/2013 16:30:00", 0.75);

        // This is how to add features
        // You can add any number of test cases
        addFeature("Prints words forwards & backwards", 30,
                new ForwardPrinterTestCase(),
                new ReversePrinterTestCase());
        addFeature("Has a common interface", 20, new CommonInterfaceTestCase());

        // You can even specify is something is extra credit
        addFeature("Prints words without vowels", 10, true, new NoVowelPrinterTestCase());

        // This is how to add restrictions. Restrictions are graded differently than features; if the test cases pass
        // Then zero points are awarded, otherwise the negative points are applied.
        addRestriction("Doesn't use StringBuilder or StringBuffer", -5, new NoStringToolsTestCase());
        addRestriction("Doesn't use StringBuilder.reverse", -5, new NoStringBuilderReverseTestCase());
    }
}
