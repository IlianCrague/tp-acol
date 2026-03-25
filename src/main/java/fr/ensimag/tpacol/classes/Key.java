package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class Key extends Item {
    /**
     * id matching with one or more door(s) to determine which door can be opened with this key
     */
    @Getter
    @Setter
    private int id;

    public Key() {
        super();
    }

    public Key(String name, String icon, int x, int y, int id) {
        super(name, icon, x, y);
        this.id = id;
    }

    public Key(int id, int x, int y) {
        this("Key", "k", x, y, id);
    }

    public void display(TerminalDisplay display, int x, int y) {
        display.write(colorize(getIcon()), x + getX(), y + getY());
    }

}
