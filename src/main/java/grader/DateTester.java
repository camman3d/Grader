package grader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTester {

	public static void testDate() {
		try {
		       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		       dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		       Date date = dateFormat.parse("20121202074855312");
		       
		       System.out.println(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
