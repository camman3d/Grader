package examples.checkers.featureCheckers;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;

/**
 * This always fails
 */
public class FailingFeatureChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        return new ACheckResult() {{
            setScore(0);
        }};
    }
}
