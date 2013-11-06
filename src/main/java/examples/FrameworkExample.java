package examples;

import framework.grading.GradingManager;
import framework.grading.ProjectRequirements;
import examples.checkers.testCases.ErrorTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;

import java.io.IOException;

/**
 * This demonstrates using the "framework" grading system
 */
public class FrameworkExample {
    public static void main(String[] args) throws IOException {

        // Project grading criteria are saved in a ProjectRequirements object
        ProjectRequirements requirements = new ProjectRequirements() {{
            addFeature("Test feature 1", 20, new PassingTestCase());
            addFeature("Test feature 2", 15, new FailingTestCase());
            addFeature("Test feature 3", 10, new ErrorTestCase());
        }};

        // The GraderManager is responsible for running the grading process
        GradingManager manager = new GradingManager("Example Assignment", requirements);
        manager.run();

    }
}
