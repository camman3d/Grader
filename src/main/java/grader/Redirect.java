package grader;

import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Redirect {
  public static void main(String args[]) throws Exception {
    PrintStream origOut = System.out;
    PrintStream origErr = System.err;

    InputStream stdin = null;
    stdin = new FileInputStream("Input.txt");
    PrintStream stdout = null;
    stdout = new PrintStream(new FileOutputStream("Output.txt"));
    PrintStream stderr = null;
    stderr = new PrintStream(new FileOutputStream("Error.txt"));
    origOut.println("1");
    System.out.println("2");
    origOut.println("3");
    System.err.println("4");
    origErr.println("5");

    System.setIn(stdin);
    System.setOut(stdout);
    System.setErr(stderr);

    origOut.println("\nR");
    System.out.println("T");
    origOut.println("Tq");
    System.err.println("Tqw");
    origErr.println("Test");

    origOut.println("\nRedirect:  Round #3");
    int inChar = 0;
    Scanner scanner = new Scanner(System.in);
    while (-1 != inChar) {
      try {
        inChar = System.in.read();
      } catch (Exception e) {
        // Clean up the output and bail.
        origOut.print("\n");
        break;
      }
//      origOut.write(inChar);
      origOut.println(inChar);
    }

    stdin.close();
    stdout.close();
    stderr.close();

    System.exit(0);
  }
}
