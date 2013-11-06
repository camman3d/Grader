package grader.sakai;

import util.misc.Common;
import util.trace.Tracer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AnAbstractSakaiStudentAssignmentsDatabase<GenericAssignment> implements GenericStudentAssignmentDatabase<GenericAssignment> {
    BulkAssignmentFolder bulkAssignmentFolder;
    Map<String, GenericAssignment> nameToStudentAssignment = new HashMap();

    abstract protected GenericAssignment createAssignment(String anInd, String aFolderName);

    public AnAbstractSakaiStudentAssignmentsDatabase(BulkAssignmentFolder aBulkAssignmentFolder) {
        bulkAssignmentFolder = aBulkAssignmentFolder;
        makeTable();
    }

    void makeTable() {
        Set<String> studentFolderNames = bulkAssignmentFolder.getStudentFolderNames();
        for (String aFolderName : studentFolderNames) {
            try {
                String studentId = Common.shortFileName(aFolderName);
                Tracer.info(this, "Folder:" + aFolderName);

                GenericAssignment studentAssignment = createAssignment(studentId, aFolderName);
                nameToStudentAssignment.put(studentId, studentAssignment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public BulkAssignmentFolder getBulkAssignmentFolder() {
        return bulkAssignmentFolder;
    }

    public Set<String> getStudentIds() {
        return nameToStudentAssignment.keySet();

    }

    public Collection<GenericAssignment> getStudentAssignments() {
        return nameToStudentAssignment.values();
    }

    public GenericAssignment getStudentAssignment(String aStudentId) {
        return nameToStudentAssignment.get(aStudentId);
    }
}
