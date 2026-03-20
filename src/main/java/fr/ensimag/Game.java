package fr.ensimag;

import fr.ensimag.classes.Inventory;
import fr.ensimag.classes.Map;
import fr.ensimag.classes.Player;
import fr.ensimag.classes.Door;
import fr.ensimag.classes.Key;
import fr.ensimag.classes.Item;
import fr.ensimag.classes.NPC;
import fr.ensimag.classes.Weapon;

import java.util.List;

public class Game {
    private final TerminalDisplay display;

    public Game(TerminalDisplay display) {
        this.display = display;
    }

    private Inventory playerInventory = new Inventory();

    private String buildTeleportPrompt(List<Displayable> teleportTargets) {
        StringBuilder promptBuilder = new StringBuilder("Teleport: ");
        for (int i = 0; i < teleportTargets.size(); i++) {
            Displayable target = teleportTargets.get(i);
            String label = target.getClass().getSimpleName();
            if (target instanceof Item item) {
                label = item.getName();
            } else if (target instanceof NPC npc) {
                label = npc.getName();
            }
            if (i > 0) {
                promptBuilder.append(", ");
            }
            promptBuilder.append(i + 1).append("=").append(label);
        }
        if (!teleportTargets.isEmpty()) {
            promptBuilder.append(", ");
        }
        promptBuilder.append("q=quit, Enter=stay");
        return promptBuilder.toString();
    }

    private String handleTeleportInput(
            String input,
            List<Displayable> teleportTargets,
            Player player,
            boolean[] quitRequested
    ) {
        String normalized = input.trim().toLowerCase();
        if (normalized.isEmpty()) {
            return null;
        }
        if (normalized.equals("q")) {
            quitRequested[0] = true;
            return null;
        }

        try {
            int choice = Integer.parseInt(normalized);
            if (choice < 1 || choice > teleportTargets.size()) {
                return "Choose a number between 1 and " + teleportTargets.size() + ", q or Enter.";
            }

            Displayable selected = teleportTargets.get(choice - 1);
            int targetX;
            int targetY;

            if (selected instanceof Door targetDoor) {
                targetX = targetDoor.getX();
                targetY = targetDoor.getY();
            } else if (selected instanceof Item targetItem) {
                targetX = targetItem.getX();
                targetY = targetItem.getY();
            } else if (selected instanceof Player targetPlayer) {
                targetX = targetPlayer.getX();
                targetY = targetPlayer.getY();
            } else if (selected instanceof NPC targetNpc) {
                targetX = targetNpc.getX();
                targetY = targetNpc.getY();
            } else {
                return "Selected element has no coordinates.";
            }

            player.setX(targetX - 1);
            player.setY(targetY);
            return null;
        } catch (NumberFormatException e) {
            return "Choose a valid number, q or Enter.";
        }
    }

    public void run() {
        var w1 = new Weapon("Sword", "😂", 0, 0, 10); // 𐃉
        var w2 = new Weapon("Spear", "🐛", 0, 0, 20); // 𐃆
        var player = new Player("Hero", "@", 8, 11);
        var door = new Door(1, 30, 6);
        var key = new Key(1, 12, 8);
        var npc = new NPC("Merchant", "M", 22, 10);
        playerInventory.addItem(w1);
        playerInventory.addItem(w2);

        Map gameMap = new Map();
        gameMap.addElement(door);
        gameMap.addElement(key);
        gameMap.addElement(npc);

        boolean quit = false;
        final boolean[] quitRequested = {false};
        while (!quit) {
            // compute game logic here
            display.draw_rectangle(1, 1, 38, 18, false);
            display.write("Dungeon map", 3, 2);
            gameMap.display(display, 0, 0);
            player.display(display, 0, 0);

            List<Displayable> teleportTargets = gameMap.getElements();
            final String prompt = buildTeleportPrompt(teleportTargets);

            display.setInput(new TerminalDisplay.Input(
                    prompt,
                    input -> handleTeleportInput(input, teleportTargets, player, quitRequested)
            ));

            // keep this at the end of the loop
            display.render();
            if (quitRequested[0]) {
                quit = true;
            }
        }
    }
}
