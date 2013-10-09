package grader.assignment.testObjects;

import grader.checkers.ACheckResult;
import grader.checkers.AnAbstractFeatureChecker;
import grader.checkers.CheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class GoodFauxFeatureChecker extends AnAbstractFeatureChecker {
    @Override
    public CheckResult check() {
        CheckResult result = new ACheckResult();
        result.setScore(feature.getScore());
        return result;
    }
}
