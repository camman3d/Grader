package grader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelTest {	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		//createSpreadsheet(true);
		//readSpreadsheet(true);
//		readExperiment();
		readAndWrite();
		
	}
//	public static void createSpreadsheet(boolean isXSSF) {
//		// TODO Auto-generated method stub
//		try {
//			
//			//Workbook w;
//			
//		Workbook wb; 
//		FileOutputStream fileOut;
//		if (isXSSF) {
//		wb = new XSSFWorkbook();
//	    fileOut = new FileOutputStream("workbook.xlsx");
//		} else {
//			wb = new HSSFWorkbook();
//		    fileOut = new FileOutputStream("workbook.xls");
//			
//		}
//	    Sheet sheet1 = wb.createSheet("new sheet");
//	    //Sheet sheet2 = wb.createSheet("second sheet");
//	    CreationHelper createHelper = wb.getCreationHelper();
//	    Row row = sheet1.createRow((short)0);
//	    Cell cell1 = row.createCell(0);
//	    cell1.setCellValue(1);
//	    Cell cell2 = row.createCell(1);
//	    row.createCell(2).setCellValue(
//	            createHelper.createRichTextString("This is a string"));
//	       row.createCell(3).setCellValue(true);
//
//
//	    wb.write(fileOut);
//	    fileOut.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public static void readSpreadsheet (boolean isXSSF) {
//		try {
//		InputStream inp;
//		if (isXSSF) {
//			inp = new FileInputStream("workbook.xlsx");
//		} else
//			inp = new FileInputStream("workbook.xls");
//	    //InputStream inp = new FileInputStream("workbook.xlsx");
//
//	    Workbook wb = WorkbookFactory.create(inp);
//	    Sheet sheet = wb.getSheetAt(0);
//	    Row row = sheet.getRow(0);
//	    Cell cell1 = row.getCell(0);
//	    double doubleCell = cell1.getNumericCellValue();
//	    Cell cell2 = row.getCell(2);
//	    String stringVal = cell2.getStringCellValue();
//	    Cell cell3 = row.getCell(3);	    
//	    boolean booleanVal = cell3.getBooleanCellValue();
//	    int i = 0;
//	    /*
//	    if (cell == null)
//	        cell = row.createCell(3);
//	    cell.setCellType(Cell.CELL_TYPE_STRING);
//	    cell.setCellValue("a test");
//	    */
//	    /*
//	    // Write the output to a file
//	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//	    wb.write(fileOut);
//	    fileOut.close();
//	    */
//		} catch (Exception e) {
//			
//		}
//
//	}
//	public static void readExperiment () {
//		try {
//		InputStream inp;
//		
//			inp = new FileInputStream("EXP1ES.xls");
//	    //InputStream inp = new FileInputStream("workbook.xlsx");
//
//	    Workbook wb = WorkbookFactory.create(inp);
//	    Sheet sheet = wb.getSheetAt(0);
//	    Row row = sheet.getRow(7);
//	    Cell cell1 = row.getCell(7);	
//	    cell1.setCellValue(25);
//	    double doubleCell = cell1.getNumericCellValue();
//	    int i = 0;
//	    /*
//	    if (cell == null)
//	        cell = row.createCell(3);
//	    cell.setCellType(Cell.CELL_TYPE_STRING);
//	    cell.setCellValue("a test");
//	    */
//	    /*
//	    // Write the output to a file
//	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//	    wb.write(fileOut);
//	    fileOut.close();
//	    */
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//
//	}
	public static void readAndWrite () {
		try {
			InputStream input = new FileInputStream("RubrickTest.xlsx");
//		    Workbook wb = WorkbookFactory.create(input);	
//			OPCPackage pkg = OPCPackage.open(new File("file.xlsx"));
//			  XSSFWorkbook wb = new XSSFWorkbook(pkg);
//			Package pkg = Package.getPackage("RubrickTest.xlsx");

//			POIFSFileSystem fs = new POIFSFileSystem(input);  
//	        XSSFWorkbook wb = new XSSFWorkbook(pkg);  
		    Workbook wb = WorkbookFactory.create(input);			
		    OutputStream output;
		
			output = new FileOutputStream("RubrickTest.xlsx");
			
			
	    //InputStream inp = new FileInputStream("workbook.xlsx");

	    Sheet sheet = wb.getSheetAt(0);
//        HSSFSheet sheet = wb.getSheetAt(0);  

	    Row row = sheet.getRow(2);
	    Cell cell1 = row.getCell(4);
	    if (cell1 == null)
	    	cell1 = row.createCell(5);
	    double doubleCell = cell1.getNumericCellValue();
	    int i = 0;
	    cell1.setCellValue (doubleCell + 2);
	    Cell cell2 = row.getCell(0);
	    if (cell2 != null) {
	    	String stringCell = cell2.getStringCellValue();
	    	System.out.println(stringCell);
	    }
	    wb.write(output);
	   
	    output.close();
	    input.close();
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
