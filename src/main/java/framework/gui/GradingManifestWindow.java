package framework.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import framework.utils.GradingManifest;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/16/14
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class GradingManifestWindow {
    private JButton beginButton;
    private JPanel mainPanel;
    private JPanel downloadPanel;
    private JPanel onyensPanel;
    private JPanel requirementsPanel;
    private JLabel downloadLabel;
    private JButton changeDownloadButton;
    private JList<String> onyensList;

    private Semaphore semaphore = new Semaphore(1);

    private static JFrame frame;

    private GradingManifestWindow(final GradingManifest gradingManifest) {

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (gradingManifest.hasDownloadPath()) {
            String path = gradingManifest.getDownloadPath();
            if (path.length() > 50)
                downloadLabel.setText("..." + path.substring(path.length() - 47));
            else
                downloadLabel.setText(path);
            updateOnyensList(onyensList, path);
        }

        requirementsPanel.setVisible(!gradingManifest.hasProjectRequirements());
        changeDownloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(mainPanel);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getAbsolutePath();
                    gradingManifest.setDownloadPath(path);
                    if (path.length() > 50)
                        downloadLabel.setText("..." + path.substring(path.length() - 47));
                    else
                        downloadLabel.setText(path);
                    updateOnyensList(onyensList, path);
                }
            }
        });

        onyensList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Turn the names of the folders into onyens
                java.util.List<String> onyens = new ArrayList<String>();
                for (String name : onyensList.getSelectedValuesList())
                    onyens.add(name.substring(name.indexOf("(") + 1, name.indexOf(")")));
                gradingManifest.setOnyens(onyens);
                System.out.println(onyens);
            }
        });

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                semaphore.release();
            }
        });
    }

    private static void updateOnyensList(JList<String> list, String path) {
        // TODO: Validate the download folder

        File folder = new File(path);
        java.util.List<String> folders = new ArrayList<String>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files)
                if (file.isDirectory())
                    folders.add(file.getName());
            String[] names = new String[folders.size()];
            names = folders.toArray(names);
            list.setListData(names);
        } else {
            list.setListData(new String[]{});
            // TODO: Show message that there are no items
        }
    }

    public static GradingManifestWindow create(GradingManifest gradingManifest) {

        frame = new JFrame("Grading Settings: " + gradingManifest.getProjectName());
        GradingManifestWindow gradingManifestWindow = new GradingManifestWindow(gradingManifest);
        frame.setContentPane(gradingManifestWindow.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 300);
        frame.setVisible(true);
        return gradingManifestWindow;
    }

    public void await() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
        mainPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        beginButton = new JButton();
        beginButton.setText("Begin");
        panel1.add(beginButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        downloadPanel = new JPanel();
        downloadPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(downloadPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        downloadPanel.setBorder(BorderFactory.createTitledBorder("Download Folder"));
        downloadLabel = new JLabel();
        downloadLabel.setText("");
        downloadPanel.add(downloadLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        downloadPanel.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        changeDownloadButton = new JButton();
        changeDownloadButton.setText("Change");
        downloadPanel.add(changeDownloadButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        mainPanel.add(spacer5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        onyensPanel = new JPanel();
        onyensPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(onyensPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        onyensPanel.setBorder(BorderFactory.createTitledBorder("Select Projects to Grade"));
        onyensList = new JList();
        onyensPanel.add(onyensList, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        requirementsPanel = new JPanel();
        requirementsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(requirementsPanel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        requirementsPanel.setBorder(BorderFactory.createTitledBorder("Requirements"));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
