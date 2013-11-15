package framework.grading;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/14/13
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDueDate {

    private DateTime time = new DateTime();
    private double percentage = 0.4;

    @Test
    public void testConstructor() throws Exception {
        DueDate dueDate = new DueDate(time, percentage);

        Assert.assertEquals(time, getCutoffDate(dueDate));
        Assert.assertTrue(percentage == getPercentage(dueDate));
    }

    @Test
    public void testSetPercentage() throws Exception {
        DueDate dueDate = new DueDate(time, percentage);
        dueDate.setPercentage(0.95);
        Assert.assertTrue(getPercentage(dueDate) == 0.95);
    }

    @Test
    public void testGetPercentage() throws Exception {
        DueDate dueDate = new DueDate(time, percentage);
        Assert.assertTrue(dueDate.getPercentage() == percentage);
    }

    @Test
    public void testSetCutoffDate() throws Exception {
        DueDate dueDate = new DueDate(time, percentage);
        DateTime newTime = time.minusHours(5);
        dueDate.setCutoffDate(newTime);
        Assert.assertEquals(newTime, getCutoffDate(dueDate));
        Assert.assertNotEquals(time, getCutoffDate(dueDate));
    }

    @Test
    public void testGetCutoffDate() throws Exception {
        DueDate dueDate = new DueDate(time, percentage);
        Assert.assertEquals(dueDate.getCutoffDate(), time);
    }

    private DateTime getCutoffDate(DueDate dueDate) throws NoSuchFieldException, IllegalAccessException {
        Field field = dueDate.getClass().getDeclaredField("cutoffDate");
        field.setAccessible(true);
        return (DateTime) field.get(dueDate);
    }

    private double getPercentage(DueDate dueDate) throws NoSuchFieldException, IllegalAccessException {
        Field field = dueDate.getClass().getDeclaredField("percentage");
        field.setAccessible(true);
        return field.getDouble(dueDate);
    }
}
