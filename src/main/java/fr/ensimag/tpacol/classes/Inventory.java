package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import org.yaml.snakeyaml.TypeDescription;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Inventory implements Displayable {
    @Getter
    private ArrayList<Item> items;
    private transient int maxItemNameLength;

    public Inventory() {
        items = new ArrayList<>();
        maxItemNameLength = 9; // length of "Inventory"
    }

    public void setItems(List<Item> items) {
        this.items = new ArrayList<>();
        if (items != null) {
            this.items.addAll(items);
        }
        recalculateMaxItemNameLength();
    }

    public static TypeDescription getTypeDescription() {
        TypeDescription inventoryType = new TypeDescription(Inventory.class);
        inventoryType.addPropertyParameters("items", Item.class);
        return inventoryType;
    }

    public static Map<Class<?>, String> getClassTags() {
        Map<Class<?>, String> tags = new LinkedHashMap<>();
        tags.put(Key.class, "!key");
        tags.put(Weapon.class, "!weapon");
        return tags;
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

    public boolean isEmpty() {
        return items.isEmpty();
    }

    private void recalculateMaxItemNameLength() {
        maxItemNameLength = 9;
        for (Item item : items) {
            maxItemNameLength = Math.max(maxItemNameLength, item.getName().length() + item.getIcon().length() + 1);
        }
    }

    // convenience methods

    public int size() {
        return items.size();
    }

    public Item get(int i) {
        return items.get(i);
    }
}
