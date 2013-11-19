package framework.grading;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import testObjects.SimpleTestCase;

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
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements();
        requirements.addFeature(new Feature("A", 12,
                new SimpleTestCase(1, "", "Test A 1"),
                new SimpleTestCase(0.5, "", "Test A 2"),
                new SimpleTestCase(0, "", "Test A 3")
        ));

        // Get the results
        List<CheckResult> results = requirements.checkFeatures(null);
        double score = 0;
        for (CheckResult result : results)
            score += result.getScore();
        assertTrue("Feature results are calculated correctly", score == 6);
    }

    @Test
    public void testRestrictions() {
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements();
        requirements.addRestriction(new Restriction("B", 5,
                new SimpleTestCase(1, "", "Test B 1"),
                new SimpleTestCase(0, "", "Test B 2")
        ));

        // Get the results
        List<CheckResult> results = requirements.checkRestrictions(null);
        double score = 0;
        for (CheckResult result : results)
            score += result.getScore();
        assertTrue("Restriction results are calculated correctly", score == -2.5);
    }

    @Test
    public void testCheckDueDate() throws Exception {
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements();
        requirements.addDueDate("10/15/2013 11:00:00", 1.5);
        requirements.addDueDate("10/15/2013 12:00:00", 1);
        requirements.addDueDate("10/15/2013 13:00:00", 0.5);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        DateTime time1 = formatter.parseDateTime("10/15/2013 10:30:00");
        DateTime time2 = formatter.parseDateTime("10/15/2013 11:30:00");
        DateTime time3 = formatter.parseDateTime("10/15/2013 12:30:00");
        DateTime time4 = formatter.parseDateTime("10/15/2013 13:30:00");

        assertTrue(requirements.checkDueDate(time1) == 1.5);
        assertTrue(requirements.checkDueDate(time2) == 1);
        assertTrue(requirements.checkDueDate(time3) == 0.5);
        assertTrue(requirements.checkDueDate(time4) == 0);
    }
}
