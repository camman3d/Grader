package framework.grading.testing;

import org.junit.Test;
import testObjects.SimpleTestCase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestFeature {

    @Test
    public void testGetName() {
        Feature checker = new Feature("checkable", 10);
        assertEquals("Names should match", "checkable", checker.getName());
    }

    @Test
    public void testGetPoints() {
        Feature checker = new Feature("checkable", 10);
        assertTrue("Points should match", checker.getPoints() == 10);
    }

    @Test
    public void testGetExtraCredit() {
        Feature checker = new Feature("checkable", 10, true);
        assertTrue("Extra credit should match", checker.isExtraCredit());
    }

    @Test
    public void testCheck() {
        List<TestCase> testCases = new ArrayList<TestCase>();
        testCases.add(new SimpleTestCase(1, "", "A"));
        testCases.add(new SimpleTestCase(0.5, "", "B"));
        testCases.add(new SimpleTestCase(0, "", "C"));
        Feature checker = new Feature("checkable", 6, testCases);
        CheckResult result = checker.check(null);
        assertTrue("Should have some result", result != null);
        assertTrue("Score should be 3", result.getScore() == 3);
    }
}
