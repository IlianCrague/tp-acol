package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Serialization;
import fr.ensimag.tpacol.TerminalDisplay;
import fr.ensimag.tpacol.classes.*;
import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.io.IOException;
import java.util.ArrayList;

public class GameState implements Displayable {
    @Getter
    @Setter
    private ArrayList<Item> playerInventory = new ArrayList<>();

    @Getter
    @Setter
    private Position playerPosition = new Position("Cargo_Bay", 0, 0);

    @Getter(onMethod_ = @Transient)
    @Setter
    private transient Player player = new Player("Player");

    public GameState() {
        // Required by SnakeYAML JavaBean constructor
    }

    public static GameState load() throws IOException {
        return load("state.yml");
    }

    public static GameState load(String file) throws IOException {
        return Serialization.load(
                file,
                GameState.class,
                true,
                java.util.Map.of("playerInventory", Item.class),
                java.util.Map.of(Key.class, "!key", Weapon.class, "!weapon")
        );
    }

    public void save() throws IOException {
        save("state.yml");
    }

    public void save(String file) throws IOException {
        Serialization.save(file, this, java.util.Map.of(Key.class, "!key", Weapon.class, "!weapon"));
    }

    public Map getCurrentMap() {
        return playerPosition.getLoadedMap();
    }

    @Override
    public void display(TerminalDisplay display, int x, int y) {
        Map currentMap = getCurrentMap();
        currentMap.display(display, x, y);
        for (Item item : playerInventory) {
            item.display(display, x + item.getX(), y + item.getY());
        }
        player.display(display, x + playerPosition.getX(), y + playerPosition.getY());
    }
}
