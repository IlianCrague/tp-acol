package fr.ensimag.tpacol;

import fr.ensimag.tpacol.classes.Item;
import fr.ensimag.tpacol.classes.Player;
import fr.ensimag.tpacol.states.GameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private final TerminalDisplay display;
    private final GameState gameState;

    public Game(TerminalDisplay display) {
        this.display = display;

        try {
            this.gameState = GameState.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildTeleportPrompt(List<Interactable> teleportTargets) {
        StringBuilder promptBuilder = new StringBuilder("Teleport: ");
        for (int i = 0; i < teleportTargets.size(); i++) {
            Interactable target = teleportTargets.get(i);
            String label = target.colorize(target.getTeleportLabel());
            if (i > 0) {
                promptBuilder.append(", ");
            }
            promptBuilder.append(i + 1).append("=").append(label);
        }
        if (!teleportTargets.isEmpty()) {
            promptBuilder.append(", ");
        }
        promptBuilder.append("i=inventory, q=quit, Enter=stay");
        return promptBuilder.toString();
    }

    private void renderInventoryOverlay(Player player) {
        List<String> lines = new ArrayList<>();
        lines.add("Inventory");

        if (player.getInventory().isEmpty()) {
            lines.add("(empty)");
        } else {
            for (int i = 0; i < player.getInventory().size(); i++) {
                Item item = player.getInventory().get(i);
                String name = item.getName() == null || item.getName().isBlank() ? item.getClass().getSimpleName() : item.getName();
                lines.add((i + 1) + ". " + item.colorize(item.getIcon()) + " " + name);
            }
        }

        int contentWidth = 0;
        for (String line : lines) {
            contentWidth = Math.max(contentWidth, TerminalDisplay.getStringWidth(line));
        }

        int width = contentWidth + 2;
        int height = lines.size() + 2;
        int x = 1;
        int y = 1;

        display.draw_rectangle(x, y, width, height, true);
        for (int i = 0; i < lines.size(); i++) {
            display.write(lines.get(i), x + 1, y + 1 + i);
        }
    }

    private void renderDialogOverlay(String title, String message) {
        if (message == null || message.isBlank()) {
            return;
        }

        List<String> lines = new ArrayList<>();
        lines.add(title);
        lines.add(message);

        int contentWidth = 0;
        for (String line : lines) {
            contentWidth = Math.max(contentWidth, TerminalDisplay.getStringWidth(line));
        }

        int width = Math.max(16, contentWidth + 2);
        int height = lines.size() + 2;
        int x = 1;
        int y = Math.max(1, 20 - height - 1);

        display.draw_rectangle(x, y, width, height, true);
        for (int i = 0; i < lines.size(); i++) {
            display.write(lines.get(i), x + 1, y + 1 + i);
        }
    }

    private List<Interactable> buildTeleportTargets() {
        List<Interactable> teleportTargets = new ArrayList<>();
        for (Displayable element : gameState.getCurrentMap().getElements()) {
            if (element instanceof Interactable interactable) {
                teleportTargets.add(interactable);
            }
        }
        return teleportTargets;
    }

    private String handleTeleportInput(
            String input,
            List<Interactable> teleportTargets,
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

            Interactable selected = teleportTargets.get(choice - 1);
            return gameState.interactWith(selected);

        } catch (NumberFormatException e) {
            return "Invalid input";
        }
    }

    public void run() throws Exception {

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

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
        boolean showInventory = false;
        String uiMessage = null;

        // BOUCLE DE JEU
        while (running) {
            gameState.display(display, 0, 0);

            if (showInventory) {
                renderInventoryOverlay(gameState.getPlayer());
            }

            if (uiMessage != null) {
                renderDialogOverlay("Message", uiMessage);
                uiMessage = null;
            }

            display.render();

            List<Interactable> teleportTargets = buildTeleportTargets();
            System.out.print("> " + buildTeleportPrompt(teleportTargets) + " ");
            String input = inputReader.readLine();

            if (input == null) {
                running = false;
                break;
            }

            String normalized = input.trim().toLowerCase();
            if (normalized.equals("i")) {
                showInventory = !showInventory;
                continue;
            }

            boolean[] quitRequested = new boolean[]{false};
            uiMessage = handleTeleportInput(input, teleportTargets, quitRequested);

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