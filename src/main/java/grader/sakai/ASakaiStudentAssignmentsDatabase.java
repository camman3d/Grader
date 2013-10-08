package grader.sakai;

public class ASakaiStudentAssignmentsDatabase extends AnAbstractSakaiStudentAssignmentsDatabase<StudentAssignment> {
//	BulkAssignmentFolder bulkAssignmentFolder;
//	Map<String, StudentAssignment> nameToStudentAssignment = new HashMap();
//	public static final String GRADES_FILE_NAME = "grades.csv";
	 
	public ASakaiStudentAssignmentsDatabase(BulkAssignmentFolder aBulkAssignmentFolder) {
		super(aBulkAssignmentFolder);
//		bulkAssignmentFolder = aBulkAssignmentFolder;
//		makeTable();
	}
//	void makeTable() {
//		Set<String> studentFolderNames = bulkAssignmentFolder.getStudentFolderNames();
//		for (String aFolderName:studentFolderNames) {
//			
//			String studentId = Common.shortFileName(aFolderName);
//			System.out.println(aFolderName);
//			
//			StudentAssignment studentAssignment = new ASakaiStudentAssignment(bulkAssignmentFolder.getStudentFolder(aFolderName));
//			nameToStudentAssignment.put(studentId, studentAssignment);
//		}
//	}
//	
//	public Set<String> getStudentIds() {
//		return nameToStudentAssignment.keySet();
//		
//	}
//	
//	public Collection<StudentAssignment> getStudentAssignments() {
//		return nameToStudentAssignment.values();
//	}
//	public StudentAssignment getStudentAssignment(String aStudentId) {
//		return nameToStudentAssignment.get(aStudentId);
//	}
	@Override
	protected StudentAssignment createAssignment(String aStudentDescription, String aFolderName) {
		// TODO Auto-generated method stub
		return new ASakaiStudentAssignment(aStudentDescription, bulkAssignmentFolder.getStudentFolder(aFolderName));
	}
	
	public static void main (String[] args) {
		BulkAssignmentFolder bulkFolder = new ASakaiBulkAssignmentFolder(ASakaiBulkAssignmentFolder.DEFAULT_BULK_DOWNLOAD_FOLDER, ASakaiBulkAssignmentFolder.DEFAULT_ASSIGNMENT_NAME);
		GenericStudentAssignmentDatabase<StudentAssignment> studentAssignmentDatabase = new ASakaiStudentAssignmentsDatabase(bulkFolder);
		System.out.println(studentAssignmentDatabase.getStudentIds());
	}
	

	
	

}
