package Interpreter;

import interpreter.Interpreter;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class InterpreterTest {
    public void getOptionFromCommandlineTest() {
        String[] commandline = new String[] {"-p", "src/test.bf"};
        new Interpreter().build(commandline);
    }
}
