package framework.grading.manifests;

import framework.navigation.BulkDownloadFolder;
import framework.navigation.StudentFolder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/14/14
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class GradingManifest {

    private String downloadPath;
    private String startOnyen = null;
    private String endOnyen = null;
    private List<String> onyens;

    public GradingManifest(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public GradingManifest(String downloadPath, String startOnyen, String endOnyen) {
        this.downloadPath = downloadPath;
        this.startOnyen = startOnyen;
        this.endOnyen = endOnyen;
    }

    public GradingManifest(String downloadPath, List<String> onyens) {
        this.downloadPath = downloadPath;
        this.onyens = onyens;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public List<String> getOnyens() {
        return onyens;
    }

    public void setOnyens(List<String> onyens) {
        this.onyens = onyens;
    }

    public void addOnyen(String onyen) {
        onyens.add(onyen);
    }

    public String getStartOnyen() {
        return startOnyen;
    }

    public void setStartOnyen(String startOnyen) {
        this.startOnyen = startOnyen;
    }

    public String getEndOnyen() {
        return endOnyen;
    }

    public void setEndOnyen(String endOnyen) {
        this.endOnyen = endOnyen;
    }

    public List<StudentFolder> getStudentFolders(BulkDownloadFolder downloadFolder) {
        if (onyens != null)
            return downloadFolder.getStudentFolders(onyens);
        if (startOnyen != null && endOnyen != null)
            return downloadFolder.getStudentFolders(startOnyen, endOnyen);
        throw new UnsupportedOperationException();
    }
}
