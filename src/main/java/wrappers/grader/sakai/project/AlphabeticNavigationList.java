package wrappers.grader.sakai.project;

import framework.utils.GradingManifest;
import framework.utils.GradingSettings;
import grader.sakai.project.NavigationListCreator;
import grader.sakai.project.SakaiProjectDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/12/13
 * Time: 9:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlphabeticNavigationList implements NavigationListCreator {
    @Override
    public List<String> getOnyenNavigationList(SakaiProjectDatabase aSakaiProjectDatabase) {
//        List<String> onyens = new ArrayList<String>();
//        String path = GradingManifest.getActiveManifest().getDownloadPath();
//        File directory = new File(path);
//        boolean include = false;
//        for (File file : directory.listFiles()) {
//            if (file.isDirectory()) {
//                if (file.getName().contains("(" + GradingSettings.get().get("start") + ")"))
//                    include = true;
//                if (include)
//                    onyens.add(file.getName().substring(file.getName().indexOf("(") + 1, file.getName().indexOf(")")));
//                if (file.getName().contains("(" + GradingSettings.get().get("end") + ")"))
//                    include = false;
//            }
//        }
//        return onyens;

        return GradingManifest.getActiveManifest().getOnyens();
    }
}
