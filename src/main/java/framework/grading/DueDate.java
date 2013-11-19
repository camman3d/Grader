package framework.grading;

import org.joda.time.DateTime;

/**
 * A representation of a due due. It contains the date and the percentage that is awarded if turned in by that date.
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
