package main;

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {

        // Create the printers
        Printer printer1 = new SimplePrinter();
        Printer printer2 = new ReversePrinter();

        // Read in a string
        System.out.println("Please enter a string: ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        // Print it out
        printer1.print(line);
        printer2.print(line);
    }
}