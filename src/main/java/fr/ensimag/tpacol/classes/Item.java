package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Interactable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public abstract class Item implements Displayable, Interactable {
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
    public String getColor() {
        return ANSI_YELLOW;
    }

    @Override
    public String getTeleportLabel() {
        return name;
    }

    @Override
    public String interact(Player player, Map currentMap) {
        player.moveLeftOf(getX(), getY());
        if (!currentMap.getElements().contains(this)) {
            return "Nothing to pick up";
        }

        currentMap.removeElement(this);
        setX(0);
        setY(0);
        player.getInventory().addItem(this);
        return "Picked up " + getName();
    }
}
