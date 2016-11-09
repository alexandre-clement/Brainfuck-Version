package core;

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

    private byte[] M;
    private int p;
    private int i;

    public Core(Language language) {
        M = new byte[MEMORY_CAPACITY];
        p = 0;
        i = 0;
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
        }
    }

    private class Decr extends Instruction {
        private Decr() {
            super("DECR", '-', new Color(75, 0, 130));
        }

        @Override
        public void execute() throws OverflowException {
        }
    }

    private class Left extends Instruction {
        private Left() {
            super("LEFT", '<', new Color(148, 0, 211));
        }

        @Override
        public void execute() throws OutOfMemoryException {
        }
    }

    private class Right extends Instruction {
        private Right() {
            super("RIGHT", '>', new Color(0, 0, 255));
        }

        @Override
        public void execute() throws OutOfMemoryException {
        }
    }

    private class Out extends Instruction {
        private Out() {
            super("OUT", '.', new Color(0, 255, 0));
        }

        @Override
        public void execute() throws InvalidFile {
        }
    }

    private class In extends Instruction {
        private In() {
            super("In", ',', new Color(255, 255, 0));
        }

        @Override
        public void execute() throws InvalidFile {
        }
    }

    private class Jump extends Instruction {
        private Jump() {
            super("JUMP", '[', new Color(255, 127, 0));
        }

        @Override
        public void execute() throws UncheckedException {

        }
    }

    private class Back extends Instruction {
        private Back() {
            super("BACK", ']', new Color(255, 0, 0));
        }

        @Override
        public void execute() throws UncheckedException {
        }
    }
}



