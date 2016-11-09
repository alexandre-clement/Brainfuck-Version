package language;

import core.*;
import exception.InvalidFile;
import interpreter.Filenames;

import java.io.*;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class Language {
    private RandomAccessFile source;
    private Reader input;
    private Writer output;

    public Core runSource() {
        return null;
    }

    private void setFile() throws IOException {
        source = new RandomAccessFile(Filenames.source.getName(), "r");
    }

    private void setInput() throws InvalidFile {
        if (Filenames.input.getName() == null)
            input = new InputStreamReader(System.in);
        else
            try {
                input = new FileReader(Filenames.input.getName());
            } catch (FileNotFoundException exception) {
                throw new InvalidFile();
            }
    }

    private void setOutput() throws InvalidFile {
        if (Filenames.output.getName() == null)
            output = new OutputStreamWriter(System.out);
        else
            try {
                output = new FileWriter(Filenames.output.getName());
            } catch (IOException exception) {
                throw new InvalidFile();
            }
    }
}
