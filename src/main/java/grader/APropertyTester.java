package grader;

import java.util.HashMap;
import java.util.Map;

import bus.uigen.oadapters.ClassAdapter;
import bus.uigen.sadapters.RecordStructure;


public class APropertyTester implements PropertyTester {
	Object object;
	ClassAdapter classAdapter;
	String annotationName;
	int numEditableProperties;
	int numProperties;
	boolean isConsistent = true;
	RecordStructure recordStructure;	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ClassAdapter getClassAdapter() {
		return classAdapter;
	}

	public void setClassAdapter(ClassAdapter objectAdapter) {
		this.classAdapter = objectAdapter;
	}

	public APropertyTester(String anAnnotationName, int aNumProperties, int aNumEditableProperties, Object anObject, ClassAdapter aClassAdapter) {
		annotationName = anAnnotationName;
		numProperties = aNumProperties;
		numEditableProperties = aNumEditableProperties;
		object = anObject;
		classAdapter = aClassAdapter;
		recordStructure = classAdapter.getRecordStructure();

	}
	
	public APropertyTester(Object[] aSpecificationArray, Object anObject, ClassAdapter aClassAdapter) {
		annotationName = (String) aSpecificationArray[0];
		numProperties = (Integer) aSpecificationArray[1];
		numProperties = (Integer) aSpecificationArray[2];
		object = anObject;
		classAdapter = aClassAdapter;
		recordStructure = classAdapter.getRecordStructure();		
	}
	public String getAnnotationName() {
		return annotationName;
	}

	public void setAnnotationName(String annotationName) {
		this.annotationName = annotationName;
	}

	public int getNumEditableProperties() {
		return numEditableProperties;
	}

	public void setNumEditableProperties(int numEditableProperties) {
		this.numEditableProperties = numEditableProperties;
	}

	public int getNumProperties() {
		return numProperties;
	}

	public void setNumProperties(int numProperties) {
		this.numProperties = numProperties;
	}		
	
	public double fractionProperties() {
		return (recordStructure.getPropertyNames().size()/numProperties);
		
	}	
	public double fractionEditableProperties() {
		return (recordStructure.getEdtitablePropertyNames().size()/numEditableProperties);
		
	}	
	public Map<String, Object> setProperties ( Map<String, Object> propertyValues) {		
		for (String aPropertyName:propertyValues.keySet()) {
			try {
				
				recordStructure.set(aPropertyName, propertyValues.get(aPropertyName));
			} catch (Exception e) {
				
			}			
		}
		Map<String, Object> retVal = new HashMap();
		for (String aPropertyName: recordStructure.getPropertyNames()) {
			try {
				retVal.put(aPropertyName, recordStructure.get(aPropertyName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retVal;		
	}
	

}
