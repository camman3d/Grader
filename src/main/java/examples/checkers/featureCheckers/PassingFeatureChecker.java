package examples.checkers.featureCheckers;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;

/**
 * This always passes w/ full credit
 */
public class PassingFeatureChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        return new ACheckResult() {{
            setScore(feature.getMax());
        }};
    }
}
