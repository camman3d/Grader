package grader.spreadsheet.csv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import grader.assignment.AssignmentDataFolder;
import grader.assignment.GradingFeature;
import grader.file.FileProxy;
import grader.sakai.project.SakaiProject;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FeatureGradeRecorder;
import grader.spreadsheet.FinalGradeRecorder;

public class ASakaiCSVFeatureGradeManager extends ASakaiCSVFinalGradeManager implements FeatureGradeRecorder {
//	SakaiProjectDatabase projectDatabase;
//	public static final int ONYEN_COLUMN = 0;
//	public static final int GRADE_COLUMN = 4;
//	InputStream input; // this may have to be reinitialized each time
//	OutputStream output; // may have to reinitialized and closed each time
//	FileProxy gradeSpreadsheet;
//	List<String[]>  table;
	 List<GradingFeature> gradingFeatures;
	 Map<String, Integer> featureToColumnNumber = new HashMap();

	public ASakaiCSVFeatureGradeManager(FileProxy aGradeSpreadsheet, List<GradingFeature> aGradingFeatures) {
		super(aGradeSpreadsheet);
		gradingFeatures = aGradingFeatures;
		
//		gradeSpreadsheet = aGradeSpreadsheet;		
	}
	
	public ASakaiCSVFeatureGradeManager(SakaiProjectDatabase aSakaiProjectDatabase) {
		super(aSakaiProjectDatabase.getAssigmentDataFolder().getFeatureGradeFile());
		gradingFeatures = aSakaiProjectDatabase.getGradingFeatures();
		
//		gradeSpreadsheet = aGradeSpreadsheet;		
	}
	
	protected void createTable() {
		super.createTable();
		if (gradingFeatures == null) return;
		String[] headers = table.get(TITLE_ROW);
		if (headers.length < GRADE_COLUMN + gradingFeatures.size() + 1) {
			
		extendTable();
		makeTitles();
		writeTable();
		}
		makeMap();
		
	}
	
	void makeTitles() {
		String[] titleRow = table.get(TITLE_ROW);
		for (int i = 0; i < gradingFeatures.size(); i++) {
			int featureColumn = GRADE_COLUMN + 1 + i;
//			featureToColumnNumber.put(gradingFeatures.get(i).getFeature(), featureColumn);
			titleRow[featureColumn] = gradingFeatures.get(i).getFeature();
			
		}
	}
	
	void makeMap() {
		for (int i = 0; i < gradingFeatures.size(); i++) {
			int featureColumn = GRADE_COLUMN + 1 + i;
			featureToColumnNumber.put(gradingFeatures.get(i).getFeature(), featureColumn);
			
		}
	}
	
	void extendTable() {
		for (int i = 0; i < table.size(); i++) {
			table.set (i, extendedRow(table.get(i)));
		}
	}

	String[] extendedRow(String[] anExistinRow) {
		String[] retVal = new String[anExistinRow.length + gradingFeatures.size()];
		for (int index = 0; index < anExistinRow.length; index++) {
			retVal[index] = anExistinRow[index];
		}
		for (int index = anExistinRow.length; index < retVal.length; index++) {
			retVal[index] = "0";
		}
		return retVal;
		
	}
	public double getGrade (String[] aRow, String aFeatureName) {
		return getGrade(aRow, featureToColumnNumber.get(aFeatureName));

	}
	
	public void recordGrade (String[] aRow, String aFeature, double aScore) {
		recordGrade(aRow, featureToColumnNumber.get(aFeature), aScore);

	}

	@Override
	public void setGrade(String aStudentName, String anOnyen, String aFeature,
			double aScore) {
		try {
//			InputStream input = gradeSpreadsheet.getInputStream();
//			CSVReader csvReader 	=	new CSVReader(new InputStreamReader(input));
//			List<String[]>  table = csvReader.readAll();
//			csvReader.close();
			maybeCreateTable();
			
		    String[] row = getStudentRow(table, aStudentName, anOnyen);
		    if (row == null) {
				System.out.println("Cannot find row for:" + aStudentName + " " + anOnyen);
				return;
		    }
		    
		    recordGrade(row, aFeature, aScore);
		    writeTable();


//		OutputStream output = gradeSpreadsheet.getOutputStream();
//		if (output == null) {
//			System.out.println("Cannot write grade as null output stream");
//			return;
//		}
//		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(output));
//		csvWriter.writeAll(table);
//		csvWriter.close();
	    
    

//
//    Row row = sheet.getRow(2);
//    Cell cell1 = row.getCell(4);
//    if (cell1 == null)
//    	cell1 = row.createCell(5);
//    double doubleCell = cell1.getNumericCellValue();
//    int i = 0;
//    cell1.setCellValue (doubleCell + 2);
//    Cell cell2 = row.getCell(0);
//    if (cell2 != null) {
//    	String stringCell = cell2.getStringCellValue();
//    	System.out.println(stringCell);
//    }

    /*
    if (cell == null)
        cell = row.createCell(3);
    cell.setCellType(Cell.CELL_TYPE_STRING);
    cell.setCellValue("a test");
    */
    /*
    // Write the output to a file
    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
    wb.write(fileOut);
    fileOut.close();
    */
	} catch (Exception e) {
		e.printStackTrace();
		
	}
		
	}

	@Override
	public double getGrade(String aStudentName, String anOnyen, String aFeature) {
		try {
//			InputStream input = gradeSpreadsheet.getInputStream();
//			CSVReader csvReader 	=	new CSVReader(new InputStreamReader(input));
//			List<String[]>  table = csvReader.readAll();
//			csvReader.close();
				maybeCreateTable();
			
		   
	    String[] row = getStudentRow(table, aStudentName, anOnyen);
	    if (row == null) {
			System.out.println("Cannot find row for:" + aStudentName + " " + anOnyen);
			return -1;
	    }
	   double retVal =  getGrade(row, aFeature);

	//
//	    input.close();
	    return retVal;
	    
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
			
		}
		
	}
	
	

	

}
