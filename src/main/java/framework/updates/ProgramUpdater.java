package framework.updates;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/21/14
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProgramUpdater {

    private String updateLocation;

    public ProgramUpdater(String updateLocation) {
        this.updateLocation = updateLocation;
    }

    private void downloadPatch(URL url) {
        String name = url.toString();
        name = name.substring(name.lastIndexOf("/") + 1);
        File file = new File("config/updates/" + name);

        // Make sure the folder exists
        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        try {
            FileUtils.copyURLToFile(url, file);
            return;
        } catch (IOException e) {
            System.err.println("Error downloading patch: " + url);
        }
        if (file.exists())
            file.delete();
    }

    private UpdatePatch loadPatchFile(File file) {
        try {
            // Load the class file
            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            Class<?> _class = classLoader.loadClass(file.getName().replace(".class", ""));
            if (ProgramUpdater.class.isAssignableFrom(_class))
                return (UpdatePatch) _class.newInstance();
            System.err.println("Class is not a valid patch: " + file);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading patch class: " + file);
        } catch (InstantiationException e) {
            System.err.println("Error instantiating patch class: " + file);
        } catch (IllegalAccessException e) {
            System.err.println("Error instantiating patch class: " + file);
        }
        // Delete the file since it's bad
        file.delete();
        return null;
    }

    private List<UpdatePatch> loadLocalPatches() {
        File patchFolder = new File("config/updates");
        List<UpdatePatch> patches = new ArrayList<UpdatePatch>();

        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{patchFolder.toURI().toURL()});
            File[] updateFiles = patchFolder.listFiles();
            if (updateFiles != null)
                for (File patchFile : updateFiles) {
                    String name = patchFile.getName().replace(".class", "");
                    Class<?> _class = classLoader.loadClass(name);
                    patches.add((UpdatePatch) _class.newInstance());
                }
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return patches;
    }

    private List<String> loadOnlinePatchFile() {
        try {
            URL url = new URL(updateLocation);
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);

            String[] lines = writer.toString().split("\n");
            List<String> patches = new ArrayList<String>();
            for (String line : lines) {
                String l = line.trim();
                if (!l.isEmpty())
                    patches.add(l);
            }
            return patches;
        } catch (MalformedURLException e) {
            System.err.println("Bad URL");
        } catch (IOException e) {
            System.err.println("Error opening URL");
        }
        return new ArrayList<String>();
    }

    private void checkForUpdates() {
        System.out.println("Checking for updates...");

        // Build a list of patches that we've already downloaded
        File patchFolder = new File("config/updates");
        File[] updateFiles = patchFolder.listFiles();
        List<String> patchNames = new ArrayList<String>();
        if (updateFiles != null)
            for (File file : updateFiles)
                patchNames.add(file.getName());

        // Load the list of patches from online
        try {
            List<String> patches = loadOnlinePatchFile();
            for (String patchUrl : patches) {
                if (!patchUrl.isEmpty()) {
                    String name = patchUrl.substring(patchUrl.lastIndexOf("/") + 1);
                    if (!patchNames.contains(name)) {
                        System.out.println("Download patch: " + patchUrl);
                        downloadPatch(new URL(patchUrl));
                    }
                }
            }
            System.out.println("Up to date!");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * The purpose of this method is to make sure that all patches are downloaded and incorporated.
     */
    public void update() {

        // Load the patch files
        checkForUpdates();
        List<UpdatePatch> patches = loadLocalPatches();

        // Make sure that they are in order
        Collections.sort(patches, new Comparator<UpdatePatch>() {
            @Override
            public int compare(UpdatePatch o1, UpdatePatch o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });

        // Run them
        for (UpdatePatch patch : patches)
            patch.patch();
    }


}
