package framework.utils;

import framework.grading.ProjectRequirements;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import scala.Option;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A grading manifest contains settings that pertain to a certain project, rather than on a global level.
 * Manifest files should be under the "manifests" folder.
 */
public class GradingManifest {

    private String projectName;

    private Option<? extends ProjectRequirements> projectRequirements;
    private Option<String> downloadPath;
    private Option<List<String>> onyens;

    private PropertiesConfiguration configuration;

//    public GradingManifest(String projectName) {
//        this(projectName, new File("config/" + projectName + ".properties"));
//    }

    public GradingManifest() {



//        this.projectName = projectName;
        File manifestFile = new File("config/grading.properties");
        try {
            if (manifestFile.exists()) {
                configuration = new PropertiesConfiguration(manifestFile);

                // Load stuff from the configuration file

                // Load the project name
                projectName = configuration.getString("project.name", "Untitled project");

                // Load the download path
                if (configuration.containsKey("downloadPath"))
                    downloadPath = Option.apply(configuration.getString("downloadPath"));
                else
                    downloadPath = Option.empty();

                // Load the list of onyens
//                if (configuration.containsKey("onyens")) {
//                    List<String> onyenList = new ArrayList<String>();
//                    for (Object obj : configuration.getList("onyens", new ArrayList<Object>()))
//                        onyenList.add((String) obj);
//                    onyens = Option.apply(onyenList);
//                } else
//                    onyens = Option.empty();

                // Load the project requirements
                if (configuration.containsKey("project.requirements")) {
                    Class<?> _class = Class.forName(configuration.getString("project.requirements"));
                    projectRequirements = Option.apply((ProjectRequirements) _class.newInstance());
                } else
                    projectRequirements = Option.empty();


            } else {
                manifestFile.createNewFile();
                configuration = new PropertiesConfiguration(manifestFile);
                projectName = "Untitled project";
                downloadPath = Option.empty();
                onyens = Option.empty();
                projectRequirements = Option.empty();
            }
        } catch (ConfigurationException e) {
            System.err.println("GradingManifest: Error loading manifest " + manifestFile.getPath());
        } catch (ClassNotFoundException e) {
            System.err.println("GradingManifest: ProjectRequirements class not found");
        } catch (InstantiationException e) {
            System.err.println("GradingManifest: Cannot instantiate ProjectRequirements class");
        } catch (IllegalAccessException e) {
            System.err.println("GradingManifest: IllegalAccessException");
        } catch (IOException e) {
            System.err.println("GradingManifest: Error creating file");
        }
    }

    public boolean hasDownloadPath() {
        return downloadPath.isDefined();
    }

    public boolean hasOnyens() {
        return onyens.isDefined();
    }

    public boolean hasProjectRequirements() {
        return projectRequirements.isDefined();
    }

    public boolean isComplete() {
        return hasDownloadPath() && hasOnyens() && hasProjectRequirements();
    }

    public void setOnyens(List<String> onyens) {
        this.onyens = Option.apply(onyens);
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = Option.apply(downloadPath);
    }

    public void setProjectRequirements(ProjectRequirements projectRequirements) {
        this.projectRequirements = Option.apply(projectRequirements);
    }

    public String getDownloadPath() {
        return downloadPath.get();
    }

    public List<String> getOnyens() {
        return onyens.get();
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectRequirements getProjectRequirements() {
        return projectRequirements.get();
    }

    public GradingManifest save() {
        try {
            if (downloadPath.isDefined()) {
                if (configuration.containsKey("downloadPath"))
                    configuration.clearProperty("downloadPath");
                configuration.addProperty("downloadPath", downloadPath.get());
            }

            configuration.clearProperty("project.name");
            configuration.addProperty("project.name", projectName);

            if (projectRequirements.isDefined()) {
                if (configuration.containsKey("project.requirements"))
                    configuration.clearProperty("project.requirements");
                configuration.addProperty("project.requirements", projectRequirements.get().getClass().getCanonicalName());
            }

            configuration.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return this;
    }

    public GradingManifest setActive() {
        activeManifest = this;
        return this;
    }

    // Static methods

    private static GradingManifest activeManifest;

    public static GradingManifest getActiveManifest() {
        return activeManifest;
    }

//    public static void setActiveManifest(GradingManifest manifest) {
//        activeManifest = manifest;
//    }
}
