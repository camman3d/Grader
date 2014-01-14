package framework.manifests;

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
    private List<String> onyens;

    public GradingManifest(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
