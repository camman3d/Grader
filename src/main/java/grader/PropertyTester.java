package grader;

import java.util.Map;


public interface PropertyTester {
	
	public String getAnnotationName();

	public void setAnnotationName(String annotationName) ;

	public int getNumEditableProperties() ;

	public void setNumEditableProperties(int numEditableProperties) ;

	public int getNumProperties() ;

	public void setNumProperties(int numProperties) ;
	
	public double fractionProperties() ;
	
	public double fractionEditableProperties() ;
	
	public Map<String, Object> setProperties(Map<String, Object> propertyValues) ;

}
