package interpreter;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
abstract class Argument implements Option {
    private String name;

    Argument(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isIn(String... commandline) {
        int isIn = 0;
        for (String command: commandline) {
            if (getName().equals(command)) isIn += 1;
        }
        return isIn == 1;
    }
}
