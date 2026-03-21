package fr.ensimag.tpacol.states;

import lombok.Getter;
import lombok.Setter;

public class PlayerPosition {
    @Getter
    @Setter
    private String map;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;

    public PlayerPosition(String map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public static PlayerPosition load() {

    }
}
