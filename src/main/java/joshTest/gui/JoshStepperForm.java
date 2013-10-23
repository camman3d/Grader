package joshTest.gui;

import grader.assignment.GradingFeature;
import grader.assignment.GradingFeatureList;
import grader.sakai.project.ProjectStepper;
import grader.sakai.project.SakaiProject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/10/13
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class JoshStepperForm {
    private JLabel userIdLabel;
    private JPanel mainPanel;
    private JButton previousButton;
    private JButton nextButton;
    private JButton doneButton;
    private JButton gradeButton;
    private JLabel featureLabel1;
    private JLabel featureLabel2;
    private JLabel featureLabel3;
    private JLabel featureLabel4;
    private JLabel featureLabel5;
    private JLabel featureLabel6;
    private JLabel featureLabel7;
    private JLabel featureLabel8;
    private JLabel featureLabel9;
    private JLabel featureLabel10;
    private JLabel featureLabel11;
    private JLabel featureLabel12;
    private JLabel featureLabel13;
    private JLabel featureLabel14;
    private JLabel featureLabel15;
    private JLabel featureLabel16;
    private JLabel featureLabel17;
    private JLabel featureLabel18;
    private JLabel featureLabel19;
    private JLabel featureLabel20;
    private JSpinner score2;
    private JSpinner score3;
    private JSpinner score4;
    private JSpinner score1;
    private JSpinner score5;
    private JSpinner score6;
    private JSpinner score7;
    private JSpinner score8;
    private JSpinner score9;
    private JSpinner score10;
    private JSpinner score11;
    private JSpinner score12;
    private JSpinner score13;
    private JSpinner score14;
    private JSpinner score15;
    private JSpinner score16;
    private JSpinner score17;
    private JSpinner score18;
    private JSpinner score19;
    private JSpinner score20;
    private JLabel max1;
    private JLabel max2;
    private JLabel max3;
    private JLabel max4;
    private JLabel max5;
    private JLabel max6;
    private JLabel max7;
    private JLabel max8;
    private JLabel max9;
    private JLabel max10;
    private JLabel max11;
    private JLabel max12;
    private JLabel max13;
    private JLabel max14;
    private JLabel max15;
    private JLabel max16;
    private JLabel max17;
    private JLabel max18;
    private JLabel max19;
    private JLabel max20;
    private JButton fullCredit1;
    private JButton fullCredit2;
    private JButton fullCredit3;
    private JButton fullCredit4;
    private JButton fullCredit5;
    private JButton fullCredit6;
    private JButton fullCredit7;
    private JButton fullCredit8;
    private JButton fullCredit9;
    private JButton fullCredit10;
    private JButton fullCredit11;
    private JButton fullCredit12;
    private JButton fullCredit13;
    private JButton fullCredit14;
    private JButton fullCredit15;
    private JButton fullCredit16;
    private JButton fullCredit17;
    private JButton fullCredit18;
    private JButton fullCredit19;
    private JButton fullCredit20;
    private JButton gradeButton1;
    private JButton gradeButton2;
    private JButton gradeButton3;
    private JButton gradeButton4;
    private JButton gradeButton5;
    private JButton gradeButton6;
    private JButton gradeButton7;
    private JButton gradeButton8;
    private JButton gradeButton9;
    private JButton gradeButton10;
    private JButton gradeButton11;
    private JButton gradeButton12;
    private JButton gradeButton13;
    private JButton gradeButton14;
    private JButton gradeButton15;
    private JButton gradeButton16;
    private JButton gradeButton17;
    private JButton gradeButton18;
    private JButton gradeButton19;
    private JButton gradeButton20;
    private JButton notesButton1;
    private JButton notesButton2;
    private JButton notesButton3;
    private JButton notesButton4;
    private JButton notesButton5;
    private JButton notesButton6;
    private JButton notesButton7;
    private JButton notesButton8;
    private JButton notesButton9;
    private JButton notesButton10;
    private JButton notesButton11;
    private JButton notesButton12;
    private JButton notesButton13;
    private JButton notesButton14;
    private JButton notesButton15;
    private JButton notesButton16;
    private JButton notesButton17;
    private JButton notesButton18;
    private JButton notesButton19;
    private JButton notesButton20;

    private ProjectStepper stepper;
    private JFrame frame;

    // features
    private JLabel[] names;
    private JSpinner[] scores;
    private JLabel[] maxes;
    private JButton[] fullCredits;
    private JButton[] gradeButtons;
    private JButton[] notesButtons;

    public JoshStepperForm(final ProjectStepper stepper) {
        this.stepper = stepper;

        stepper.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateUI();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepper.previous();
                updateUI();
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepper.next();
                updateUI();
            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stepper.done();
                updateUI();
            }
        });
    }

    private void makeFeatureComponentList() {
        names = new JLabel[] {
                featureLabel1, featureLabel2, featureLabel3, featureLabel4, featureLabel5,
                featureLabel6, featureLabel7, featureLabel8, featureLabel9, featureLabel10,
                featureLabel11, featureLabel12, featureLabel13, featureLabel14, featureLabel15,
                featureLabel16, featureLabel17, featureLabel18, featureLabel19, featureLabel20
        };
        scores = new JSpinner[] {
                score1, score2, score3, score4, score5, score6, score7, score8, score9, score10,
                score11, score12, score13, score14, score15, score16, score17, score18, score19, score20
        };
        maxes = new JLabel[] {
                max1, max2, max3, max4, max5, max6, max7, max8, max9, max10,
                max11, max12, max13, max14, max15, max16, max17, max18, max19, max20
        };
        fullCredits = new JButton[] {
                fullCredit1, fullCredit2, fullCredit3, fullCredit4, fullCredit5,
                fullCredit6, fullCredit7, fullCredit8, fullCredit9, fullCredit10,
                fullCredit11, fullCredit12, fullCredit13, fullCredit14, fullCredit15,
                fullCredit16, fullCredit17, fullCredit18, fullCredit19, fullCredit20
        };
        gradeButtons = new JButton[] {
                gradeButton1, gradeButton2, gradeButton3, gradeButton4, gradeButton5,
                gradeButton6, gradeButton7, gradeButton8, gradeButton9, gradeButton10,
                gradeButton11, gradeButton12, gradeButton13, gradeButton14, gradeButton15,
                gradeButton16, gradeButton17, gradeButton18, gradeButton19, gradeButton20
        };
        notesButtons = new JButton[] {
                notesButton1, notesButton2, notesButton3, notesButton4, notesButton5,
                notesButton6, notesButton7, notesButton8, notesButton9, notesButton10,
                notesButton11, notesButton12, notesButton13, notesButton14, notesButton15,
                notesButton16, notesButton17, notesButton18, notesButton19, notesButton20
        };

        // Add listeners
        for (int i = 0; i < fullCredits.length; i++) {
            final int index = i;

            // Clicking on the full credit button awards maximum points
            fullCredits[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GradingFeature feature = stepper.getGradingFeatures().get(index);
                    feature.setScore(feature.getMax());
                    updateUI();
                }
            });

            // Updating the score field saves that score in the feature
            scores[i].addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {

                    // Use pureSetScore so the comment dialog doesn't pop up
                    GradingFeature feature = stepper.getGradingFeatures().get(index);
                    if (scores[index].getValue() instanceof Double)
                        feature.pureSetScore((Double) scores[index].getValue());
                    if (scores[index].getValue() instanceof Integer)
                        feature.pureSetScore((Integer) scores[index].getValue());
                    updateUI();
                }
            });

            gradeButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GradingFeature feature = stepper.getGradingFeatures().get(index);
                    feature.autoGrade();
                    updateUI();
                }
            });

            notesButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GradingFeature feature = stepper.getGradingFeatures().get(index);
                    feature.comment();
                }
            });
        }
    }

    private void updateUI() {
        // Set the name
        SakaiProject project = stepper.getProject();

//        SakaiProject project = null;
        if (project != null) {
            // Show the student name
            userIdLabel.setText(project.getStudentAssignment().getStudentName());

            // Enable/disable buttons
            previousButton.setEnabled(stepper.prePrevious());
            nextButton.setEnabled(stepper.preNext());
            doneButton.setEnabled(stepper.preDone());

            // Show the features
            setFeatures();
        } else {
            System.out.println("Invalid project");
        }

//        frame.pack();
    }

    private void setFeatures() {
        GradingFeatureList features = stepper.getProjectDatabase().getGradingFeatures();
        if (names == null)
            makeFeatureComponentList();
        for (int i = 0; i < features.size(); i++) {
            GradingFeature feature = features.get(i);
            names[i].setText(feature.getFeature());
            maxes[i].setText(feature.getMax() + "");
            scores[i].setValue(feature.getScore());

            names[i].setVisible(true);
            scores[i].setVisible(true);
            maxes[i].setVisible(true);
            fullCredits[i].setVisible(true);
            gradeButtons[i].setVisible(true);
            notesButtons[i].setVisible(true);
        }
        for (int i = features.size(); i < names.length; i++) {
            names[i].setVisible(false);
            scores[i].setVisible(false);
            maxes[i].setVisible(false);
            fullCredits[i].setVisible(false);
            gradeButtons[i].setVisible(false);
            notesButtons[i].setVisible(false);
        }
    }

    /**
     * This creates a new gui and associates it with a project stepper
     * @param stepper The project stepper powering it
     */
    public static void create(ProjectStepper stepper) {
        JoshStepperForm form = new JoshStepperForm(stepper);
        form.frame = new JFrame("COMP 401 Grading Tool");
        form.frame.setContentPane(form.mainPanel);
        form.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.frame.pack();
        form.frame.setVisible(true);
    }
}
