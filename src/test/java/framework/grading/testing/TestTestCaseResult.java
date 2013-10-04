package framework.grading.testing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestTestCaseResult {

    @Test
    public void testCreation1() {
        TestCaseResult result = new TestCaseResult(0.4, "Foo");
        assertTrue("Percentages should be equal", result.getPercentage() == 0.4);
        assertEquals("Names should match", "Foo", result.getName());
    }

    @Test
    public void testCreation2() {
        TestCaseResult result = new TestCaseResult(0.4, "Some notes", "Foo");
        assertTrue("Percentages should be equal", result.getPercentage() == 0.4);
        assertEquals("Notes should match", "Some notes", result.getNotes());
        assertEquals("Names should match", "Foo", result.getName());
    }

    @Test
    public void testCreation3() {
        TestCaseResult result = new TestCaseResult(true, "Foo");
        assertTrue("Percentages should be equal", result.getPercentage() == 1);
        assertEquals("Names should match", "Foo", result.getName());
    }

    @Test
    public void testCreation4() {
        TestCaseResult result = new TestCaseResult(false, "Some notes", "Foo");
        assertTrue("Percentages should be equal", result.getPercentage() == 0);
        assertEquals("Notes should match", "Some notes", result.getNotes());
        assertEquals("Names should match", "Foo", result.getName());
    }


}
