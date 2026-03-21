package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class Door implements Displayable {
    /**
     * id matching with one key
     */
    @Getter
    private final int id;

    @Getter
    private final String icon;

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    public Door(int id, String icon, int x, int y) {
        this.id = id;
        this.icon = icon;
        this.x = x;
        this.y = y;
    }

    public Door(int id, int x, int y) {
        this(id, "d", x, y);
    }

    public void display(TerminalDisplay display, int x, int y) {
        display.write(icon, x + this.x, y + this.y);
    }
}
