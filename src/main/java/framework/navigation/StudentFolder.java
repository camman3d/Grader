package framework.navigation;

import org.joda.time.DateTime;
import framework.project.Project;
import scala.Option;

import java.io.File;

/**
 * Like StudentAssignment
 */
public interface StudentFolder<T extends Project> {

    /**
     * @return A JODA {@link org.joda.time.DateTime} representing the submission time in an {@link scala.Option} if it doesn't exit
     */
    public Option<DateTime> getSubmissionTime();

    /**
     * @return The folder where feedback files will be stored
     */
    public File getFeedbackFolder();

    /**
     * @return The student's ID. For Sakai, it looks like this: "Last Name, First Name(Onyen)"
     */
    public String getUserId();

    /**
     * @return The student's Onyen
     */
    public String getOnyen();

    /**
     * @return The student's project wrapped in a {@link Project} and a {@link scala.Option} if it doesn't exist
     */
    public Option<T> getProject(String name);
}
