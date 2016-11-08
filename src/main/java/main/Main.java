package main;

import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new Interpreter().build(args);
        System.out.println(System.currentTimeMillis() - start);
    }
}
