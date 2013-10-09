package grader;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ReadDocFile {
	
	public static void readDocxFile(String[] args) {
		try {
		XWPFDocument docx = new XWPFDocument(OPCPackage.openOrCreate(new File("hello.docx")));  
		XWPFWordExtractor wx = new XWPFWordExtractor(docx);  
		String text = wx.getText();  
		System.out.println("text = "+text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		File file = null;
		WordExtractor extractor = null ;
		try {

		   file = new File("sources.doc");
		   FileInputStream fis=new FileInputStream(file.getAbsolutePath());
		   HWPFDocument document=new HWPFDocument(fis);
		   extractor = new WordExtractor(document);
		   String [] fileData = extractor.getParagraphText();
		   for(int i=0;i<fileData.length;i++){
		     if(fileData[i] != null)
		       System.out.println(fileData[i]);
		   }
		}
		catch(Exception exep){
			exep.printStackTrace();
		}
	}
		

}
