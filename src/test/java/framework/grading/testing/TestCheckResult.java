package framework.grading.testing;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestCheckResult {

    @Test
    public void testSaveAndGetResults() {
        CheckResult result = new CheckResult(1, null);
        result.save(new TestCaseResult(1, "one"));
        result.save(new TestCaseResult(1, "two"));
        result.save(new TestCaseResult(1, "three"));
        assertTrue("There should be three things saved", result.getResults().size() == 3);
    }

    @Test
    public void testGetScore() {
        CheckResult result = new CheckResult(2, null);
        result.save(new TestCaseResult(1, "one"));
        result.save(new TestCaseResult(0.5, "two"));
        result.save(new TestCaseResult(0, "three"));
        assertTrue("Score should be three", result.getScore() == 3);
    }

//    @Test
//    public void testGetNoteSummary() {
//        CheckResult result = new CheckResult(1, null);
//        result.save(new TestCaseResult(true, "First note", "A"));
//        result.save(new TestCaseResult(true, "Second note", "B"));
//        assertTrue("There should be some notes", result.getResultNotes().split("\n").length >= 4);
//    }
}
