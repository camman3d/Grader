package gradingTools.assignment2;

import framework.grading.ProjectRequirements;
import gradingTools.assignment2.testCases.*;
import gradingTools.assignment6.testCases.ManualTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/7/13
 * Time: 9:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment2ProjectRequirements extends ProjectRequirements {
    public Assignment2ProjectRequirements() {

        // Functionality
        addFeature("Bean class", 20, new BeanClassTestCase());
        addFeature("Numbers and words", 20,
                new NumberTokensTestCase(),
                new WordTokensTestCase());
        addFeature("Quoted string", 30, new QuotedStringTestCase());

        // Testing
        addFeature("Debugging screenshots", 20, new ManualTestCase("Debugging screenshots test case"));
        addFeature("Output screenshots", 10, new ManualTestCase("Output screenshots test case"));

        // Extra credit
        addFeature("Variable spaces", 3, true, new ManualTestCase("Variable spaces test case"));
        addFeature("Error message w/ missing quote", 3, true, new ManualTestCase("Missing quote test case"));
        addFeature("Custom isLetter method", 3, true, new IsLetterTestCase());
        addFeature("Plus and minus tokens", 4, true, new ManualTestCase("Plus and minus tokens test case"));


    }
}
