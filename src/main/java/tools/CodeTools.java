package tools;

import java.io.IOException;
import java.io.StringReader;

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

        StringBuilder newCode = new StringBuilder();
        StringReader sr = null;
        try {
            sr = new StringReader(code);
            boolean inBlockComment = false;
            boolean inLineComment = false;
            boolean out = true;

            int prev = sr.read();
            int cur;
            for(cur = sr.read(); cur != -1; cur = sr.read()) {
                if(inBlockComment) {
                    if (prev == '*' && cur == '/') {
                        inBlockComment = false;
                        out = false;
                    }
                } else if (inLineComment) {
                    if (cur == '\n') {
                        inLineComment = false;
                        out = false;
                    }
                } else {
                    if (prev == '/' && cur == '*') {
                        inBlockComment = true;
                    } else if (prev == '/' && cur == '/') {
                        inLineComment = true;
                    } else if (out){
                        newCode.append((char)prev);
                    } else {
                        out = true;
                    }
                }
                prev = cur;
            }
            if (out && !inLineComment) {
                newCode.append((char)prev);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sr != null) {
                sr.close();
            }
        }

        return newCode.toString();

        // The regex fails with nested comments
        //return code.replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "");
    }

}
