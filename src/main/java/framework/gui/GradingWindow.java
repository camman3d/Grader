package framework.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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
    private JLabel gradeStatus1;
    private JLabel gradeStatus2;
    private JLabel gradeStatus3;
    private JLabel gradeStatus4;
    private JLabel gradeStatus5;
    private JLabel gradeStatus6;
    private JLabel gradeStatus7;
    private JLabel gradeStatus8;
    private JLabel gradeStatus9;
    private JLabel gradeStatus10;
    private JLabel gradeStatus11;
    private JLabel gradeStatus12;
    private JButton gradeResults1;
    private JButton gradeResults2;
    private JButton gradeResults3;
    private JButton gradeResults4;
    private JButton gradeResults5;
    private JButton gradeResults6;
    private JButton gradeResults7;
    private JButton gradeResults8;
    private JButton gradeResults9;
    private JButton gradeResults10;
    private JButton gradeResults11;
    private JButton gradeResults12;
    private JButton restrictionResults1;
    private JButton restrictionResults2;
    private JButton restrictionResults3;
    private JButton restrictionResults4;
    private JButton restrictionResults5;
    private JButton restrictionResults6;
    private JButton restrictionResults7;
    private JLabel featureName13;
    private JLabel featureName14;
    private JLabel featureName15;
    private JLabel featureName16;
    private JLabel featureName17;
    private JLabel featureName18;
    private JLabel featureName19;
    private JLabel featureName20;
    private JLabel featureName21;
    private JLabel featureName22;
    private JSpinner score13;
    private JSpinner score14;
    private JSpinner score15;
    private JSpinner score16;
    private JSpinner score17;
    private JSpinner score18;
    private JSpinner score19;
    private JSpinner score20;
    private JSpinner score21;
    private JSpinner score22;
    private JButton fullCredit13;
    private JLabel max13;
    private JLabel max14;
    private JLabel max15;
    private JLabel max16;
    private JLabel max17;
    private JLabel max18;
    private JLabel max19;
    private JLabel max20;
    private JLabel max21;
    private JLabel max22;
    private JButton fullCredit14;
    private JButton fullCredit15;
    private JButton fullCredit16;
    private JButton fullCredit17;
    private JButton fullCredit18;
    private JButton fullCredit19;
    private JButton fullCredit20;
    private JButton fullCredit21;
    private JButton fullCredit22;
    private JLabel extraCredit13;
    private JLabel extraCredit14;
    private JLabel extraCredit15;
    private JLabel extraCredit16;
    private JLabel extraCredit17;
    private JLabel extraCredit18;
    private JLabel extraCredit19;
    private JLabel extraCredit20;
    private JLabel extraCredit21;
    private JLabel extraCredit22;
    private JButton notes13;
    private JButton notes14;
    private JButton notes15;
    private JButton notes16;
    private JButton notes17;
    private JButton notes18;
    private JButton notes19;
    private JButton notes20;
    private JButton notes21;
    private JButton notes22;
    private JLabel gradeStatus13;
    private JLabel gradeStatus14;
    private JLabel gradeStatus15;
    private JLabel gradeStatus16;
    private JLabel gradeStatus17;
    private JLabel gradeStatus18;
    private JLabel gradeStatus19;
    private JLabel gradeStatus20;
    private JLabel gradeStatus21;
    private JLabel gradeStatus22;
    private JButton autoGrade13;
    private JButton autoGrade14;
    private JButton autoGrade15;
    private JButton autoGrade16;
    private JButton autoGrade17;
    private JButton autoGrade18;
    private JButton autoGrade19;
    private JButton autoGrade20;
    private JButton autoGrade21;
    private JButton autoGrade22;
    private JButton gradeResults13;
    private JButton gradeResults14;
    private JButton gradeResults15;
    private JButton gradeResults16;
    private JButton gradeResults17;
    private JButton gradeResults18;
    private JButton gradeResults19;
    private JButton gradeResults20;
    private JButton gradeResults21;
    private JButton gradeResults22;
    private JTabbedPane Feature;
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
        features.add(new FeatureListItem(featureName1, score1, max1, fullCredit1, extraCredit1, notes1, gradeStatus1, autoGrade1, gradeResults1));
        features.add(new FeatureListItem(featureName2, score2, max2, fullCredit2, extraCredit2, notes2, gradeStatus2, autoGrade2, gradeResults2));
        features.add(new FeatureListItem(featureName3, score3, max3, fullCredit3, extraCredit3, notes3, gradeStatus3, autoGrade3, gradeResults3));
        features.add(new FeatureListItem(featureName4, score4, max4, fullCredit4, extraCredit4, notes4, gradeStatus4, autoGrade4, gradeResults4));
        features.add(new FeatureListItem(featureName5, score5, max5, fullCredit5, extraCredit5, notes5, gradeStatus5, autoGrade5, gradeResults5));
        features.add(new FeatureListItem(featureName6, score6, max6, fullCredit6, extraCredit6, notes6, gradeStatus6, autoGrade6, gradeResults6));
        features.add(new FeatureListItem(featureName7, score7, max7, fullCredit7, extraCredit7, notes7, gradeStatus7, autoGrade7, gradeResults7));
        features.add(new FeatureListItem(featureName8, score8, max8, fullCredit8, extraCredit8, notes8, gradeStatus8, autoGrade8, gradeResults8));
        features.add(new FeatureListItem(featureName9, score9, max9, fullCredit9, extraCredit9, notes9, gradeStatus9, autoGrade9, gradeResults9));
        features.add(new FeatureListItem(featureName10, score10, max10, fullCredit10, extraCredit10, notes10, gradeStatus10, autoGrade10, gradeResults10));
        features.add(new FeatureListItem(featureName11, score11, max11, fullCredit11, extraCredit11, notes11, gradeStatus11, autoGrade11, gradeResults11));
        features.add(new FeatureListItem(featureName12, score12, max12, fullCredit12, extraCredit12, notes12, gradeStatus12, autoGrade12, gradeResults12));
        features.add(new FeatureListItem(featureName13, score13, max13, fullCredit13, extraCredit13, notes13, gradeStatus13, autoGrade13, gradeResults13));
        features.add(new FeatureListItem(featureName14, score14, max14, fullCredit14, extraCredit14, notes14, gradeStatus14, autoGrade14, gradeResults14));
        features.add(new FeatureListItem(featureName15, score15, max15, fullCredit15, extraCredit15, notes15, gradeStatus15, autoGrade15, gradeResults15));
        features.add(new FeatureListItem(featureName16, score16, max16, fullCredit16, extraCredit16, notes16, gradeStatus16, autoGrade16, gradeResults16));
        features.add(new FeatureListItem(featureName17, score17, max17, fullCredit17, extraCredit17, notes17, gradeStatus17, autoGrade17, gradeResults17));
        features.add(new FeatureListItem(featureName18, score18, max18, fullCredit18, extraCredit18, notes18, gradeStatus18, autoGrade18, gradeResults18));
        features.add(new FeatureListItem(featureName19, score19, max19, fullCredit19, extraCredit19, notes19, gradeStatus19, autoGrade19, gradeResults19));
        features.add(new FeatureListItem(featureName20, score20, max20, fullCredit20, extraCredit20, notes20, gradeStatus20, autoGrade20, gradeResults20));
        features.add(new FeatureListItem(featureName21, score21, max21, fullCredit21, extraCredit21, notes21, gradeStatus21, autoGrade21, gradeResults21));
        features.add(new FeatureListItem(featureName22, score22, max22, fullCredit22, extraCredit22, notes22, gradeStatus22, autoGrade22, gradeResults22));

        restrictions = new ArrayList<RestrictionListItem>();
        restrictions.add(new RestrictionListItem(restriction1, rScore1, rMax1, rNotes1, status1, restrictionResults1));
        restrictions.add(new RestrictionListItem(restriction2, rScore2, rMax2, rNotes2, status2, restrictionResults2));
        restrictions.add(new RestrictionListItem(restriction3, rScore3, rMax3, rNotes3, status3, restrictionResults3));
        restrictions.add(new RestrictionListItem(restriction4, rScore4, rMax4, rNotes4, status4, restrictionResults4));
        restrictions.add(new RestrictionListItem(restriction5, rScore5, rMax5, rNotes5, status5, restrictionResults5));
        restrictions.add(new RestrictionListItem(restriction6, rScore6, rMax6, rNotes6, status6, restrictionResults6));
        restrictions.add(new RestrictionListItem(restriction7, rScore7, rMax7, rNotes7, status7, restrictionResults7));

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
                    project.get().launchInteractive();
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
            frame.setPreferredSize(new Dimension(1000, 600));
//            frame.setSize(new Dimension(2000, 2000));
            frame.pack();
            frame.setVisible(true);
//            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

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
