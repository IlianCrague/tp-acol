package fr.ensimag.tpacol;

import fr.ensimag.tpacol.classes.*;
import fr.ensimag.tpacol.states.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private final TerminalDisplay display;
    private final GameState gameState;
    private final Map currentMap;

    public Game(TerminalDisplay display) {
        this.display = display;

        try {
            this.gameState = GameState.load();
            this.currentMap = gameState.getCurrentMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildTeleportPrompt(List<Teleportable> teleportTargets) {
        StringBuilder promptBuilder = new StringBuilder("Teleport: ");
        for (int i = 0; i < teleportTargets.size(); i++) {
            Teleportable target = teleportTargets.get(i);
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

    private List<Teleportable> buildTeleportTargets() {
        List<Teleportable> teleportTargets = new ArrayList<>();
        for (Displayable element : currentMap.getElements()) {
            if (element instanceof Teleportable teleportable) {
                teleportTargets.add(teleportable);
            }
        }
        return teleportTargets;
    }

    private String handleTeleportInput(
            String input,
            List<Teleportable> teleportTargets,
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

            Teleportable selected = teleportTargets.get(choice - 1);
            int targetX, targetY;

            targetX = selected.getX();
            targetY = selected.getY();

            int destinationX = Math.max(0, targetX - 1);
            player.getPosition().setX(destinationX);
            player.getPosition().setY(targetY);

        } catch (NumberFormatException e) {
            return "Invalid input";
        }

        return null;
    }

    public void run() throws Exception {

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

        var door = new Door(1, 30, 6);
        var key = new Key(1, 12, 8);
        var npc = new NPC("Merchant", "M", 22, 10, 100);

        currentMap.addElement(door);
        currentMap.addElement(key);
        currentMap.addElement(npc);

        Thread saveOnShutdown = new Thread(() -> {
            try {
                System.out.println("Saving game...");
                gameState.save();
            } catch (IOException e) {
                System.err.println("Failed to save game on shutdown: " + e.getMessage());
            }
        });
        Runtime.getRuntime().addShutdownHook(saveOnShutdown);

        boolean running = true;

        // BOUCLE DE JEU
        while (running) {

            //fight test
            var testFight = new Fight(gameState.getPlayer(), npc);
            testFight.handleFight(display);

            gameState.display(display, 0, 0);
            display.render();

            List<Teleportable> teleportTargets = buildTeleportTargets();
            System.out.print("> " + buildTeleportPrompt(teleportTargets) + " ");
            String input = inputReader.readLine();

            if (input == null) {
                running = false;
                break;
            }

            boolean[] quitRequested = new boolean[]{false};
            String error = handleTeleportInput(input, teleportTargets, gameState.getPlayer(), quitRequested);
            if (error != null) {
                System.err.println("Invalid input: " + error);
            }

            if (quitRequested[0]) {
                running = false;
                gameState.save();
            }

            // Small pause keeps CPU usage low when idling in the loop.
            Thread.sleep(16);

        }

        try {
            Runtime.getRuntime().removeShutdownHook(saveOnShutdown);
        } catch (IllegalStateException ignored) {
            // JVM is already shutting down (e.g. Ctrl+C), hook is executing there.
        }
    }
}