package core;

import java.awt.*;

/**
 * @author Alexandre Clement
 *         Created the 07 novembre 2016.
 */
public abstract class Instruction implements Executable {
    private String longSyntax;
    private Character shortSyntax;
    private Color colorSyntax;

    public String longSyntax() { return longSyntax; }
    public Character shortSyntax() { return shortSyntax; }
    public Color colorSyntax() { return colorSyntax; }

    @Override
    public String rewrite() {
        return String.valueOf(shortSyntax);
    }

    @Override
    public String translate() {
        return String.valueOf(colorSyntax.getRGB());
    }

    @Override
    public String toString() {
        return longSyntax().charAt(0) + longSyntax().substring(1).toLowerCase();
    }

    public Instruction(String longSyntax, Character shortSyntax, Color colorSyntax) {
        this.longSyntax = longSyntax;
        this.shortSyntax = shortSyntax;
        this.colorSyntax = colorSyntax;
    }
}
