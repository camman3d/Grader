package gradingTools.assignment1;

import framework.execution.NotRunnableException;
import framework.execution.RunningProject;
import framework.project.Project;

/**
 * This tries to run the program with a space at the end of each line and adds the terminating character '.'
 */
public class FlexibleProgramRunner {

    private static final int Timeout = 1;

    private Project project;
    private String input;
    private String spacedInput = "";

    public FlexibleProgramRunner(Project project, String input) {
        this(project, input, true);
    }

    public FlexibleProgramRunner(Project project, String input, boolean includePeriod) {
        this.project = project;
        this.input = input;

        String[] lines = input.split("\n");
        for (String line : lines)
            spacedInput += line + " \n";

        if (includePeriod) {
            input += ".\n";
            spacedInput += ".\n";
        }
    }

    public String runWithSpaces() throws NotRunnableException {
        RunningProject runningProject = project.launch(spacedInput, Timeout);
        return runningProject.await();
    }

    public String runNoSpaces() throws NotRunnableException {
        RunningProject runningProject = project.launch(input, Timeout);
        return runningProject.await();
    }

}
