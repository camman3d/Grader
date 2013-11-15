package tools;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/14/13
 * Time: 9:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestConfig {

    private static PropertiesConfiguration config;

    private static void openConfig() {
        try {
            config = new PropertiesConfiguration("./config/config.properties");
        } catch (ConfigurationException e) {
            System.out.println("Error loading config file");
            e.printStackTrace();
        }
    }

    public static PropertiesConfiguration getConfig() {
        if (config == null)
            openConfig();
        return config;
    }
}
