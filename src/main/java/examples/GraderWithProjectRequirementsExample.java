package examples;

import framework.grading.ProjectRequirements;
import framework.wrappers.ProjectDatabaseWrapper;
import examples.checkers.testCases.ErrorTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;

/**
 * This demonstrates how you can use the ProjectRequirements object with the "grader" system
 */
public class GraderWithProjectRequirementsExample {

    public static void main(String[] args) {
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

        // You can still run it as normal
        database.nonBlockingRunProjectsInteractively();
    }
}
