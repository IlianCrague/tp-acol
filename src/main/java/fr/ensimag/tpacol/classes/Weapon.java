package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class Weapon extends Item {
    @Getter
    @Setter
    private int hitPoint;

    public Weapon() {
        super();
    }

    public Weapon(String name, String icon, int x, int y, int hitPoint) {
        super(name, icon, x, y);
        this.hitPoint = hitPoint;
    }

    public void display(TerminalDisplay display, int x, int y) {
        display.write(colorize(getIcon()), x + getX(), y + getY());
    }

}
