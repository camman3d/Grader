package framework.wrappers;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.utils.GraderSettings;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * This extends the project database class to support adding ProjectRequirements
 */
public class ProjectDatabaseWrapper extends ASakaiProjectDatabase {

    private ProjectRequirements projectRequirements;

    /**
     * If you don't pass in any arguments, then it attempts to find/create the folders.
     * This requires that you register the download path in the GraderSettings singleton.
     */
    public ProjectDatabaseWrapper() {
        super(getAssignmentFolder(), getDataFolder());
    }

    public ProjectDatabaseWrapper(String aBulkAssignmentsFolderName, String anAssignmentsDataFolderName) {
        super(aBulkAssignmentsFolderName, anAssignmentsDataFolderName);
    }

    /**
     * @return The download path registered in the GraderSettings singleton.
     */
    private static String getAssignmentFolder() {
        return GraderSettings.get().get("path");
    }

    /**
     * This attempts to find/make the data folder.
     * @return The path of the data folder
     */
    private static String getDataFolder() {

        // Make sure the appropriate folder exists
        File path = new File(getAssignmentFolder());
        String name = path.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && !pathname.getName().equals("__MACOSX");
            }
        })[0].getName();
        File dataFolder = new File("./GraderData/" + name);
        dataFolder.mkdirs();

        // TODO: Make sure the onyens file exists
        // TODO: Make sure the log.txt file exits

        return dataFolder.getParentFile().getAbsolutePath();
    }

    /**
     * This generates grading features based on the project requirements
     * @param requirements The ProjectRequirements to add to the project database
     */
    public void addProjectRequirements(ProjectRequirements requirements) {
        projectRequirements = requirements;
        List<GradingFeature> gradingFeatures = new ArrayList<GradingFeature>();

        // Add the features
        for (Feature feature : requirements.getFeatures()) {
            gradingFeatures.add(new GradingFeatureWrapper(feature.getName(), feature.getPoints(), new FeatureCheckerWrapper(feature), feature.isExtraCredit()));
        }

        // Add the restrictions
        for (Restriction restriction : requirements.getRestrictions()) {
            gradingFeatures.add(new GradingFeatureWrapper(restriction.getName(), restriction.getPoints(), new FeatureCheckerWrapper(restriction)));
        }

        addGradingFeatures(gradingFeatures);
    }

    public ProjectRequirements getProjectRequirements() {
        return projectRequirements;
    }
}
