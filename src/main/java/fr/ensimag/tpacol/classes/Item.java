package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Teleportable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public abstract class Item implements Displayable, Teleportable  {
    private static final String ANSI_YELLOW = "\u001B[33m";

    @Getter
    @Setter
    private String name;

    /**
     * emoji to display the weapon
     */
    @Getter
    @Setter
    private String icon;

    /**
     * absolute position on map/terminal
     */
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;

    protected Item() {
        // Required by SnakeYAML when using JavaBean construction
    }


    public Item(String name, String icon, int x, int y) {
        this.name = name;
        this.icon = icon;
        this.x = x;
        this.y = y;
    }

    public void display(TerminalDisplay display, int x, int y) {
        display.write(colorize(icon), x + this.x, y + this.y);
    }

    @Override
    public String getTeleportColor() {
        return ANSI_YELLOW;
    }

    @Override
    public String getTeleportLabel() {
        return name;
    }
}
