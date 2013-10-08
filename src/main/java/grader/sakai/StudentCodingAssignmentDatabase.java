package grader.sakai;

import java.util.Collection;
import java.util.Set;

public interface StudentCodingAssignmentDatabase {
	public Set<String> getStudentIds() ;
	
	public Collection<StudentCodingAssignment> getStudentCodingAssignments() ;
	public StudentCodingAssignment getStudentCodingAssignment(String aStudentId) ;

}
