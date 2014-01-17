package framework.utils;

import java.io.File;
import java.io.IOException;

/**
 * A singleton that investigates the machine for certain things. It looks for:
 * <ul>
 *     <li>Operating System</li>
 *     <li>File Browser (OS specific)</li>
 *     <li>Classpath (OS specific because Windows delimits with ';' rather than ':'</li>
 * </ul>
 */
public class GradingEnvironment {

    private String osName;
    private String browser;
    private String classpath;

    private GradingEnvironment() {
        osName = System.getProperty("os.name");
        if (osName.equals("Mac OS X")) {
            osName = "Mac";
            browser = "open";
            classpath = findClasspath(":");
        } else {
            osName = "Windows";
            browser = "explorer";
            classpath = findClasspath(";");
        }
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

    public String getOsName() {
        return osName;
    }

    public String getBrowser() {
        return browser;
    }

    // Singleton methods
    private static GradingEnvironment singleton = null;

    public static GradingEnvironment get() {
        if (singleton == null)
            singleton = new GradingEnvironment();
        return singleton;
    }

}
