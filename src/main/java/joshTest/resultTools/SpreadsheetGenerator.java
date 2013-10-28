package joshTest.resultTools;

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
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/25/13
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpreadsheetGenerator {

    public static void main(String[] args) throws IOException {
        String logPath = "./log/Assignment7";
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
            Double score = computeScore(file);

            // Save to the spreadsheet
            XSSFRow row = sheet.createRow(index++);
            XSSFCell nameCell = row.createCell(0);
            nameCell.setCellValue(name);
            XSSFCell gradeCell = row.createCell(1);
            gradeCell.setCellValue(score);
        }

        workbook.write(new FileOutputStream(gradeFile));
        System.out.println("Done writing grade file.");
    }

    private static double computeScore(File jsonFile) {
        try {
            String contents = FileUtils.readFileToString(jsonFile);
            return ResultHolder.fromJson(contents).totalScore();
        } catch (IOException e) {
            System.out.println("Unable to compute score on: " + jsonFile.getName());
            return 0;
        }
    }
}
