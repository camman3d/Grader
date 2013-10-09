package grader.sakai.project;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectStepperFactory {
    public static ProjectStepper create() {

        // Load the class from the properties file
        try {
            Configuration config = new PropertiesConfiguration("config/config.properties");
            String stepperClassName = config.getString("stepper");
            Class<ProjectStepper> stepperClass = (Class<ProjectStepper>) ClassLoader.getSystemClassLoader().loadClass(stepperClassName);
            return stepperClass.newInstance();

        } catch (Exception e) {

            // If it fails, use the standard one
            System.out.println("Unable to create project stepper... Using default.");
            return new AProjectStepper();
        }
    }
}
