package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;

import java.util.ArrayList;

public class Inventory implements Displayable {
    private ArrayList<Item> items;
    private int maxItemNameLength;

    public Inventory() {
        items = new ArrayList<>();
        maxItemNameLength = 9; // length of "Inventory"
    }

    public void display(TerminalDisplay display, int x, int y) {
        display.draw_rectangle(x, y, maxItemNameLength + 4, items.size() + 2, true);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            display.write(item.getIcon() + " " + item.getName(), x + 2, y + 1 + i);
        }
    }

    public void addItem(Item i) {
        items.add(i);
        maxItemNameLength = Math.max(maxItemNameLength, i.getName().length() + i.getIcon().length() + 1);
    }

    public void removeItem(Item i) {
        items.remove(i);
        maxItemNameLength = 9; // reset to default
        for (Item item : items) {
            maxItemNameLength = Math.max(maxItemNameLength, item.getName().length() + item.getIcon().length() + 1);
        }
    }

    public int getSize() {
        return items.size();
    }
}
