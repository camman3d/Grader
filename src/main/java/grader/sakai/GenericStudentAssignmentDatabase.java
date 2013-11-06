package grader.sakai;

import java.util.Collection;
import java.util.Set;

public interface GenericStudentAssignmentDatabase<GenericAssignment> {
    public Set<String> getStudentIds();

    public BulkAssignmentFolder getBulkAssignmentFolder();

    public Collection<GenericAssignment> getStudentAssignments();

    public GenericAssignment getStudentAssignment(String aStudentId);

}
