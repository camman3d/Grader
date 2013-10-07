package framework.logging;

import framework.grading.testing.CheckResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 6:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class JsonWritableResults {

    private String userId;
    private List<CheckResult> featureResults;
    private List<CheckResult> restrictionResults;
    private String comments;

    public JsonWritableResults(String userId, List<CheckResult> featureResults, List<CheckResult> restrictionResults, String comments) {
        this.userId = userId;
        this.featureResults = featureResults;
        this.restrictionResults = restrictionResults;
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public List<CheckResult> getFeatureResults() {
        return featureResults;
    }

    public List<CheckResult> getRestrictionResults() {
        return restrictionResults;
    }

    public String getComments() {
        return comments;
    }
}
