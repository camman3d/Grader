import framework.gui.SettingsWindow;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 10/2/13
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Driver {
    public static void main(String[] args) {

        SettingsWindow window = SettingsWindow.create();
        window.awaitBegin();
        System.out.println("Testing");
    }
}
