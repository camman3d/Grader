package joshTest.resultTools;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/25/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultHolder {

    private class ScoreResult {
        private double score;

        private double getScore() {
            return score;
        }

        private void setScore(double score) {
            this.score = score;
        }
    }

    private List<ScoreResult> featureResults;
    private List<ScoreResult> restrictionResults;

    public List<ScoreResult> getFeatureResults() {
        return featureResults;
    }

    public void setFeatureResults(List<ScoreResult> featureResults) {
        this.featureResults = featureResults;
    }

    public List<ScoreResult> getRestrictionResults() {
        return restrictionResults;
    }

    public void setRestrictionResults(List<ScoreResult> restrictionResults) {
        this.restrictionResults = restrictionResults;
    }

    public double totalScore() {
        double total = 0;
        if (featureResults != null)
            for (ScoreResult result : featureResults)
                total += result.getScore();
        if (restrictionResults != null)
            for (ScoreResult result : restrictionResults)
                total += result.getScore();
        return total;
    }

    public static ResultHolder fromJson(String json) {
        return new Gson().fromJson(json, ResultHolder.class);
    }

    public static void main(String[] args) throws IOException {
        String json = FileUtils.readFileToString(new File("./log/Assignment7/Abbey, Wesley(wabbey).json"));
        ResultHolder result = ResultHolder.fromJson(json);
        System.out.println(result.totalScore());
    }
}
