package grading.testing;

import project.Project;

/**
 * Like AnAbstractFeatureChecker
 */
public interface TestCase {

    /**
     * @return A name or short description about the test case.
     */
    public String getName();

    /**
     * This tests the project to see if somethings is the way it is supposed to be
     * @param project The project to test
     * @return A {@link TestCaseResult} containing the result and any notes.
     */
    public TestCaseResult test(Project project);

    /**
     * Sets the checkable containing this test case.
     * @param checkable The check to set as the owner.
     */
    public void setCheckable(Checkable checkable);
}
