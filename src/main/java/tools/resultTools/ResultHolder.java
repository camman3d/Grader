package tools.resultTools;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This is a simple score holder. Use this turn the .json result files into some usable object.
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
    private double latePenalty;

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

    public double getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(double latePenalty) {
        this.latePenalty = latePenalty;
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
        String json = FileUtils.readFileToString(new File("./log/Assignment8/Abbey, Wesley(wabbey).json"));
        ResultHolder result = ResultHolder.fromJson(json);
        System.out.println(result.totalScore());
        System.out.println(result.getLatePenalty());
    }
}
