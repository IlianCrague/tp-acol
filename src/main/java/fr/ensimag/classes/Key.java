package fr.ensimag.classes;

import lombok.Getter;

public class Key extends Item {
    /**
     * id matching with one or more door(s) to determine which door can be opened with this key
     */
    @Getter
    private int id;

    public Key(String name, String icon, int x, int y, int id) {
        super(name, icon, x, y);
        this.id = id;
    }

}
