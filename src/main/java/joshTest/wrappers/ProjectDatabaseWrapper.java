package joshTest.wrappers;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.utils.GraderSettings;
import grader.assignment.AGradingFeature;
import grader.assignment.GradingFeature;
import grader.sakai.project.ASakaiProjectDatabase;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/23/13
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDatabaseWrapper extends ASakaiProjectDatabase {

    public ProjectDatabaseWrapper() {
        super(getAssignmentFolder(), getDataFolder());
    }

    private static String getAssignmentFolder() {
        return GraderSettings.get().get("path");
    }

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

    public void addProjectRequirements(ProjectRequirements requirements) {
        List<GradingFeature> gradingFeatures = new ArrayList<GradingFeature>();

        // Add the features
        for (Feature feature : requirements.getFeatures()) {
            gradingFeatures.add(new AGradingFeature(feature.getName(), feature.getPoints(), new FeatureCheckerWrapper(feature), feature.isExtraCredit()));
        }

        // Add the restrictions
        for (Restriction restriction : requirements.getRestrictions()) {
            gradingFeatures.add(new AGradingFeature(restriction.getName(), restriction.getPoints(), new FeatureCheckerWrapper(restriction)));
        }

        addGradingFeatures(gradingFeatures);
    }
}
