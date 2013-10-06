package framework.gui;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.TestCaseResult;
import framework.project.Project;
import scala.Option;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureListItem {

    private JLabel name;
    private JSpinner score;
    private JLabel max;
    private JButton fullCredit;
    private JButton notes;
    private JButton autoGrade;

    private Feature feature;
    private CheckResult result;

    public FeatureListItem(JLabel name, final JSpinner score, JLabel max, JButton fullCredit, final JButton notes, JButton autoGrade) {
        this.name = name;
        this.score = score;
        this.max = max;
        this.fullCredit = fullCredit;
        this.notes = notes;
        this.autoGrade = autoGrade;

        result = new CheckResult(0, "", CheckResult.CheckStatus.NotGraded);

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
            }
        });
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
        name.setText(feature.getName());
        max.setText(feature.getPoints() + "");
    }

    public void setResult(CheckResult result) {
        this.result = result;
        score.setValue(result.getScore());
        if (result.getStatus() == CheckResult.CheckStatus.Successful) {
            autoGrade.setEnabled(false);
            autoGrade.setText("Graded");
        }
        if (result.getStatus() == CheckResult.CheckStatus.Failed) {
            autoGrade.setEnabled(false);
            autoGrade.setText("Cannot Grade");
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
        notes.setVisible(false);
        autoGrade.setVisible(false);
    }

    public void setProject(final Option<Project> project) {
        if (project.isDefined()) {
            autoGrade.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setResult(feature.check(project.get()));
                }
            });
        } else {
            autoGrade.setEnabled(false);
            autoGrade.setText("Cannot Grade");
        }
    }
}
