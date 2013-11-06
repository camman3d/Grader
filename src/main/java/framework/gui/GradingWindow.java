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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Now grading: ");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        userId = new JLabel();
        userId.setText("[User ID]");
        panel1.add(userId, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        mainPanel.add(tabbedPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(14, 13, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setFont(new Font(panel2.getFont().getName(), Font.BOLD, panel2.getFont().getSize()));
        tabbedPane1.addTab("Features", panel2);
        featureName1 = new JLabel();
        featureName1.setText("Feature 1");
        panel2.add(featureName1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel2.add(spacer4, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel2.add(spacer5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        score1 = new JSpinner();
        panel2.add(score1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit1 = new JButton();
        fullCredit1.setText("✓");
        panel2.add(fullCredit1, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel2.add(spacer6, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        max1 = new JLabel();
        max1.setText("20");
        panel2.add(max1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName2 = new JLabel();
        featureName2.setText("Feature 2");
        panel2.add(featureName2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes1 = new JButton();
        notes1.setText("Notes");
        panel2.add(notes1, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel2.add(spacer7, new GridConstraints(1, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        autoGrade1 = new JButton();
        autoGrade1.setText("Grade");
        panel2.add(autoGrade1, new GridConstraints(1, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score2 = new JSpinner();
        panel2.add(score2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max2 = new JLabel();
        max2.setText("Label");
        panel2.add(max2, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit2 = new JButton();
        fullCredit2.setText("✓");
        panel2.add(fullCredit2, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes2 = new JButton();
        notes2.setText("Notes");
        panel2.add(notes2, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade2 = new JButton();
        autoGrade2.setText("Grade");
        panel2.add(autoGrade2, new GridConstraints(2, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score3 = new JSpinner();
        panel2.add(score3, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max3 = new JLabel();
        max3.setText("Label");
        panel2.add(max3, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit3 = new JButton();
        fullCredit3.setText("✓");
        panel2.add(fullCredit3, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit4 = new JButton();
        fullCredit4.setText("✓");
        panel2.add(fullCredit4, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit5 = new JButton();
        fullCredit5.setText("✓");
        panel2.add(fullCredit5, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit6 = new JButton();
        fullCredit6.setText("✓");
        panel2.add(fullCredit6, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit7 = new JButton();
        fullCredit7.setText("✓");
        panel2.add(fullCredit7, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max4 = new JLabel();
        max4.setText("Label");
        panel2.add(max4, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max5 = new JLabel();
        max5.setText("Label");
        panel2.add(max5, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max6 = new JLabel();
        max6.setText("Label");
        panel2.add(max6, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max7 = new JLabel();
        max7.setText("Label");
        panel2.add(max7, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score4 = new JSpinner();
        panel2.add(score4, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score5 = new JSpinner();
        panel2.add(score5, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score6 = new JSpinner();
        panel2.add(score6, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score7 = new JSpinner();
        panel2.add(score7, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName3 = new JLabel();
        featureName3.setText("Feature 2");
        panel2.add(featureName3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName4 = new JLabel();
        featureName4.setText("Feature 2");
        panel2.add(featureName4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName5 = new JLabel();
        featureName5.setText("Feature 2");
        panel2.add(featureName5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName6 = new JLabel();
        featureName6.setText("Feature 2");
        panel2.add(featureName6, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName7 = new JLabel();
        featureName7.setText("Feature 2");
        panel2.add(featureName7, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes3 = new JButton();
        notes3.setText("Notes");
        panel2.add(notes3, new GridConstraints(3, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes4 = new JButton();
        notes4.setText("Notes");
        panel2.add(notes4, new GridConstraints(4, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes5 = new JButton();
        notes5.setText("Notes");
        panel2.add(notes5, new GridConstraints(5, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes6 = new JButton();
        notes6.setText("Notes");
        panel2.add(notes6, new GridConstraints(6, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes7 = new JButton();
        notes7.setText("Notes");
        panel2.add(notes7, new GridConstraints(7, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade3 = new JButton();
        autoGrade3.setText("Grade");
        panel2.add(autoGrade3, new GridConstraints(3, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade4 = new JButton();
        autoGrade4.setText("Grade");
        panel2.add(autoGrade4, new GridConstraints(4, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade5 = new JButton();
        autoGrade5.setText("Grade");
        panel2.add(autoGrade5, new GridConstraints(5, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade6 = new JButton();
        autoGrade6.setText("Grade");
        panel2.add(autoGrade6, new GridConstraints(6, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade7 = new JButton();
        autoGrade7.setText("Grade");
        panel2.add(autoGrade7, new GridConstraints(7, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(new Font(label2.getFont().getName(), Font.BOLD, label2.getFont().getSize()));
        label2.setText("Feature");
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setFont(new Font(label3.getFont().getName(), Font.BOLD, label3.getFont().getSize()));
        label3.setText("Score");
        panel2.add(label3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setFont(new Font(label4.getFont().getName(), Font.BOLD, label4.getFont().getSize()));
        label4.setText("Max");
        panel2.add(label4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setFont(new Font(label5.getFont().getName(), Font.BOLD, label5.getFont().getSize()));
        label5.setText("Full Credit?");
        panel2.add(label5, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setFont(new Font(label6.getFont().getName(), Font.BOLD, label6.getFont().getSize()));
        label6.setText("Leave Notes");
        panel2.add(label6, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setFont(new Font(label7.getFont().getName(), Font.BOLD, label7.getFont().getSize()));
        label7.setText("Auto Grading");
        panel2.add(label7, new GridConstraints(0, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName8 = new JLabel();
        featureName8.setText("Feature 2");
        panel2.add(featureName8, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName9 = new JLabel();
        featureName9.setText("Feature 2");
        panel2.add(featureName9, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName10 = new JLabel();
        featureName10.setText("Feature 2");
        panel2.add(featureName10, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName11 = new JLabel();
        featureName11.setText("Feature 2");
        panel2.add(featureName11, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featureName12 = new JLabel();
        featureName12.setText("Feature 2");
        panel2.add(featureName12, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score8 = new JSpinner();
        panel2.add(score8, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score9 = new JSpinner();
        panel2.add(score9, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score10 = new JSpinner();
        panel2.add(score10, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score11 = new JSpinner();
        panel2.add(score11, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        score12 = new JSpinner();
        panel2.add(score12, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max8 = new JLabel();
        max8.setText("Label");
        panel2.add(max8, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max9 = new JLabel();
        max9.setText("Label");
        panel2.add(max9, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max10 = new JLabel();
        max10.setText("Label");
        panel2.add(max10, new GridConstraints(10, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max11 = new JLabel();
        max11.setText("Label");
        panel2.add(max11, new GridConstraints(11, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        max12 = new JLabel();
        max12.setText("Label");
        panel2.add(max12, new GridConstraints(12, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit8 = new JButton();
        fullCredit8.setText("✓");
        panel2.add(fullCredit8, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit9 = new JButton();
        fullCredit9.setText("✓");
        panel2.add(fullCredit9, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit10 = new JButton();
        fullCredit10.setText("✓");
        panel2.add(fullCredit10, new GridConstraints(10, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit11 = new JButton();
        fullCredit11.setText("✓");
        panel2.add(fullCredit11, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fullCredit12 = new JButton();
        fullCredit12.setText("✓");
        panel2.add(fullCredit12, new GridConstraints(12, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes8 = new JButton();
        notes8.setText("Notes");
        panel2.add(notes8, new GridConstraints(8, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes9 = new JButton();
        notes9.setText("Notes");
        panel2.add(notes9, new GridConstraints(9, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes10 = new JButton();
        notes10.setText("Notes");
        panel2.add(notes10, new GridConstraints(10, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes11 = new JButton();
        notes11.setText("Notes");
        panel2.add(notes11, new GridConstraints(11, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        notes12 = new JButton();
        notes12.setText("Notes");
        panel2.add(notes12, new GridConstraints(12, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade8 = new JButton();
        autoGrade8.setText("Grade");
        panel2.add(autoGrade8, new GridConstraints(8, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade9 = new JButton();
        autoGrade9.setText("Grade");
        panel2.add(autoGrade9, new GridConstraints(9, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade10 = new JButton();
        autoGrade10.setText("Grade");
        panel2.add(autoGrade10, new GridConstraints(10, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade11 = new JButton();
        autoGrade11.setText("Grade");
        panel2.add(autoGrade11, new GridConstraints(11, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        autoGrade12 = new JButton();
        autoGrade12.setText("Grade");
        panel2.add(autoGrade12, new GridConstraints(12, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit1 = new JLabel();
        extraCredit1.setText("★");
        panel2.add(extraCredit1, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        panel2.add(spacer8, new GridConstraints(1, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setFont(new Font(label8.getFont().getName(), Font.BOLD, label8.getFont().getSize()));
        label8.setText("EC");
        panel2.add(label8, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit2 = new JLabel();
        extraCredit2.setText("★");
        panel2.add(extraCredit2, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit3 = new JLabel();
        extraCredit3.setText("★");
        panel2.add(extraCredit3, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit4 = new JLabel();
        extraCredit4.setText("★");
        panel2.add(extraCredit4, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit5 = new JLabel();
        extraCredit5.setText("★");
        panel2.add(extraCredit5, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit6 = new JLabel();
        extraCredit6.setText("★");
        panel2.add(extraCredit6, new GridConstraints(6, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit7 = new JLabel();
        extraCredit7.setText("★");
        panel2.add(extraCredit7, new GridConstraints(7, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit8 = new JLabel();
        extraCredit8.setText("★");
        panel2.add(extraCredit8, new GridConstraints(8, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit9 = new JLabel();
        extraCredit9.setText("★");
        panel2.add(extraCredit9, new GridConstraints(9, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit10 = new JLabel();
        extraCredit10.setText("★");
        panel2.add(extraCredit10, new GridConstraints(10, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit11 = new JLabel();
        extraCredit11.setText("★");
        panel2.add(extraCredit11, new GridConstraints(11, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraCredit12 = new JLabel();
        extraCredit12.setText("★");
        panel2.add(extraCredit12, new GridConstraints(12, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus1 = new JLabel();
        gradeStatus1.setText("Status");
        panel2.add(gradeStatus1, new GridConstraints(1, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults1 = new JButton();
        gradeResults1.setText("Results");
        panel2.add(gradeResults1, new GridConstraints(1, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus2 = new JLabel();
        gradeStatus2.setText("Status");
        panel2.add(gradeStatus2, new GridConstraints(2, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus3 = new JLabel();
        gradeStatus3.setText("Status");
        panel2.add(gradeStatus3, new GridConstraints(3, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus4 = new JLabel();
        gradeStatus4.setText("Status");
        panel2.add(gradeStatus4, new GridConstraints(4, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus5 = new JLabel();
        gradeStatus5.setText("Status");
        panel2.add(gradeStatus5, new GridConstraints(5, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus6 = new JLabel();
        gradeStatus6.setText("Status");
        panel2.add(gradeStatus6, new GridConstraints(6, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus7 = new JLabel();
        gradeStatus7.setText("Status");
        panel2.add(gradeStatus7, new GridConstraints(7, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus8 = new JLabel();
        gradeStatus8.setText("Status");
        panel2.add(gradeStatus8, new GridConstraints(8, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus9 = new JLabel();
        gradeStatus9.setText("Status");
        panel2.add(gradeStatus9, new GridConstraints(9, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus10 = new JLabel();
        gradeStatus10.setText("Status");
        panel2.add(gradeStatus10, new GridConstraints(10, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus11 = new JLabel();
        gradeStatus11.setText("Status");
        panel2.add(gradeStatus11, new GridConstraints(11, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeStatus12 = new JLabel();
        gradeStatus12.setText("Status");
        panel2.add(gradeStatus12, new GridConstraints(12, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults2 = new JButton();
        gradeResults2.setText("Results");
        panel2.add(gradeResults2, new GridConstraints(2, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults3 = new JButton();
        gradeResults3.setText("Results");
        panel2.add(gradeResults3, new GridConstraints(3, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults4 = new JButton();
        gradeResults4.setText("Results");
        panel2.add(gradeResults4, new GridConstraints(4, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults5 = new JButton();
        gradeResults5.setText("Results");
        panel2.add(gradeResults5, new GridConstraints(5, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults6 = new JButton();
        gradeResults6.setText("Results");
        panel2.add(gradeResults6, new GridConstraints(6, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults7 = new JButton();
        gradeResults7.setText("Results");
        panel2.add(gradeResults7, new GridConstraints(7, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults8 = new JButton();
        gradeResults8.setText("Results");
        panel2.add(gradeResults8, new GridConstraints(8, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults9 = new JButton();
        gradeResults9.setText("Results");
        panel2.add(gradeResults9, new GridConstraints(9, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults10 = new JButton();
        gradeResults10.setText("Results");
        panel2.add(gradeResults10, new GridConstraints(10, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults11 = new JButton();
        gradeResults11.setText("Results");
        panel2.add(gradeResults11, new GridConstraints(11, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gradeResults12 = new JButton();
        gradeResults12.setText("Results");
        panel2.add(gradeResults12, new GridConstraints(12, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        featuresPanel = new JPanel();
        featuresPanel.setLayout(new GridLayoutManager(9, 9, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Restrictions", featuresPanel);
        final JLabel label9 = new JLabel();
        label9.setEnabled(true);
        label9.setFont(new Font(label9.getFont().getName(), Font.BOLD, label9.getFont().getSize()));
        label9.setText("Restriction");
        featuresPanel.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        featuresPanel.add(spacer9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        featuresPanel.add(spacer10, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setFont(new Font(label10.getFont().getName(), Font.BOLD, label10.getFont().getSize()));
        label10.setText("Penalty");
        featuresPanel.add(label10, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setFont(new Font(label11.getFont().getName(), Font.BOLD, label11.getFont().getSize()));
        label11.setText("Max");
        featuresPanel.add(label11, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        featuresPanel.add(spacer11, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setFont(new Font(label12.getFont().getName(), Font.BOLD, label12.getFont().getSize()));
        label12.setText("Notes");
        featuresPanel.add(label12, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction1 = new JLabel();
        restriction1.setText("Restriction 1");
        featuresPanel.add(restriction1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax1 = new JLabel();
        rMax1.setText("-5");
        featuresPanel.add(rMax1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore1 = new JSpinner();
        featuresPanel.add(rScore1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes1 = new JButton();
        rNotes1.setText("Notes");
        featuresPanel.add(rNotes1, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction2 = new JLabel();
        restriction2.setText("Restriction 1");
        featuresPanel.add(restriction2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction3 = new JLabel();
        restriction3.setText("Restriction 1");
        featuresPanel.add(restriction3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction4 = new JLabel();
        restriction4.setText("Restriction 1");
        featuresPanel.add(restriction4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction5 = new JLabel();
        restriction5.setText("Restriction 1");
        featuresPanel.add(restriction5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction6 = new JLabel();
        restriction6.setText("Restriction 1");
        featuresPanel.add(restriction6, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restriction7 = new JLabel();
        restriction7.setText("Restriction 1");
        featuresPanel.add(restriction7, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore2 = new JSpinner();
        featuresPanel.add(rScore2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore3 = new JSpinner();
        featuresPanel.add(rScore3, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore4 = new JSpinner();
        featuresPanel.add(rScore4, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore5 = new JSpinner();
        featuresPanel.add(rScore5, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore6 = new JSpinner();
        featuresPanel.add(rScore6, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rScore7 = new JSpinner();
        featuresPanel.add(rScore7, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax2 = new JLabel();
        rMax2.setText("-5");
        featuresPanel.add(rMax2, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax3 = new JLabel();
        rMax3.setText("-5");
        featuresPanel.add(rMax3, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax4 = new JLabel();
        rMax4.setText("-5");
        featuresPanel.add(rMax4, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax5 = new JLabel();
        rMax5.setText("-5");
        featuresPanel.add(rMax5, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax6 = new JLabel();
        rMax6.setText("-5");
        featuresPanel.add(rMax6, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rMax7 = new JLabel();
        rMax7.setText("-5");
        featuresPanel.add(rMax7, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes2 = new JButton();
        rNotes2.setText("Notes");
        featuresPanel.add(rNotes2, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes3 = new JButton();
        rNotes3.setText("Notes");
        featuresPanel.add(rNotes3, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes4 = new JButton();
        rNotes4.setText("Notes");
        featuresPanel.add(rNotes4, new GridConstraints(4, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes5 = new JButton();
        rNotes5.setText("Notes");
        featuresPanel.add(rNotes5, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes6 = new JButton();
        rNotes6.setText("Notes");
        featuresPanel.add(rNotes6, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rNotes7 = new JButton();
        rNotes7.setText("Notes");
        featuresPanel.add(rNotes7, new GridConstraints(7, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        featuresPanel.add(spacer12, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setFont(new Font(label13.getFont().getName(), Font.BOLD, label13.getFont().getSize()));
        label13.setText("Status");
        featuresPanel.add(label13, new GridConstraints(0, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status1 = new JLabel();
        status1.setText("Not Graded");
        featuresPanel.add(status1, new GridConstraints(1, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status2 = new JLabel();
        status2.setText("Not Graded");
        featuresPanel.add(status2, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status3 = new JLabel();
        status3.setText("Not Graded");
        featuresPanel.add(status3, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status4 = new JLabel();
        status4.setText("Not Graded");
        featuresPanel.add(status4, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status5 = new JLabel();
        status5.setText("Not Graded");
        featuresPanel.add(status5, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status6 = new JLabel();
        status6.setText("Not Graded");
        featuresPanel.add(status6, new GridConstraints(6, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        status7 = new JLabel();
        status7.setText("Not Graded");
        featuresPanel.add(status7, new GridConstraints(7, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults1 = new JButton();
        restrictionResults1.setText("Results");
        featuresPanel.add(restrictionResults1, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults2 = new JButton();
        restrictionResults2.setText("Results");
        featuresPanel.add(restrictionResults2, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults3 = new JButton();
        restrictionResults3.setText("Results");
        featuresPanel.add(restrictionResults3, new GridConstraints(3, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults4 = new JButton();
        restrictionResults4.setText("Results");
        featuresPanel.add(restrictionResults4, new GridConstraints(4, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults5 = new JButton();
        restrictionResults5.setText("Results");
        featuresPanel.add(restrictionResults5, new GridConstraints(5, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults6 = new JButton();
        restrictionResults6.setText("Results");
        featuresPanel.add(restrictionResults6, new GridConstraints(6, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restrictionResults7 = new JButton();
        restrictionResults7.setText("Results");
        featuresPanel.add(restrictionResults7, new GridConstraints(7, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setFont(new Font(label14.getFont().getName(), Font.BOLD, label14.getFont().getSize()));
        label14.setText("View Results");
        featuresPanel.add(label14, new GridConstraints(0, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel3, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        saveAndContinueButton = new JButton();
        saveAndContinueButton.setText("Save and Continue");
        panel3.add(saveAndContinueButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveAndQuitButton = new JButton();
        saveAndQuitButton.setText("Save and Quit");
        panel3.add(saveAndQuitButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaveCommentsButton = new JButton();
        leaveCommentsButton.setText("Leave Comments");
        panel3.add(leaveCommentsButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panel3.add(spacer13, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        projectActions = new JPanel();
        projectActions.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(projectActions, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        viewCodeButton = new JButton();
        viewCodeButton.setText("View Code");
        projectActions.add(viewCodeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openFolderButton = new JButton();
        openFolderButton.setText("Open Folder");
        projectActions.add(openFolderButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        runButton = new JButton();
        runButton.setText("Run");
        projectActions.add(runButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        launchButton = new JButton();
        launchButton.setText("Launch");
        projectActions.add(launchButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        projectActions.add(spacer14, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        noProjectActions = new JPanel();
        noProjectActions.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(noProjectActions, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        noProjectActions.add(spacer15, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("No valid project was found.");
        noProjectActions.add(label15, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        openSakaiFolderButton = new JButton();
        openSakaiFolderButton.setText("Open Sakai Folder");
        noProjectActions.add(openSakaiFolderButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Total Score:");
        panel4.add(label16, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalScoreLabel = new JLabel();
        totalScoreLabel.setText("[Score]");
        panel4.add(totalScoreLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        panel4.add(spacer16, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        panel4.add(spacer17, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
