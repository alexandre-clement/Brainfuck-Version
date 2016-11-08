package Core;

import exception.InvalidFile;
import exception.OutOfMemoryException;
import exception.OverflowException;
import exception.UncheckedException;
import language.Language;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public class Core {
    private final static int CELL = 256;
    private final static byte MIN = Byte.MIN_VALUE; // -128
    private final static byte MAX = Byte.MAX_VALUE; // 127
    private final static byte MAXVALUE = MAX - MIN - CELL;
    private final static int MEMORY_CAPACITY = 30000;

    private Language language;
    private List<Instruction> instructions;
    private byte[] M;
    private int p;
    private int i;

    public Core(Language language) {
        this.language = language;
        instructions = getInstructions();
        M = new byte[MEMORY_CAPACITY];
        p = 0;
        i = 0;
    }

    public Instruction match(char c) {
        for (Instruction instruction: instructions)
            if (instruction.shortSyntax().equals(c)) return instruction;
        return new Null();
    }

    public int getExecutionPointer() {
        return i;
    }

    private int nextInstruction() {
        return ++i;
    }

    private int previousInstruction () {
        return --i;
    }

    public int getDataPointer() {
        return p;
    }

    public void getMemorySnapshot() {
        for (int i=0; i<MEMORY_CAPACITY; i++)
            if (!dp(i))
                language.stout("C" + i + ": " + getValue(i));
    }

    private boolean dp() {
        return M[p] == 0;
    }

    private boolean dp(int p) {
        return M[p] == 0;
    }

    public void bound(int i) {
        this.i = i;
    }

    private int getValue() {
        return M[p] >= 0 ? M[p] : CELL + M[p];
    }

    private int getValue(int p) {
        return M[p] >= 0 ? M[p] : CELL + M[p];
    }

    private void setValue(int value) {
        M[p] = value <= MAX ? (byte) value : (byte) (CELL - value);
    }

    private List<Instruction> getInstructions() {
        return new ArrayList<Instruction>(Arrays.asList(new Incr(), new Decr(), new Right(), new Left(), new Out(), new In(), new Jump(), new Back()));
    }

    private class Incr extends Instruction {
        private Incr() {
            super("INCR", '+', new Color(255, 255, 255));
        }

        @Override
        public void execute() throws OverflowException {
            if (M[p] != MAXVALUE)
                M[p]++;
            else
                throw new OverflowException();
            nextInstruction();
        }
    }

    private class Decr extends Instruction {
        private Decr() {
            super("DECR", '-', new Color(75, 0, 130));
        }

        @Override
        public void execute() throws OverflowException {
            if (M[p] != 0)
                M[p]--;
            else
                throw new OverflowException();
            nextInstruction();
        }
    }

    private class Left extends Instruction {
        private Left() {
            super("LEFT", '<', new Color(148, 0, 211));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (p != 0)
                p--;
            else
                throw new OutOfMemoryException();
        }
    }

    private class Right extends Instruction {
        private Right() {
            super("RIGHT", '>', new Color(0, 0, 255));
        }

        @Override
        public void execute() throws OutOfMemoryException {
            if (p != M.length - 1)
                p++;
            else
                throw new OutOfMemoryException();
            nextInstruction();
        }
    }

    private class Out extends Instruction {
        private Out() {
            super("OUT", '.', new Color(0, 255, 0));
        }

        @Override
        public void execute() throws InvalidFile {
            // language.write(getValue());
            System.out.print((char) getValue());
            nextInstruction();
        }
    }

    private class In extends Instruction {
        private In() {
            super("In", ',', new Color(255, 255, 0));
        }

        @Override
        public void execute() throws InvalidFile {
            setValue(language.read());
        }
    }

    private class Jump extends Instruction {
        private Jump() {
            super("JUMP", '[', new Color(255, 127, 0));
        }

        @Override
        public void execute() throws UncheckedException {
            if (dp()) {
                int n = 0;
                Instruction instruction;
                while (n >= 0) {
                    if (!language.hasNext()) throw new UncheckedException();
                    instruction = language.next();
                    if (instruction instanceof Jump)
                        n += 1;
                    else if (instruction instanceof Back)
                        n -= 1;
                    nextInstruction();
                }
            }
            nextInstruction();
        }
    }

    private class Back extends Instruction {
        private Back() {
            super("BACK", ']', new Color(255, 0, 0));
        }

        @Override
        public void execute() throws UncheckedException {
            if (!dp()) {
                int n = 0;
                Instruction instruction;
                while (n <= 0) {
                    if (!language.hasPrevious()) throw new UncheckedException();
                    instruction = language.previous();
                    if (instruction instanceof Jump)
                        n += 1;
                    else if (instruction instanceof Back)
                        n -= 1;
                    previousInstruction();
                }
            }
            nextInstruction();
        }
    }

    public class Null extends Instruction {
        public Null() {
            super("NULL", '\0', Color.BLACK);
        }

        @Override
        public void execute() throws OverflowException, OutOfMemoryException, InvalidFile, UncheckedException {
            nextInstruction();
        }
    }
}



