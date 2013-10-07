package framework.gui;

import framework.execution.NotRunnableException;
import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.navigation.StudentFolder;
import framework.project.Project;
import scala.Option;
import framework.utils.GradingEnvironment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 7:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class GradingWindow {

    private JFrame frame;

    private JLabel userId;
    private JTabbedPane tabbedPane1;
    private JButton saveAndContinueButton;
    private JButton saveAndQuitButton;
    private JButton leaveCommentsButton;
    private JButton viewCodeButton;
    private JButton openFolderButton;
    private JButton runButton;
    private JButton launchButton;
    private JButton openSakaiFolderButton;
    private JPanel mainPanel;
    private JPanel featuresPanel;
    private JPanel projectActions;
    private JPanel noProjectActions;

    // Feature list parts
    private List<FeatureListItem> features;

    private JLabel featureName1;
    private JLabel featureName2;
    private JLabel featureName3;
    private JLabel featureName4;
    private JLabel featureName5;
    private JLabel featureName6;
    private JLabel featureName7;
    private JSpinner score1;
    private JSpinner score2;
    private JSpinner score3;
    private JSpinner score4;
    private JSpinner score5;
    private JSpinner score6;
    private JSpinner score7;
    private JLabel max1;
    private JLabel max2;
    private JLabel max3;
    private JLabel max4;
    private JLabel max5;
    private JLabel max6;
    private JLabel max7;
    private JButton fullCredit1;
    private JButton fullCredit2;
    private JButton fullCredit3;
    private JButton fullCredit4;
    private JButton fullCredit5;
    private JButton fullCredit6;
    private JButton fullCredit7;
    private JButton notes1;
    private JButton notes2;
    private JButton notes3;
    private JButton notes4;
    private JButton notes5;
    private JButton notes6;
    private JButton notes7;
    private JButton autoGrade1;
    private JButton autoGrade2;
    private JButton autoGrade3;
    private JButton autoGrade4;
    private JButton autoGrade5;
    private JButton autoGrade6;
    private JButton autoGrade7;

    // Restrictions parts
    private List<RestrictionListItem> restrictions;

    private JLabel restriction1;
    private JLabel restriction2;
    private JLabel restriction3;
    private JLabel restriction4;
    private JLabel restriction5;
    private JLabel restriction6;
    private JLabel restriction7;
    private JSpinner rScore1;
    private JSpinner rScore2;
    private JSpinner rScore3;
    private JSpinner rScore4;
    private JSpinner rScore5;
    private JSpinner rScore6;
    private JSpinner rScore7;
    private JLabel rMax1;
    private JLabel rMax2;
    private JLabel rMax3;
    private JLabel rMax4;
    private JLabel rMax5;
    private JLabel rMax6;
    private JLabel rMax7;
    private JButton rNotes1;
    private JButton rNotes2;
    private JButton rNotes3;
    private JButton rNotes4;
    private JButton rNotes5;
    private JButton rNotes6;
    private JButton rNotes7;
    private JLabel status1;
    private JLabel status2;
    private JLabel status3;
    private JLabel status4;
    private JLabel status5;
    private JLabel status6;
    private JLabel status7;
    private JLabel totalScoreLabel;
    private JLabel featureName8;
    private JLabel featureName9;
    private JLabel featureName10;
    private JLabel featureName11;
    private JLabel featureName12;
    private JSpinner score8;
    private JSpinner score9;
    private JSpinner score10;
    private JSpinner score11;
    private JSpinner score12;
    private JLabel max8;
    private JLabel max9;
    private JLabel max10;
    private JLabel max11;
    private JLabel max12;
    private JButton fullCredit8;
    private JButton fullCredit9;
    private JButton fullCredit10;
    private JButton fullCredit11;
    private JButton fullCredit12;
    private JButton notes8;
    private JButton notes9;
    private JButton notes10;
    private JButton notes11;
    private JButton notes12;
    private JButton autoGrade8;
    private JButton autoGrade9;
    private JButton autoGrade10;
    private JButton autoGrade11;
    private JButton autoGrade12;
    private JLabel extraCredit1;
    private JLabel extraCredit2;
    private JLabel extraCredit3;
    private JLabel extraCredit4;
    private JLabel extraCredit5;
    private JLabel extraCredit6;
    private JLabel extraCredit7;
    private JLabel extraCredit8;
    private JLabel extraCredit9;
    private JLabel extraCredit10;
    private JLabel extraCredit11;
    private JLabel extraCredit12;
    private TotalScoreUpdater scoreUpdater;

    // Properties dealing with the project
    private StudentFolder folder;
    private Option<Project> project;

    private String comments = "";

    // Properties dealing with finishing
    private Semaphore done = new Semaphore(1);
    private boolean continueGrading = true;

    private GradingWindow() throws InterruptedException {
        done.acquire();

        features = new ArrayList<FeatureListItem>();
        features.add(new FeatureListItem(featureName1, score1, max1, fullCredit1, extraCredit1, notes1, autoGrade1));
        features.add(new FeatureListItem(featureName2, score2, max2, fullCredit2, extraCredit2, notes2, autoGrade2));
        features.add(new FeatureListItem(featureName3, score3, max3, fullCredit3, extraCredit3, notes3, autoGrade3));
        features.add(new FeatureListItem(featureName4, score4, max4, fullCredit4, extraCredit4, notes4, autoGrade4));
        features.add(new FeatureListItem(featureName5, score5, max5, fullCredit5, extraCredit5, notes5, autoGrade5));
        features.add(new FeatureListItem(featureName6, score6, max6, fullCredit6, extraCredit6, notes6, autoGrade6));
        features.add(new FeatureListItem(featureName7, score7, max7, fullCredit7, extraCredit7, notes7, autoGrade7));
        features.add(new FeatureListItem(featureName8, score8, max8, fullCredit8, extraCredit8, notes8, autoGrade8));
        features.add(new FeatureListItem(featureName9, score9, max9, fullCredit9, extraCredit9, notes9, autoGrade9));
        features.add(new FeatureListItem(featureName10, score10, max10, fullCredit10, extraCredit10, notes10, autoGrade10));
        features.add(new FeatureListItem(featureName11, score11, max11, fullCredit11, extraCredit11, notes11, autoGrade11));
        features.add(new FeatureListItem(featureName12, score12, max12, fullCredit12, extraCredit12, notes12, autoGrade12));

        restrictions = new ArrayList<RestrictionListItem>();
        restrictions.add(new RestrictionListItem(restriction1, rScore1, rMax1, rNotes1, status1));
        restrictions.add(new RestrictionListItem(restriction2, rScore2, rMax2, rNotes2, status2));
        restrictions.add(new RestrictionListItem(restriction3, rScore3, rMax3, rNotes3, status3));
        restrictions.add(new RestrictionListItem(restriction4, rScore4, rMax4, rNotes4, status4));
        restrictions.add(new RestrictionListItem(restriction5, rScore5, rMax5, rNotes5, status5));
        restrictions.add(new RestrictionListItem(restriction6, rScore6, rMax6, rNotes6, status6));
        restrictions.add(new RestrictionListItem(restriction7, rScore7, rMax7, rNotes7, status7));

        saveAndQuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                continueGrading = false;
                done.release();
            }
        });
        saveAndContinueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done.release();
            }
        });
        openSakaiFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradingEnvironment.get().open(folder.getFolder());
            }
        });
        viewCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradingEnvironment.get().edit(project.get().getSourceFolder());
            }
        });
        openFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradingEnvironment.get().open(project.get().getSourceFolder());
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    project.get().start("");
                } catch (NotRunnableException e1) {
                    JOptionPane.showMessageDialog(mainPanel, "Error running project.");
                }
            }
        });
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    project.get().launch("");
                } catch (NotRunnableException e1) {
                    JOptionPane.showMessageDialog(mainPanel, "Error running project.");
                }
            }
        });
        leaveCommentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newComments = (String) JOptionPane.showInputDialog(mainPanel,"Please enter in some comments:",
                        "Comments", JOptionPane.QUESTION_MESSAGE, null, null, comments);
                if (newComments != null)
                    comments = newComments;
            }
        });
    }

    private void setFeatures(List<Feature> featureList, List<CheckResult> featureResults) {
        for (int i = 0; i < featureList.size(); i++) {
            if (i < features.size()) {
                features.get(i).setUpdater(scoreUpdater);
                features.get(i).setFeature(featureList.get(i));
                features.get(i).setResult(featureResults.get(i));
                features.get(i).setProject(project);
            } else
                System.out.println("We need more feature slots!!!!");
        }
        for (int i = featureList.size(); i < features.size(); i++)
            features.get(i).hide();
    }

    private void setRestrictions(List<Restriction> restrictionList, List<CheckResult> restrictionResults) {
        for (int i = 0; i < restrictionList.size(); i++) {
            if (i < restrictions.size()) {
                restrictions.get(i).setUpdater(scoreUpdater);
                restrictions.get(i).setRestriction(restrictionList.get(i));
                restrictions.get(i).setResult(restrictionResults.get(i));
            } else
                System.out.println("We need more restriction slots!!!!");
        }
        for (int i = restrictionList.size(); i < restrictions.size(); i++)
            restrictions.get(i).hide();
    }

    public static GradingWindow create(ProjectRequirements requirements, StudentFolder folder, Option<Project> project,
                                       List<CheckResult> featureResults, List<CheckResult> restrictionResults) {
        try {
            JFrame frame = new JFrame("GradingWindow");
            GradingWindow window = new GradingWindow();
            window.frame = frame;
            frame.setContentPane(window.mainPanel);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(640, 600));
            frame.pack();
            frame.setVisible(true);

            // Setup the UI
            window.folder = folder;
            window.project = project;
            window.userId.setText(folder.getUserId());

            // Setup the features/restrictions
            window.scoreUpdater = new TotalScoreUpdater(window.totalScoreLabel, featureResults, restrictionResults);
            window.setFeatures(requirements.getFeatures(), featureResults);
            window.setRestrictions(requirements.getRestrictions(), restrictionResults);

            if (project.isDefined())
                window.noProjectActions.setVisible(false);
            else
                window.projectActions.setVisible(false);

            return window;
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public boolean awaitDone() {
        try {
            done.acquire();
            frame.dispose();
            return continueGrading;
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

    public String getComments() {
        return comments;
    }
}
