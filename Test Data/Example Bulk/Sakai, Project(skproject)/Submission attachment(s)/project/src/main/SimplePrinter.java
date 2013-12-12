package main;

import util.annotations.Tags;

@Tags({"Printer"})
public class SimplePrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

}