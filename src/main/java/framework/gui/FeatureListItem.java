package framework.gui;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import scala.Option;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This represents a row in the feature list and handles setting up all the listeners.
 */
public class FeatureListItem {

    private JLabel name;
    private JSpinner score;
    private JLabel max;
    private JButton fullCredit;
    private JLabel extraCredit;
    private JButton notes;
    private JLabel gradeStatus;
    private JButton autoGrade;
    private JButton gradeResults;

    private TotalScoreUpdater updater;

    private Feature feature;
    private CheckResult result;

    public FeatureListItem(JLabel name, JSpinner score, JLabel max, JButton fullCredit, JLabel extraCredit,
                           JButton notes, JLabel gradeStatus, JButton autoGrade, JButton gradeResults) {

        this.name = name;
        this.score = score;
        this.max = max;
        this.fullCredit = fullCredit;
        this.extraCredit = extraCredit;
        this.notes = notes;
        this.gradeStatus = gradeStatus;
        this.autoGrade = autoGrade;
        this.gradeResults = gradeResults;

        gradeStatus.setText("Not Graded");
        gradeResults.setEnabled(false);

        result = null;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
        name.setText(feature.getName());
        max.setText(feature.getPoints() + "");
        if (!feature.isExtraCredit())
            extraCredit.setVisible(false);
    }

    public void setResult(CheckResult result) {
        setResult(result, false);
    }

    public void setResult(CheckResult _result, boolean manual) {

        // If this is the first time then initialize the listeners
        if (this.result == null) {
            notes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String noteString = (String) JOptionPane.showInputDialog(notes, "Please enter in some notes:",
                            "Notes", JOptionPane.QUESTION_MESSAGE, null, null, result.getNotes());
                    if (noteString != null)
                        result.setNotes(noteString);
                }
            });
            fullCredit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result.setScore(feature.getPoints());
                    score.setValue(feature.getPoints());
                    updater.update();
                }
            });
            score.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (score.getValue() instanceof Double)
                        result.setScore((Double) score.getValue());
                    if (score.getValue() instanceof Integer)
                        result.setScore((Integer) score.getValue());
                    updater.update();
                }
            });
            gradeResults.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = "";
                    for (TestCaseResult testResult : result.getResults()) {
                        message += testResult.getName() + ": " + (testResult.getPercentage() * 100) + "% \n";
                        if (!testResult.getNotes().isEmpty())
                            message += "  -- " + testResult.getNotes() + "\n";
                    }
                    JOptionPane.showMessageDialog(gradeResults, message, "Result notes", JOptionPane.PLAIN_MESSAGE);
                }
            });
            this.result = _result;
        } else {
            // Copy the values. The original object needs to stay the same, as it is used in other scopes
            this.result.setNotes(_result.getNotes());
            this.result.setScore(_result.getScore());
            this.result.setStatus(_result.getStatus());
            this.result.setResults(_result.getResults());
            this.result.setTarget(_result.getTarget());
        }

        score.setValue(_result.getScore());
        if (_result.getStatus() == CheckResult.CheckStatus.Successful) {
            autoGrade.setText("Regrade");
            gradeStatus.setText("Graded");
            gradeResults.setEnabled(true);
        }
        if (_result.getStatus() == CheckResult.CheckStatus.NotGraded && manual) {
            autoGrade.setEnabled(true);
            gradeStatus.setText("Cannot Grade");
            gradeResults.setEnabled(false);
        }
        if (_result.getStatus() == CheckResult.CheckStatus.Failed) {
            autoGrade.setEnabled(false);
            gradeStatus.setText("Cannot Grade");
            gradeResults.setEnabled(false);
        }
    }

    public CheckResult getResult() {
        return result;
    }

    public void hide() {
        name.setVisible(false);
        score.setVisible(false);
        max.setVisible(false);
        fullCredit.setVisible(false);
        extraCredit.setVisible(false);
        notes.setVisible(false);
        gradeStatus.setVisible(false);
        autoGrade.setVisible(false);
        gradeResults.setVisible(false);
    }

    public void setProject(final Option<Project> project) {
        if (project.isDefined()) {
            autoGrade.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setResult(feature.check(project.get(), false), true);
                }
            });
        } else {
            autoGrade.setEnabled(false);
            autoGrade.setText("Cannot Grade");
        }
    }

    public void setUpdater(TotalScoreUpdater updater) {
        this.updater = updater;
    }
}
