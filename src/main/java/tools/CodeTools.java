package tools;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 11/19/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class CodeTools {

    /**
     * Given code in a string, this removes all comments.
     */
    public static String removeComments(String code) {
        return code.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
    }

}
