package gradingTools.assignment1;

import framework.grading.ProjectRequirements;
import gradingTools.assignment1.testCases.*;
import gradingTools.assignment6.testCases.ManualTestCase;
import gradingTools.assignment6.testCases.QuestionTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/6/13
 * Time: 9:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment1ProjectRequirements extends ProjectRequirements {

    public Assignment1ProjectRequirements() {

        // Functionality
        addFeature("Process & print tokens", 40,
                new SingleTokenTestCase(),
                new MultipleTokensTestCase(),
                new RemovePrecedingZerosTestCase());
        addFeature("Sum and product", 10,
                new SumTestCase(),
                new ProductTestCase());
        addFeature("Terminates with period", 10, new TerminateWithPeriodTestCase());

        // Style
        addFeature("One loop on string", 20, new QuestionTestCase("Is there only one loop over the input string?", "Input string one loop test case"));
        addFeature("Two methods", 10, new TwoMethodTestCase());
        addFeature("Screenshots", 10, new QuestionTestCase("Are there screenshots included which show test data output?", "Screenshots testcase"));

        // TODO: Extra Credit
        addFeature("Handle invalid chars", 2, true, new ManualTestCase("Invalid character test case"));
        addFeature("No-array parser class", 10, true, new ManualTestCase("Separate parser class w/out array test case"));
        addFeature("Variable spaces", 5, true, new ManualTestCase("Supports a variable number of spaces test case"));
        addFeature("Nice code", 10, true, new ManualTestCase("Nice code test case"));

        // Restrictions
        addRestriction("No .split allowed", -10, new NoSplitTestCase());

    }
}
