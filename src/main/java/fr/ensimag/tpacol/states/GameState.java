package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Interactable;
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

    public String interactWith(Interactable target) {
        return target.interact(player, getCurrentMap());
    }

    @Override
    public void display(TerminalDisplay display, int x, int y) {
        Map currentMap = getCurrentMap();
        currentMap.display(display, x, y);

        String heartsHud = player.getHeartsHud();
        int mapWidth = currentMap.getDimensions().getWidth();
        int heartsX = Math.max(0, mapWidth - TerminalDisplay.getStringWidth(heartsHud) - 1);
        display.write(heartsHud, heartsX, 0);

        player.display(display, x, y);
    }
}
