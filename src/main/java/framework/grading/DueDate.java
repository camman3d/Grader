package framework.grading;

import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/28/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class DueDate {

    private DateTime cutoffDate;
    private double percentage;

    public DueDate(DateTime cutoffDate, double percentage) {
        this.cutoffDate = cutoffDate;
        this.percentage = percentage;
    }

    public DateTime getCutoffDate() {
        return cutoffDate;
    }

    public void setCutoffDate(DateTime cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
