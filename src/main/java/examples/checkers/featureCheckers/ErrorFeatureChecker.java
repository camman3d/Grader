package examples.checkers.featureCheckers;

import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;

/**
 * This always errors out (returns null)
 */
public class ErrorFeatureChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        return null;
    }
}
