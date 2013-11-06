package framework.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/4/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsWindow {
    private JButton changeDownloadButton;
    private JButton changeEditorButton;
    private JButton beginButton;
    private JPanel mainPanel;
    private JLabel downloadPathLabel;
    private JLabel editorLabel;
    private JTextField end;
    private JTextField start;

    // Use the semaphore to allow blocking until the window is closed.
    private JFrame frame;
    private final Semaphore finished = new Semaphore(1);

    // Settings
    private String downloadPath;
    private String editor;

    public SettingsWindow() {
        // Get previous settings
        if (GraderSettings.get().has("editor")) {
            editor = GraderSettings.get().get("editor");
            GradingEnvironment.get().setEditor(editor);
        } else
            editor = GradingEnvironment.get().getEditor();
        editorLabel.setText(editor);
        if (GraderSettings.get().has("path")) {
            downloadPath = GraderSettings.get().get("path");
            downloadPathLabel.setText(GraderSettings.get().get("path"));
        }
        if (GraderSettings.get().has("start"))
            start.setText(GraderSettings.get().get("start"));
        if (GraderSettings.get().has("end"))
            end.setText(GraderSettings.get().get("end"));

        // Clicking the "Begin" button will close the window and release the semaphore.
        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                finished.release();
            }
        });
        changeDownloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    downloadPath = fileChooser.getSelectedFile().getAbsolutePath();
                    downloadPathLabel.setText(downloadPath);
                }
            }
        });
        changeEditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String _editor = fileChooser.getSelectedFile().getAbsolutePath();
                    GradingEnvironment.get().setEditor(_editor);
                    editor = _editor;
                    editorLabel.setText(_editor);
                }
            }
        });
    }

    public static SettingsWindow create() {
        try {
            JFrame frame = new JFrame("Grader Settings");
            SettingsWindow window = new SettingsWindow();
            window.frame = frame;
            window.finished.acquire();
            frame.setContentPane(window.mainPanel);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            return window;
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public void awaitBegin() {
        try {
            finished.acquire();
            // Update the settings
            GraderSettings.get().set("start", start.getText());
            GraderSettings.get().set("end", end.getText());
            GraderSettings.get().set("path", downloadPath);
            GraderSettings.get().set("editor", editor);
            GraderSettings.get().save();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.exit(1);
        }
    }

    // Getters for the settings

    public String getDownloadPath() {
        return downloadPath;
    }

//    public String getEditor() {
//        return editor;
//    }

    public String getStart() {
        return start.getText();
    }

    public String getEnd() {
        return end.getText();
    }

}
