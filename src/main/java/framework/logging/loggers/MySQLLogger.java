package framework.logging.loggers;

import framework.grading.testing.CheckResult;
import framework.logging.recorder.RecordingSession;
import framework.utils.GradingManifest;
import framework.utils.GradingSettings;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.*;
import java.util.*;

/**
 * This logs each CheckResult to a MySQL database
 */
public class MySQLLogger implements Logger {

    private Connection connection;
    private static final String TableName = "gradingrecords";
    private static final String InsertStatement = "insert into " + TableName + " values(default, ?, ?, ?, ?, ?, ?, ?, ?)";

    public MySQLLogger() {
        try {
            // Load the driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish a connection based on the configuration
            PropertiesConfiguration configuration = new PropertiesConfiguration("./config/config.properties");
            String host = configuration.getString("grader.logger.mysql.hostname");
            String schema = configuration.getString("grader.logger.mysql.schema");
            String user = configuration.getString("grader.logger.mysql.username");
            String password = configuration.getString("grader.logger.mysql.password");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + schema, user, password);

        } catch (ClassNotFoundException e) {
            System.err.println("MySQLLogger: Cannot load MySQL driver.");
            connection = null;
        } catch (SQLException e) {
            System.err.println("MySQLLogger: MySQL error: " + e.getMessage() + ".");
            connection = null;
        } catch (ConfigurationException e) {
            System.err.println("MySQLLogger: Unable to load configuration.");
            connection = null;
        }
    }

    @Override
    public void save(RecordingSession recordingSession) {
        if (connection == null)
            return;

        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

        // Record each feature
        for (CheckResult checkResult : recordingSession.getFeatureResults()) {
            try {
                prepareStatement(checkResult, recordingSession, timestamp).executeUpdate();
            } catch (SQLException e) {
                System.err.println("MySQLLogger: Error writing feature result to DB.");
            }
        }

        // Record each restriction
        for (CheckResult checkResult : recordingSession.getRestrictionResults()) {
            try {
                prepareStatement(checkResult, recordingSession, timestamp).executeUpdate();
            } catch (SQLException e) {
                System.err.println("MySQLLogger: Error writing restriction result to DB.");
            }
        }
    }

    private PreparedStatement prepareStatement(CheckResult checkResult, RecordingSession recordingSession, Timestamp timestamp) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(InsertStatement);
        preparedStatement.setString(1, GradingSettings.get().getUsername());
        preparedStatement.setString(2, GradingManifest.getActiveManifest().getProjectName());
        preparedStatement.setString(3, recordingSession.getUserId());
        preparedStatement.setTimestamp(4, timestamp);
        preparedStatement.setString(5, checkResult.getTarget().getName());
        preparedStatement.setDouble(6, checkResult.getScore());
        preparedStatement.setString(7, checkResult.getNotes());
        preparedStatement.setString(8, checkResult.getSummary());
        return preparedStatement;
    }
}
