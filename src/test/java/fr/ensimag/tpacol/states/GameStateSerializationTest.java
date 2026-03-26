package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Key;
import fr.ensimag.tpacol.classes.Weapon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameStateSerializationTest {

    @TempDir
    Path tempDir;

    @Test
    void loadParsesInventoryAndPosition() throws Exception {
        Path stateFile = tempDir.resolve("state.yml");
        Files.writeString(stateFile, """
                player:
                  name: Hero
                  icon: '@'
                  maxHearts: 3
                  hearts: 2
                  inventory:
                    items:
                      - !key
                        name: Access Key
                        id: 7
                        x: 3
                        y: 4
                      - !weapon
                        name: Blade
                        hitPoint: 21
                        x: 1
                        y: 2
                  position:
                    map: Cargo_Bay
                    x: 10
                    y: 11
                """);

        GameState gameState = GameState.load(stateFile.toString());

        assertEquals(2, gameState.getPlayer().getInventory().size());
        Key key = assertInstanceOf(Key.class, gameState.getPlayer().getInventory().get(0));
        assertEquals(7, key.getId());

        Weapon weapon = assertInstanceOf(Weapon.class, gameState.getPlayer().getInventory().get(1));
        assertEquals(21, weapon.getHitPoint());

        assertEquals("Cargo_Bay", gameState.getPlayer().getPosition().getMap());
        assertEquals(10, gameState.getPlayer().getPosition().getX());
        assertEquals(11, gameState.getPlayer().getPosition().getY());
        assertEquals(3, gameState.getPlayer().getMaxHearts());
        assertEquals(2, gameState.getPlayer().getHearts());
    }

    @Test
    void loadFailsWhenTypeIsUnsupported() throws Exception {
        Path stateFile = tempDir.resolve("state-invalid.yml");
        Files.writeString(stateFile, """
                player:
                  inventory:
                    items:
                      - !potion
                        name: Healing Potion
                """);

        IOException error = assertThrows(IOException.class, () -> GameState.load(stateFile.toString()));
        assertEquals("Failed to deserialize YAML file: " + stateFile, error.getMessage());
    }

    @Test
    void saveThenLoadRoundTripWorks() throws Exception {
        Path stateFile = tempDir.resolve("saved-state.yml");

        GameState state = new GameState();
        ArrayList<fr.ensimag.tpacol.classes.Item> inventory = new ArrayList<>();
        inventory.add(new Key("Access", "k", 2, 3, 11));
        inventory.add(new Weapon("Blade", "!", 4, 5, 9));
        state.getPlayer().setInventory(inventory);
        state.getPlayer().setPosition(new Position("Cargo_Bay", 7, 8));
        state.getPlayer().setMaxHearts(3);
        state.getPlayer().setHearts(1);

        state.save(stateFile.toString());

        GameState loaded = GameState.load(stateFile.toString());
        assertEquals(2, loaded.getPlayer().getInventory().size());
        assertEquals(11, assertInstanceOf(Key.class, loaded.getPlayer().getInventory().get(0)).getId());
        assertEquals(9, assertInstanceOf(Weapon.class, loaded.getPlayer().getInventory().get(1)).getHitPoint());
        assertEquals("Cargo_Bay", loaded.getPlayer().getPosition().getMap());
        assertEquals(7, loaded.getPlayer().getPosition().getX());
        assertEquals(8, loaded.getPlayer().getPosition().getY());
        assertEquals(3, loaded.getPlayer().getMaxHearts());
        assertEquals(1, loaded.getPlayer().getHearts());
    }
}


