package fr.ensimag;

import fr.ensimag.classes.Inventory;
import fr.ensimag.classes.Map;
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

        Map gameMap = new Map();
        gameMap.addElement((terminal, x, y) -> terminal.draw_rectangle(1, 1, 38, 18, false));
        gameMap.addElement((terminal, x, y) -> terminal.write("Dungeon map", 3, 2));
        gameMap.addElement((terminal, x, y) -> terminal.write("🚪", 30, 6));
        gameMap.addElement((terminal, x, y) -> terminal.write("👤", 8, 11));
        gameMap.addElement((terminal, x, y) -> playerInventory.display(terminal, 5, 5));

        boolean quit = false;
        while (!quit) {
            // compute game logic here
            gameMap.display(display, 0, 0);
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
