package fr.ensimag.tpacol.states;

import fr.ensimag.tpacol.classes.Item;
import lombok.Getter;

import java.util.ArrayList;

public class GameState {
    @Getter
    private final ArrayList<Item> playerInventory;
    @Getter
    private final PlayerPosition playerPosition;

    public GameState(ArrayList<Item> playerInventory, PlayerPosition playerPosition) {
        this.playerInventory = playerInventory;
        this.playerPosition = playerPosition;
    }
}
