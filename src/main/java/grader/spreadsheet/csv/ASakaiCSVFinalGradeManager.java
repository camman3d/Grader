package grader.spreadsheet.csv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import grader.file.FileProxy;
import grader.sakai.project.SakaiProjectDatabase;
import grader.spreadsheet.FinalGradeRecorder;

public class ASakaiCSVFinalGradeManager implements FinalGradeRecorder {
	public static final int ONYEN_COLUMN = 0;
	public static final int GRADE_COLUMN = 4;
	public static final int TITLE_ROW = 2;
//	InputStream input; // this may have to be reinitialized each time
//	OutputStream output; // may have to reinitialized and closed each time
	FileProxy gradeSpreadsheet;
	List<String[]>  table;
  

	public ASakaiCSVFinalGradeManager(FileProxy aGradeSpreadsheet) {
		gradeSpreadsheet = aGradeSpreadsheet;		
	}
	
	public ASakaiCSVFinalGradeManager(SakaiProjectDatabase aSakaiProjectDatabase) {
		gradeSpreadsheet = aSakaiProjectDatabase.getBulkAssignmentFolder().getSpreadsheet();		
	}
	
	protected void maybeCreateTable() {
		if (table != null)
			return;
		createTable();
		
	}
	
	protected void createTable() {
		
		try {
			InputStream input = gradeSpreadsheet.getInputStream();
			CSVReader csvReader 	=	new CSVReader(new InputStreamReader(input));
		     table = csvReader.readAll();
			csvReader.close();
			input.close();
			
		   
	    
	    
		} catch (Exception e) {
			e.printStackTrace();
		
			
		}
		
	}
	
	public String[] getStudentRow(List<String[]> aSheet, String aStudentName, String anOnyen) {
		 for (int rowNum = 0; rowNum < aSheet.size(); rowNum ++) {
			 String[] aRow = aSheet.get(rowNum);
			 if (aRow[ONYEN_COLUMN].equals(anOnyen))
				 return aRow;
		 }
		 return null;
		
	}
	
	public void recordGrade (String[] aRow, double aScore) {
		recordGrade(aRow, GRADE_COLUMN, aScore);

	}
	
	public void recordGrade (String[] aRow, int aColumn, double aScore) {
		String aGradeCell = aRow[aColumn];
		aRow[aColumn] = Double.toString(aScore);
		
	}
	
	public double getGrade (String[] aRow, int aColumn) {
		try {
		String aGradeCell = aRow[aColumn];
		
		return Double.parseDouble(aGradeCell);
		} catch (Exception e) {
//			e.printStackTrace();
			return 0;
		}
	}
	
	
	public double getGrade (String[] aRow) {
		return getGrade(aRow, GRADE_COLUMN);

	}
	
	
	public double getGrade(String aStudentName, String anOnyen) {
		try {
//		InputStream input = gradeSpreadsheet.getInputStream();
//		CSVReader csvReader 	=	new CSVReader(new InputStreamReader(input));
//		List<String[]>  table = csvReader.readAll();
//		csvReader.close();
			maybeCreateTable();
		
	   
    String[] row = getStudentRow(table, aStudentName, anOnyen);
    if (row == null) {
		System.out.println("Cannot find row for:" + aStudentName + " " + anOnyen);
		return -1;
    }
   double retVal =  getGrade(row);

//
//    input.close();
    return retVal;
    
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
		
	}
	
		
	}
	
	void writeTable() {
		OutputStream output = gradeSpreadsheet.getOutputStream();
		if (output == null) {
			System.out.println("Cannot write grade as null output stream");
			return;
		}
		CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(output));
		csvWriter.writeAll(table);
		try {
			csvWriter.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}
	@Override
	public void setGrade(String aStudentName, String anOnyen, double aScore) {
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
		    
		    recordGrade(row, aScore);
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
	
	

	

}
