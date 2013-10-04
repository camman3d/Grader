package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.grading.testing.TestCase;
import org.junit.Test;
import testObjects.SimpleTestCase;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestProjectRequirements {

    @Test
    public void testFeatures() {
        ProjectRequirements requirements = new ProjectRequirements();
        requirements.addFeature(new Feature("A", 12, Arrays.asList(
                (TestCase) new SimpleTestCase(1, "", "Test A 1"),
                new SimpleTestCase(0.5, "", "Test A 2"),
                new SimpleTestCase(0, "", "Test A 3")
        )));

        // Get the results
        List<CheckResult> results = requirements.checkFeatures(null);
        double score = 0;
        for (CheckResult result : results)
            score += result.getScore();
        assertTrue("Results be calculated correctly", score == 6);
    }

    @Test
    public void testRestrictions() {
        ProjectRequirements requirements = new ProjectRequirements();
        requirements.addRestriction(new Restriction("B", 5, Arrays.asList(
                (TestCase) new SimpleTestCase(1, "", "Test B 1"),
                new SimpleTestCase(0, "", "Test B 2")
        )));

        // Get the results
        List<CheckResult> results = requirements.checkRestrictions(null);
        double score = 0;
        for (CheckResult result : results)
            score += result.getScore();
        assertTrue("Results be calculated correctly", score == -2.5);
    }

}
