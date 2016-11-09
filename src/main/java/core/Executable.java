package core;

import exception.InvalidFile;
import exception.OutOfMemoryException;
import exception.OverflowException;
import exception.UncheckedException;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public interface Executable {
    void execute() throws OverflowException, OutOfMemoryException, InvalidFile, UncheckedException;
    String rewrite();
    String translate();
}