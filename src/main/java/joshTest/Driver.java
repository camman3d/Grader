package joshTest;

import grader.sakai.project.ASakaiProjectDatabase;
import org.junit.Before;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/9/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {

    public static void main(String[] args) {
        String bulkFolder = "/tmp/a6";
        String dataFolder = "/Users/josh/Documents/School/Fall 2013/COMP401/Grader2/GraderData";

        ASakaiProjectDatabase database = new ASakaiProjectDatabase(bulkFolder, dataFolder);

        database.runProjectsInteractively();
    }
}
