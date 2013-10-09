package grader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadDocxFile {
	
	public static void main(String[] args) {
		try {
		XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(new File("sources.docx")));  
		XWPFWordExtractor wx = new XWPFWordExtractor(docx);  
		String text = wx.getText();  
		System.out.println("text = "+text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
