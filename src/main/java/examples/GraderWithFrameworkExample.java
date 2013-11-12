package examples;

import examples.checkers.testCases.ErrorTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;
import framework.grading.ProjectRequirements;
import framework.utils.GradingEnvironment;
import framework.wrappers.ProjectDatabaseWrapper;
import framework.wrappers.ProjectStepperDisplayerWrapper;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 8:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class GraderWithFrameworkExample {
    public static void main(String[] args) {

        GradingEnvironment.get().setAssignmentName("Assignment 7");
        String bulkFolder = "/Users/josh/Downloads/a7";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        // Create a project database, which will contain the project grading criteria
        ProjectDatabaseWrapper database = new ProjectDatabaseWrapper(bulkFolder, dataFolder);

        // Create the project requirements and add them to the database. Nothing special is needed
        ProjectRequirements requirements = new ProjectRequirements() {{
            addFeature("Test feature 1", 20, new PassingTestCase());
            addFeature("Test feature 2", 15, new FailingTestCase());
            addFeature("Test feature 3", 10, new ErrorTestCase());
        }};
        database.addProjectRequirements(requirements);

        database.setProjectStepperDisplayer(new ProjectStepperDisplayerWrapper());
        database.nonBlockingRunProjectsInteractively();

    }
}
