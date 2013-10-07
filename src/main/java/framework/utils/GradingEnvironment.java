package framework.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/5/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GradingEnvironment {

    private static final String[] macEditors = new String[]{
        "/Applications/Sublime Text 2.app/Contents/SharedSupport/bin/subl",
        "/usr/local/bin/edit"
    };

    private static final String[] windowsEditors = new String[]{
        "C:\\Program Files\\Sublime Text 2\\sublime_text.exe",
        "C:\\Program Files (x86)\\Sublime Text 2\\sublime_text.exe",
        "C:\\Program Files\\Notepad++\\notepad++.exe",
        "C:\\Program Files (x86)\\Notepad++\\notepad++.exe",
        "notepad"
    };

    private String osName;
    private String editor;
    private String browser;
    private String classpath;

    private GradingEnvironment() {
        osName = System.getProperty("os.name");
        if (osName.equals("Mac OS X")) {
            osName = "Mac";
            browser = "open";
            editor = findEditor(macEditors);
            classpath = findClasspath(":");
        } else {
            osName = "Windows";
            browser = "explorer";
            editor = findEditor(windowsEditors);
            classpath = findClasspath(";");
        }
    }

    private static String findEditor(String[] editors) {
        for (String editor : editors) {
            if (new File(editor).exists())
                return editor;
        }
        return "";
    }

    private static String findClasspath(String separator) {
        File oe = new File("oeall-22.jar");
        String[] paths = new String[] { ".", "..", oe.getAbsolutePath()};
        String classpath = "";
        for (String path : paths)
            classpath += (classpath.isEmpty() ? "" : separator) + path;
        return classpath;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getEditor() {
        return editor;
    }

    public String getOsName() {
        return osName;
    }

    public void open(File file) {
        try {
            new ProcessBuilder(browser, file.getAbsolutePath()).start();
        } catch (IOException e) {
            System.out.println("Can't open file");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void edit(File file) {
        try {
            new ProcessBuilder(editor, file.getAbsolutePath()).start();
        } catch (IOException e) {
            System.out.println("Can't edit file/folder");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    // Singleton methods
    private static GradingEnvironment singleton = null;

    public static GradingEnvironment get() {
        if (singleton == null)
            singleton = new GradingEnvironment();
        return singleton;
    }

}
