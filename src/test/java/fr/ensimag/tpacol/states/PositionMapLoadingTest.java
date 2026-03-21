package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Map;
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
}

