package gradingTools.assignment3;

import framework.grading.FrameworkProjectRequirements;
import gradingTools.assignment2.testCases.BeanClassTestCase;
import gradingTools.assignment3.testCases.NamingConventionsTestCase;
import gradingTools.assignment3.testCases.ScannerBeanWordOutputTestCase;
import gradingTools.assignment6.testCases.MainClassTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/8/13
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Assignment3ProjectRequirements extends FrameworkProjectRequirements {
    public Assignment3ProjectRequirements() {

        // Naming conventions
        addFeature("Naming conventions", 10, new NamingConventionsTestCase());

        // Scanner Bean
        addFeature("Correct output", 20, new ScannerBeanWordOutputTestCase());
        addFeature("Follows bean format", 5, new BeanClassTestCase());

        // Token classes


        // Restrictions
        addRestriction("Main method", -5, new MainClassTestCase("Assignment3", "Main method test case"));
    }
}
