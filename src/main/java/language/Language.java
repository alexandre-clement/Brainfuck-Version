package language;

import Core.*;
import exception.InvalidFile;
import exception.OutOfMemoryException;
import exception.OverflowException;
import exception.UncheckedException;

import java.io.*;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class Language {
    private RandomAccessFile file;
    private Core core;
    private long length;
    private Reader input;
    private Writer output;


    public void setFile(String filename) throws IOException {
        file = new RandomAccessFile(filename, "r");
        length = file.length();
    }

    public void setInput(String filename) throws InvalidFile {
        if (filename == null)
            input = new InputStreamReader(System.in);
        else
            try {
                input = new FileReader(filename);
            } catch (FileNotFoundException exception) {
                throw new InvalidFile();
            }
    }

    public void setOutput(String filename) throws InvalidFile {
        if (filename == null)
            output = new OutputStreamWriter(System.out);
        else
            try {
                output = new FileWriter(filename);
            } catch (IOException exception) {
                throw new InvalidFile();
            }
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public Core execute() throws OverflowException, OutOfMemoryException, InvalidFile, UncheckedException {
        while(hasNext())
            next().execute();
        return core;
    }

    public boolean hasNext() {
        return core.getExecutionPointer() < length;
    }

    public Instruction next() {
        try {
            return core.match((char) file.read());
        } catch (IOException exception) {
            System.out.println("Next Fail");
        }
        return null;
    }

    public boolean hasPrevious() {
        return core.getExecutionPointer() > 0;
    }

    public Instruction previous() {
        try {
            file.seek(core.getExecutionPointer());
            return core.match((char) file.read());
        } catch (IOException exception) {
            System.out.println("Previous fail");
        }
        return null;
    }

    public int read() throws InvalidFile {
        try {
            return input.read();
        } catch (IOException exception) {
            throw new InvalidFile();
        }
    }

    public void write(int value) throws InvalidFile {
        try {
            output.write(value);
        } catch (IOException exception) {
            throw new InvalidFile();
        }
    }

    public void stout(Object object) {
        System.out.println(object);
    }
}
