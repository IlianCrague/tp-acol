package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;

public abstract class Item implements Displayable {
    @Getter
    private final String name;

    /**
     * emoji to display the weapon
     */
    @Getter
    private final String icon;

    /**
     * absolute position on map/terminal
     */
    @Getter
    private final int x;
    @Getter
    private final int y;


    public Item(String name, String icon, int x, int y) {
        this.name = name;
        this.icon = icon;
        this.x = x;
        this.y = y;
    }

    public void display(TerminalDisplay display, int x, int y) {

    }
}
