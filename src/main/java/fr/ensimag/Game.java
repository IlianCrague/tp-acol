package fr.ensimag;

import fr.ensimag.classes.Inventory;
import fr.ensimag.classes.Weapon;

public class Game {
    private final TerminalDisplay display;

    public Game(TerminalDisplay display) {
        this.display = display;
    }

    private Inventory playerInventory = new Inventory();

    public void run() {
        var w1 = new Weapon("Sword", "😂", 0, 0, 10); // 𐃉
        var w2 = new Weapon("Spear", "🐛", 0, 0, 20); // 𐃆
        playerInventory.addItem(w1);
        playerInventory.addItem(w2);
        boolean quit = false;
        while (!quit) {
            // compute game logic here
            playerInventory.display(display, 5, 5);
            if (playerInventory.getSize() == 2) {
                display.setInput(TerminalDisplay.Input.yesNo("Do you want to equip the sword?", equip -> {
                    if (equip) {
                        playerInventory.removeItem(w1);
                    }
                }));
            } else {
                display.setInput(TerminalDisplay.Input.EMPTY);
                quit = true;
            }

            // keep this at the end of the loop
            display.render();
        }
    }
}
