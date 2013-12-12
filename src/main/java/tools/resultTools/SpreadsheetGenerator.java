package tools.resultTools;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This looks at all the .json files in the folder designated by "logPath" and creates a spreadsheet based on those
 * scores.
 */
public class SpreadsheetGenerator {

    public static void main(String[] args) throws IOException {

        // Get the log files
        String logPath = "./log/Assignment11";
        File logFolder = new File(logPath);
        File[] files = logFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".json");
            }
        });

        File gradeFile = new File(logFolder, "grades.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Grades");

        int index = 0;
        for (File file : files) {
            String name = file.getName().replace(".json", "");
            ResultHolder result = computeScore(file);

            // Save to the spreadsheet
            XSSFRow row = sheet.createRow(index++);
            XSSFCell nameCell = row.createCell(0);
            nameCell.setCellValue(name);
            XSSFCell gradeCell = row.createCell(1);
            gradeCell.setCellValue(result.totalScore());
            XSSFCell adjustmentCell = row.createCell(2);
            adjustmentCell.setCellValue(result.getLatePenalty());
        }

        workbook.write(new FileOutputStream(gradeFile));
        System.out.println("Done writing grade file.");
    }

    private static ResultHolder computeScore(File jsonFile) {
        try {
            String contents = FileUtils.readFileToString(jsonFile);
            return ResultHolder.fromJson(contents);
        } catch (IOException e) {
            System.out.println("Unable to compute score on: " + jsonFile.getName());
            return new ResultHolder();
        }
    }
}
