package fr.ensimag.tpacol;

import fr.ensimag.tpacol.classes.Map;
import fr.ensimag.tpacol.classes.Player;

public interface Interactable extends Displayable {
    String ANSI_RESET = "\u001B[0m";

    int getX();
    int getY();

    default String getColor() {
        return "";
    }

    default String getTeleportLabel() {
        return getClass().getSimpleName();
    }

    default String colorize(String text) {
        String color = getColor();
        if (color == null || color.isEmpty()) {
            return text;
        }
        return color + text + ANSI_RESET;
    }

    default String interact(Player player, Map currentMap) {
        player.moveLeftOf(getX(), getY());
        return null;
    }
}
