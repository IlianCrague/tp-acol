package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Teleportable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class Door implements Displayable, Teleportable {
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * id matching with one key
     */
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String icon;

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    public Door() {
        // Required by SnakeYAML JavaBean construction
        this.icon = "d";
    }

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
        display.write(colorize(icon), x + this.x, y + this.y);
    }

    @Override
    public String getTeleportColor() {
        return ANSI_CYAN;
    }

    @Override
    public String getTeleportLabel() {
        return "Door " + id;
    }
}
