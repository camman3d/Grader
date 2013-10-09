package grader.assignment;

import grader.sakai.project.ASakaiProjectDatabase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestSakaiProjectDatabase {

    private static final String BulkFolder = "/tmp/a6";
    private static final String DataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

    private ASakaiProjectDatabase database;

    @Before
    public void setUp() throws Exception {

        database = new ASakaiProjectDatabase(BulkFolder, DataFolder);

//        GoodFauxProjectDatabase db = new GoodFauxProjectDatabase(BulkFolder);
//        db.addGradingFeature(new AGradingFeature("Feature 1", 20, new GoodFauxFeatureChecker()));
//        db.addGradingFeature(new AGradingFeature("Feature 2", 15, new GoodFauxFeatureChecker()));
//        db.addGradingFeature(new AGradingFeature("Feature 3", 10, new GoodFauxFeatureChecker()));
//        db.addGradingFeature(new AGradingFeature("Feature 4",  5, new GoodFauxFeatureChecker()));
//        database = db;
    }

    @Test
    public void testCheck() throws Exception {
        database.runProjectInteractively("wabbey");
        assertTrue(true);
    }
}
