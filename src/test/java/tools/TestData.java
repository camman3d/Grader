package tools;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/11/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestData {

    // Define the static singleton methods and shortcuts here

    private static TestData ourInstance = new TestData();

    public static TestData get() {
        return ourInstance;
    }

    public static File getResource(String path) {
        return new File(ourInstance.dataFolder, path);
    }

    // Define the actual object here

    private PropertiesConfiguration config;
    private File dataFolder;

    private TestData() {
        try {
            // Load the configuration file
            config = new PropertiesConfiguration("./config/config.properties");

            // Make sure the test data folder exists
            dataFolder = new File(config.getString("test.folder"));
//            if (!dataFolder.exists())
//                dataFolder.createNewFile();

        } catch (ConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
