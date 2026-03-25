package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Door;
import fr.ensimag.tpacol.classes.Key;
import fr.ensimag.tpacol.classes.Map;
import fr.ensimag.tpacol.classes.NPC;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionMapLoadingTest {

    @Test
    void getLoadedMapLoadsAndCachesMap() {
        Position position = new Position("Cargo_Bay", 0, 0);

        Map first = position.getLoadedMap();
        Map second = position.getLoadedMap();

        assertNotNull(first);
        assertSame(first, second);
    }

    @Test
    void getLoadedMapFailsOnMissingMap() {
        Position position = new Position("Unknown_Map", 0, 0);

        assertThrows(IllegalStateException.class, position::getLoadedMap);
    }

    @Test
    void cargoBayLoadsSerializableElements() {
        Map map = Map.load("Cargo_Bay");

        assertEquals(3, map.getElements().size());
        assertInstanceOf(Door.class, map.getElements().get(0));
        assertInstanceOf(Key.class, map.getElements().get(1));
        assertInstanceOf(NPC.class, map.getElements().get(2));
    }
}

