package framework.grading.testing;

import org.junit.Test;
import testObjects.SimpleTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestBasicTestCase {

    @Test
    public void testGetName() {
        TestCase testCase = new SimpleTestCase("Foo bar");
        assertEquals("Names should match", "Foo bar", testCase.getName());
    }

    @Test
    public void testSetChecker() {
        SimpleTestCase testCase = new SimpleTestCase("Foo bar");
        Checkable checkable = new Feature("The checkable", 5);
        testCase.setCheckable(checkable);
        assertEquals("setCheckable should set the checkable", checkable, testCase.getChecker());
    }

    @Test
    public void testTest() {
        SimpleTestCase testCase = new SimpleTestCase(0.4, "Some notes", "Foo bar");
        TestCaseResult result = testCase.test(null, true);
        assertEquals("Names should match", result.getName(), "Foo bar");
        assertEquals("Notes should match", result.getNotes(), "Some notes");
        assertTrue("Percentages should match", result.getPercentage() == 0.4);
    }
}
