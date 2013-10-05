package framework.gui;

import javax.swing.*;
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
                    editor = fileChooser.getSelectedFile().getAbsolutePath();
                    editorLabel.setText(editor);
                }
            }
        });
    }

    public static SettingsWindow create() {
        try {
            // TODO: Get previous settings

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
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.exit(1);
        }
    }

    // Getters for the settings

    public String getDownloadPath() {
        return downloadPath;
    }

    public String getEditor() {
        return editor;
    }

    public String getStart() {
        return start.getText();
    }

    public String getEnd() {
        return end.getText();
    }
}
