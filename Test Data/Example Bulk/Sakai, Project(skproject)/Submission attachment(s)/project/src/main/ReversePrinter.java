package main;

public class ReversePrinter implements Printer {

    @Override
    public void print(String message) {
        String reverse = new StringBuilder(message).reverse().toString();
        System.out.println(reverse);
    }

}