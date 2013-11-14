package framework.wrappers;

import framework.grading.ProjectRequirements;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;
import grader.assignment.AGradingFeature;
import grader.assignment.AnAssignmenDataFolder;
import grader.assignment.GradingFeature;
import grader.file.RootFolderProxy;
import grader.sakai.*;
import grader.sakai.project.ASakaiProjectDatabase;
import grader.sakai.project.SakaiProject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * This extends the project database class to support adding ProjectRequirements
 */
public class ProjectDatabaseWrapper extends ASakaiProjectDatabase {

    private static final String GraderPath = "./GraderData/";

    private ProjectRequirements projectRequirements;
    private boolean projectsMade = false;
    private ASakaiStudentCodingAssignmentsDatabase studentAssignmentDatabase;

    /**
     * If you don't pass in any arguments, then it attempts to find/create the folders.
     * This requires that you register the download path in the GraderSettings singleton.
     */
    public ProjectDatabaseWrapper() {
        super(GraderSettings.get().get("path"), getDataFolder());

        // We want to go alphabetically, so set the NavigationListCreator
        setNavigationListCreator(new AlphabeticNavigationList());
    }

    public ProjectDatabaseWrapper(String aBulkAssignmentsFolderName, String anAssignmentsDataFolderName) {
        super(aBulkAssignmentsFolderName, anAssignmentsDataFolderName);
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
            gradingFeatures.add(new AGradingFeature(feature.getName(), feature.getPoints(), new FeatureCheckerWrapper(feature), feature.isExtraCredit()));
        }

        // Add the restrictions
        for (Restriction restriction : requirements.getRestrictions()) {
            gradingFeatures.add(new AGradingFeature(restriction.getName(), restriction.getPoints(), new FeatureCheckerWrapper(restriction)));
        }

        addGradingFeatures(gradingFeatures);
    }

    public ProjectRequirements getProjectRequirements() {
        return projectRequirements;
    }

    /**
     * This method is overridden to take the assignment name for the settings singleton rather than looking for an extra
     * folder nesting and pulling the name from the folder.
     */
    @Override
    public void maybeMakeProjects() {
        if (projectsMade)
            return;
        projectsMade = true;

        // Create the bulk folder
        BulkAssignmentFolder bulkFolder = new NonNestedBulkAssignmentFolder();
        setBulkFolder(bulkFolder);

        // Create the assignmentDataFolder
        AnAssignmenDataFolder assignmentDataFolder = new AnAssignmenDataFolder(getDataFolder() + "/" + GradingEnvironment.get().getAssignmentName(), bulkFolder.getSpreadsheet());
        setAssignmentDataFolder(assignmentDataFolder);

        // Create the collection of assignments
        GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();
        System.out.println(aStudentAssignmentDatabase.getStudentIds());
        Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase.getStudentAssignments();

        // Make the projects for the onyens that are specified
        for (StudentCodingAssignment anAssignment : studentAssignments) {
//            RootFolderProxy projectFolder = anAssignment.getProjectFolder();
            if (!assignmentDataFolder.getStudentIDs().contains(anAssignment.getOnyen()))
                continue;
            SakaiProject project = makeProject(anAssignment);
            if (project != null) {
                saveProject(anAssignment.getOnyen(), project);
            }
        }
    }

    @Override
    public GenericStudentAssignmentDatabase<StudentCodingAssignment> getStudentAssignmentDatabase() {
        if (studentAssignmentDatabase == null)
            studentAssignmentDatabase = new ASakaiStudentCodingAssignmentsDatabase(getBulkAssignmentFolder());
        return studentAssignmentDatabase;
    }

    @Override
    public StudentCodingAssignment getStudentAssignment(String anOnyen) {
        GenericStudentAssignmentDatabase<StudentCodingAssignment> aStudentAssignmentDatabase = getStudentAssignmentDatabase();
        Collection<StudentCodingAssignment> studentAssignments = aStudentAssignmentDatabase.getStudentAssignments();
        for (StudentCodingAssignment anAssignment : studentAssignments) {
            if (anAssignment.getOnyen().equals(anOnyen))
                return anAssignment;
        }
        return null;
    }

    /**
     * This attempts to find/make the data folder.
     * @return The path of the data folder
     */
    private static String getDataFolder() {

        // Make sure the appropriate folder exists
        File dataFolder = new File(GraderPath + GradingEnvironment.get().getAssignmentName());
        dataFolder.mkdirs();

        // Make sure the onyens.txt file exists
        String onyens = "";
        Boolean include = false;
        for (File file : new File(GraderSettings.get().get("path")).listFiles()) {
            if (file.isDirectory()) {
                String name = file.getName();
                String onyen = name.substring(name.indexOf("(")+1, name.indexOf(")"));
                if (onyen.equals(GraderSettings.get().get("start")))
                    include = true;
                if (include)
                    onyens += (onyens.isEmpty() ? "" : "\n") + onyen;
                if (onyen.equals(GraderSettings.get().get("end")))
                    include = false;
            }
        }
        try {
            FileUtils.writeStringToFile(new File(dataFolder, "onyens.txt"), onyens);
        } catch (IOException e) {
            System.out.println("Error creating onyens.txt file.");
        }

        // Make sure the log.txt file exits
        try {
            new File(dataFolder, "log.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating log.txt file.");
        }

        // Make sure that there is input
        File inputFolder = new File(dataFolder, "input");
        inputFolder.mkdir();
        try {
            FileUtils.writeStringToFile(new File(inputFolder, "Input1.txt"), "");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return dataFolder.getParentFile().getAbsolutePath();
    }



}
