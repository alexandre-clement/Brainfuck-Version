package interpreter;

import exception.*;
import language.Language;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alexandre Clement
 *         Created the 04 novembre 2016.
 */
public class Interpreter {
    private List<Argument> arguments;
    private List<Option> options;
    private Language language;

    public int build(final String... commandline) {
        language = new Language();
        arguments = getNewArgument();
        options = getOptionFromCommandline(commandline).stream().map(argument -> argument).collect(Collectors.toList());
        callOption();
        // exit(0);
        return 0;
    }

    private List<Argument> getOptionFromCommandline(final String... commandline) {
        return arguments.stream().filter(argument -> argument.isIn(commandline)).collect(Collectors.toList());
    }

    private boolean hasUniqueOption() {
        return !options.stream().filter(option -> option instanceof UniqueOption).limit(1).collect(Collectors.toSet()).isEmpty();
    }

    private void callOption() {
        try {
            for (Option option: options) {
                option.call();
            }
        } catch (IOException exception) {
            exit(127);
        } catch (OverflowException exception) {
            exit(1);
        } catch (OutOfMemoryException exception) {
            exit(2);
        } catch (InvalidFile exception) {
            exit(3);
        } catch (MalFormedException exception) {
            exit(4);
        }
    }

    private List<Argument> getNewArgument() {
        return new ArrayList<>(Arrays.asList(new InFile(), new OutFile(), new Print(), new Check(), new Rewrite(), new Translate()));
    }

    private static void exit(int code) {
        System.exit(code);
    }

    private class Print extends FileOption {
        private Print() {
            super("-p");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
            language.setFile(super.getFilename());
            language.setCore(new Core.Core(language));
            if (!hasUniqueOption())
                try {
                    language.execute().getMemorySnapshot();
                } catch (UncheckedException exception) {
                    throw new MalFormedException();
                }
        }
    }

    private class Rewrite extends UniqueOption {
        private Rewrite() {
            super("--rewrite");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, MalFormedException {
        }
    }

    private class Translate extends UniqueOption {
        private Translate() {
            super("--translate");
        }

        @Override
        public void call() throws IOException {
        }
    }

    private class InFile extends FileOption {
        private InFile() {
            super("-i");
        }

        @Override
        public void call() throws InvalidFile {
            language.setInput(super.getFilename());
        }
    }

    private class OutFile extends FileOption {
        private OutFile() {
            super("-o");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
            language.setOutput(super.getFilename());
        }
    }

    private class Check extends UniqueOption {
        private Check() {
            super("--check");
        }

        @Override
        public void call() throws IOException, OverflowException, OutOfMemoryException, InvalidFile, MalFormedException {
        }
    }
}
