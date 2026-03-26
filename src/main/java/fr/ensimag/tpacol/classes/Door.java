package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Interactable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class Door implements Displayable, Interactable {
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

    @Getter
    @Setter
    private String destinationMap;

    @Getter
    @Setter
    private int destinationX;

    @Getter
    @Setter
    private int destinationY;

    public Door() {
        // Required by SnakeYAML JavaBean construction
        this.icon = "d";
        this.destinationMap = "";
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
    public String getColor() {
        return ANSI_CYAN;
    }

    @Override
    public String getTeleportLabel() {
        return "Door " + id;
    }

    @Override
    public String interact(Player player, Map currentMap) {
        player.moveLeftOf(getX(), getY());

        if (!player.hasKey(id)) {
            return "Door is locked (missing key " + id + ")";
        }

        if (destinationMap == null || destinationMap.isBlank()) {
            return "Door opened";
        }

        player.teleportTo(destinationMap, destinationX, destinationY);
        return null;
    }
}
