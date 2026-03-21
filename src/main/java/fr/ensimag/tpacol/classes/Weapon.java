package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;

public class Weapon extends Item implements Displayable {
    @Getter
    private final int hitPoint;

    public Weapon(String name, String icon, int x, int y, int hitPoint) {
        super(name, icon, x, y);
        this.hitPoint = hitPoint;
    }

    public void display(TerminalDisplay display, int x, int y) {

    }

}
