package framework.gui;

import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.project.Project;
import scala.Option;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class RestrictionListItem {

    private JLabel name;
    private JSpinner score;
    private JLabel max;
    private JButton notes;
    private JLabel status;
    private TotalScoreUpdater updater;

//    private Restriction restriction;
    private CheckResult result;

    public RestrictionListItem(JLabel name, final JSpinner score, JLabel max, final JButton notes, JLabel status) {
        this.name = name;
        this.score = score;
        this.max = max;
        this.notes = notes;
        this.status = status;

        result = new CheckResult(0, "", CheckResult.CheckStatus.NotGraded, null);

        notes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteString = (String) JOptionPane.showInputDialog(notes, "Please enter in some notes:",
                        "Notes", JOptionPane.QUESTION_MESSAGE, null, null, result.getNotes());
                if (noteString != null)
                    result.setNotes(noteString);
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
    }

    public void setRestriction(Restriction restriction) {
//        this.restriction = restriction;
        name.setText(restriction.getName());
        max.setText(restriction.getPoints() + "");
    }

    public void setResult(CheckResult result) {
        this.result = result;
        score.setValue(result.getScore());
        if (result.getStatus() == CheckResult.CheckStatus.Successful)
            status.setText("Graded");
    }

    public CheckResult getResult() {
        return result;
    }

    public void hide() {
        name.setVisible(false);
        score.setVisible(false);
        max.setVisible(false);
        notes.setVisible(false);
        status.setVisible(false);
    }

    public void setUpdater(TotalScoreUpdater updater) {
        this.updater = updater;
    }

}
