package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Door;
import fr.ensimag.tpacol.classes.Key;
import fr.ensimag.tpacol.classes.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateInteractionTest {

    @Test
    void pickUpKeyMovesItToInventoryAndRemovesItFromMap() {
        GameState gameState = new GameState();
        gameState.getPlayer().setPosition(new Position("Cargo_Bay", 0, 0));

        Map map = gameState.getCurrentMap();
        Key key = map.getElements().stream()
                .filter(Key.class::isInstance)
                .map(Key.class::cast)
                .findFirst()
                .orElseThrow();

        String result = gameState.interactWith(key);

        assertEquals("Picked up Key", result);
        assertFalse(map.getElements().contains(key));
        assertEquals(1, gameState.getPlayer().getInventory().size());
        assertInstanceOf(Key.class, gameState.getPlayer().getInventory().get(0));
        assertEquals(11, gameState.getPlayer().getPosition().getX());
        assertEquals(8, gameState.getPlayer().getPosition().getY());
    }

    @Test
    void openingDoorWithoutKeyStaysOnCurrentMap() {
        GameState gameState = new GameState();
        gameState.getPlayer().setPosition(new Position("Cargo_Bay", 0, 0));

        Door door = gameState.getCurrentMap().getElements().stream()
                .filter(Door.class::isInstance)
                .map(Door.class::cast)
                .findFirst()
                .orElseThrow();

        String result = gameState.interactWith(door);

        assertEquals("Door is locked (missing key 1)", result);
        assertEquals("Cargo_Bay", gameState.getPlayer().getPosition().getMap());
        assertEquals(29, gameState.getPlayer().getPosition().getX());
        assertEquals(6, gameState.getPlayer().getPosition().getY());
    }

    @Test
    void openingDoorWithKeyChangesMapAndDestinationPosition() {
        GameState gameState = new GameState();
        gameState.getPlayer().setPosition(new Position("Cargo_Bay", 0, 0));

        Map map = gameState.getCurrentMap();
        Key key = map.getElements().stream()
                .filter(Key.class::isInstance)
                .map(Key.class::cast)
                .findFirst()
                .orElseThrow();
        Door door = map.getElements().stream()
                .filter(Door.class::isInstance)
                .map(Door.class::cast)
                .findFirst()
                .orElseThrow();

        gameState.interactWith(key);
        String result = gameState.interactWith(door);

        assertEquals("Door opened: Engine_Room", result);
        assertEquals("Engine_Room", gameState.getPlayer().getPosition().getMap());
        assertEquals(3, gameState.getPlayer().getPosition().getX());
        assertEquals(3, gameState.getPlayer().getPosition().getY());
    }
}

