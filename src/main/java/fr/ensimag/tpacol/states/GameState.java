package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Serialization;
import fr.ensimag.tpacol.TerminalDisplay;
import fr.ensimag.tpacol.classes.*;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.TypeDescription;

import java.io.IOException;

public class GameState implements Displayable {
    @Getter
    @Setter
    private Player player = new Player("Player");

    public GameState() {
        // Required by SnakeYAML JavaBean constructor
    }

    public static GameState load() throws IOException {
        return load("state.yml");
    }

    public static GameState load(String file) throws IOException {
        TypeDescription playerType = new TypeDescription(Player.class);
        playerType.addPropertyParameters("inventory", Item.class);

        TypeDescription keyType = new TypeDescription(Key.class, "!key");
        TypeDescription weaponType = new TypeDescription(Weapon.class, "!weapon");

        return Serialization.load(
                file,
                GameState.class,
                true,
                playerType,
                keyType,
                weaponType
        );
    }

    public void save() throws IOException {
        save("state.yml");
    }

    public void save(String file) throws IOException {
        Serialization.save(file, this, java.util.Map.of(Key.class, "!key", Weapon.class, "!weapon"));
    }

    public Map getCurrentMap() {
        return player.getPosition().getLoadedMap();
    }

    @Override
    public void display(TerminalDisplay display, int x, int y) {
        Map currentMap = getCurrentMap();
        currentMap.display(display, x, y);
        for (Item item : player.getInventory()) {
            item.display(display, x + item.getX(), y + item.getY());
        }
        player.display(display, x, y);
    }
}
