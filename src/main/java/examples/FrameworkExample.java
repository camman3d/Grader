package examples;

import framework.grading.AGUIGradingManager;
import framework.grading.FrameworkProjectRequirements;
import examples.checkers.testCases.ErrorTestCase;
import examples.checkers.testCases.FailingTestCase;
import examples.checkers.testCases.PassingTestCase;
import framework.grading.GradingManager;

import java.io.IOException;

/**
 * This demonstrates using the "framework" grading system
 */
public class FrameworkExample {
    public static void main(String[] args) throws IOException {

        // Project grading criteria are saved in a FrameworkProjectRequirements object
        FrameworkProjectRequirements requirements = new FrameworkProjectRequirements() {{
            addFeature("Test feature 1", 20, new PassingTestCase());
            addFeature("Test feature 2", 15, new FailingTestCase());
            addFeature("Test feature 3", 10, new ErrorTestCase());
        }};

        // The GraderManager is responsible for running the grading process
        GradingManager manager = new AGUIGradingManager("Example Assignment", requirements);
        manager.run();

    }
}
