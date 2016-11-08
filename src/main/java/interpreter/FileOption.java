package interpreter;


/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
abstract class FileOption extends Argument implements Option {
    private String filename;

    FileOption(String name) {
        super(name);
    }

    @Override
    public boolean isIn(String... commandline) {
        int isIn = 0;
        for (int i = 0; i < commandline.length - 1; i++) {
            if (getName().equals(commandline[i])) {
                filename = commandline[i + 1];
                isIn += 1;
                commandline[i + 1] = null;
            }
        }
        return isIn == 1;
    }

    String getFilename() {
        return filename;
    }
}
