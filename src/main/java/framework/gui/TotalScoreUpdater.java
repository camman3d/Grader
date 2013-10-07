package framework.gui;

import framework.grading.testing.CheckResult;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/7/13
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class TotalScoreUpdater {

    private JLabel scoreLabel;
    private List<CheckResult> featureResults;
    private List<CheckResult> restrictionResults;

    public TotalScoreUpdater(JLabel scoreLabel, List<CheckResult> featureResults, List<CheckResult> restrictionResults) {
        this.scoreLabel = scoreLabel;
        this.featureResults = featureResults;
        this.restrictionResults = restrictionResults;
    }

    public void update() {
        double score = 0;
        for (CheckResult result : featureResults)
            score += result.getScore();
        for (CheckResult result : restrictionResults)
            score += result.getScore();
        scoreLabel.setText(score + "");
    }
}
