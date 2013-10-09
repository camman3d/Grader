package grader.documents;


import util.misc.Common;

// TODO: There are x86 Windows-specific paths hard-coded here. That should probably be changed
public class AWordDocumentDisplayer implements DocumentDisplayer {
    public static final String WORD_14 = "C:\\Program Files\\Microsoft Office\\Office14\\WINWORD";
    public static final String WORD_12 = "C:\\Program Files\\Microsoft Office\\Office12\\WINWORD";
    public static final int DEFAULT_WORD_NO = 14;
    public String wordPath;
    int officeNo = 14;

    public AWordDocumentDisplayer() {
        setWordPath();
    }

    public AWordDocumentDisplayer(int anOfficeNo) {
        officeNo = anOfficeNo;
        setWordPath();
    }

    void setWordPath() {
        wordPath = "C:/Program Files/Microsoft Office/Office" + officeNo + "/WINWORD";
    }

    public void displayFile(String aFileName) {
        String windowsName = Common.toWindowsFileName(aFileName);

        String[] command = {wordPath, windowsName};
        Common.exec(command);
    }

}
