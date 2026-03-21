package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Map;
import lombok.Getter;
import lombok.Setter;

public class Position {
    @Getter
    private String map;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;

    private transient Map loadedMap;

    public Position() {
        // Required by SnakeYAML JavaBean construction
    }

    public Position(String map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public void setMap(String map) {
        this.map = map;
        this.loadedMap = null;
    }

    public Map getLoadedMap() {
        if (loadedMap == null) {
            loadedMap = Map.load(map);
        }
        return loadedMap;
    }

    public void addX(int dx) {
        x += dx;
    }

    public void addY(int dy) {
        y += dy;
    }
}
