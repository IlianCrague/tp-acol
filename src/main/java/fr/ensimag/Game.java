package fr.ensimag;

import fr.ensimag.classes.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.Reader;

public class Game {

    private final TerminalDisplay display;
    private Inventory playerInventory = new Inventory();
    private Player player;

    public Game(TerminalDisplay display) {
        this.display = display;
    }

    /*private String buildTeleportPrompt(List<Displayable> teleportTargets) {
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
        if (normalized.isEmpty()) return null;

        if (normalized.equals("q")) {
            quitRequested[0] = true;
            return null;
        }

        try {
            int choice = Integer.parseInt(normalized);
            if (choice < 1 || choice > teleportTargets.size()) {
                return "Choose a number between 1 and " + teleportTargets.size();
            }

            Displayable selected = teleportTargets.get(choice - 1);
            int targetX, targetY;

            if (selected instanceof Door d) {
                targetX = d.getX();
                targetY = d.getY();
            } else if (selected instanceof Item i) {
                targetX = i.getX();
                targetY = i.getY();
            } else if (selected instanceof Player p) {
                targetX = p.getX();
                targetY = p.getY();
            } else if (selected instanceof NPC n) {
                targetX = n.getX();
                targetY = n.getY();
            } else {
                return "Invalid target";
            }

            player.setX(targetX - 1);
            player.setY(targetY);

        } catch (NumberFormatException e) {
            return "Invalid input";
        }

        return null;
    }*/

    public void run() throws Exception {

        // INIT JLINE
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        Reader reader = terminal.reader();

        var w1 = new Weapon("Sword", "😂", 0, 0, 10);
        var w2 = new Weapon("Spear", "🐛", 0, 0, 20);

        player = new Player("Hero", "@", 8, 11);

        var door = new Door(1, 30, 6);
        var key = new Key(1, 12, 8);
        var npc = new NPC("Merchant", "M", 22, 10);

        playerInventory.addItem(w1);
        playerInventory.addItem(w2);

        Map gameMap = new Map();
        gameMap.addElement(door);
        gameMap.addElement(key);
        gameMap.addElement(npc);

        boolean running = true;
        boolean needsDisplay = true;

        // BOUCLE DE JEU
        while (running) {

            // INPUT NON BLOQUANT
            if (reader.ready()) {
                int ch = reader.read();

                switch (ch) {
                    case 'z':
                        player.setY(player.getY() - 1);
                        needsDisplay = true;
                        break;
                    case 's':
                        player.setY(player.getY() + 1);
                        needsDisplay = true;
                        break;
                    case 'q':
                        player.setX(player.getX() - 1);
                        needsDisplay = true;
                        break;
                    case 'd':
                        player.setX(player.getX() + 1);
                        needsDisplay = true;
                        break;
                    case 'x':
                        running = false;
                        break; // quitter
                }
            }

            // RENDER
            if (needsDisplay) {
                terminal.puts(InfoCmp.Capability.clear_screen);
                terminal.flush();

                display.draw_rectangle(1, 1, 38, 18, false);
                display.write("Dungeon map", 3, 2);
                gameMap.display(display, 0, 0);
                player.display(display, 0, 0);

                display.render();

                // FPS (~60)
                Thread.sleep(16);
                needsDisplay = false;
            }

        }

        terminal.close();
    }
}