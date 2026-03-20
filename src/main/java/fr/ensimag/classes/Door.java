package fr.ensimag.classes;

import fr.ensimag.Displayable;
import fr.ensimag.TerminalDisplay;
import lombok.Getter;

public class Door implements Displayable {
    /**
     * id matching with one key
     */
    @Getter
    private int id;

    public void display(TerminalDisplay display, int x, int y) {
    }
}
