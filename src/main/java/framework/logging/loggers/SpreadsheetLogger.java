package framework.logging.loggers;

import au.com.bytecode.opencsv.CSVReader;
import framework.grading.ProjectRequirements;
import framework.grading.testing.CheckResult;
import framework.grading.testing.Feature;
import framework.grading.testing.Restriction;
import framework.logging.recorder.RecordingSession;
import framework.utils.GraderSettings;
import framework.utils.GradingEnvironment;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * This maintains a spreadsheet of all students and their grades
 */
public class SpreadsheetLogger implements Logger {

    private static final String SakaiGradesSpreadsheetFilename = "grades.csv";

    private ProjectRequirements projectRequirements;
    private File spreadsheetFile;
    private XSSFWorkbook workbook;

    public SpreadsheetLogger(ProjectRequirements projectRequirements) throws ConfigurationException {
        this.projectRequirements = projectRequirements;

        // Get the spreadsheet file
        PropertiesConfiguration configuration = new PropertiesConfiguration("./config/config.properties");
        String spreadsheetPath = configuration.getString("grader.logger.spreadsheetFilename")
                .replace("{projectName}", GradingEnvironment.get().getAssignmentName());
        spreadsheetFile = new File(spreadsheetPath);

        // Load or create the workbook
        if (spreadsheetFile.exists())
            loadWorkbook();
        else
            createWorkbook();
    }

    @Override
    public void save(RecordingSession recordingSession) {

        // Get the row and save the feature and restriction results
        XSSFRow row = findRow(recordingSession);
        double rawScore = 0;
        int columnCounter = 7;
        for (CheckResult result : recordingSession.getFeatureResults()) {
            rawScore += result.getScore();
            row.getCell(columnCounter++).setCellValue(result.getScore());
        }
        for (CheckResult result : recordingSession.getRestrictionResults()) {
            rawScore += result.getScore();
            row.getCell(columnCounter++).setCellValue(result.getScore());
        }

        // Save the final scores
        row.getCell(4).setCellValue(rawScore);
        row.getCell(5).setCellValue(recordingSession.getLatePenalty());
        row.getCell(6).setCellValue(recordingSession.getLatePenalty() * rawScore);

        // Save the workbook to the file
        try {
            // Delete the old one just to avoid problems
            spreadsheetFile.delete();
            workbook.write(new FileOutputStream(spreadsheetFile));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private XSSFRow findRow(RecordingSession recordingSession) {
        String id = recordingSession.getUserId();
        String onyen = id.substring(id.indexOf("(") + 1, id.indexOf(")"));
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row.getCell(0).getStringCellValue().equals(onyen))
                return row;
        }
        return null;
    }

    private void loadWorkbook() {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(spreadsheetFile));
        } catch (Exception e) {
            System.out.println("Error creating spreadsheet. Creating new");
            spreadsheetFile.delete();
            createWorkbook();
        }
    }

    private void createWorkbook() {
        // Create workbook and add the header
        workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Display ID");
        row.createCell(1).setCellValue("ID");
        row.createCell(2).setCellValue("Last Name");
        row.createCell(3).setCellValue("First Name");
        row.createCell(4).setCellValue("Raw score");
        row.createCell(5).setCellValue("Early/Late modifier");
        row.createCell(6).setCellValue("Final score");
        int columnCounter = 7;
        for (Feature feature : projectRequirements.getFeatures())
            row.createCell(columnCounter++).setCellValue(feature.getName());
        for (Restriction restriction : projectRequirements.getRestrictions())
            row.createCell(columnCounter++).setCellValue(restriction.getName());

        // Load the Sakai grades file to get the list of students
        try {
            String csvFile = GraderSettings.get().get("path") + "/" + SakaiGradesSpreadsheetFilename;
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            reader.readNext();
            reader.readNext();
            reader.readNext();
            String[] line;
            int rowCounter = 1;
            while ((line = reader.readNext()) != null) {
                row = sheet.createRow(rowCounter++);
                row.createCell(0).setCellValue(line[0]);
                row.createCell(1).setCellValue(line[1]);
                row.createCell(2).setCellValue(line[2]);
                row.createCell(3).setCellValue(line[3]);
                row.createCell(4).setCellValue(0);
                row.createCell(5).setCellValue(0);
                row.createCell(6).setCellValue(0);
                for (int i = 7; i < columnCounter; i++)
                    row.createCell(i).setCellValue(0);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
