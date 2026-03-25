package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Serialization;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.util.*;

public class Map implements Displayable {
    @Getter
    @Setter
    @NonNull
    private String name = "";
    @Getter
    @Setter
    @NonNull
    private String background = "";
    @Getter
    @Setter
    @NonNull
    private Dimensions dimensions = new Dimensions();

    private ArrayList<Displayable> elements = new ArrayList<>();

    public Map() {
        // Required by SnakeYAML JavaBean construction
    }

    public void setElements(List<Displayable> elements) {
        this.elements.clear();
        if (elements != null) {
            this.elements.addAll(elements);
        }
    }

    public void addElement(Displayable element) {
        elements.add(Objects.requireNonNull(element, "element cannot be null"));
    }

    public void removeElement(Displayable element) {
        elements.remove(element);
    }

    public List<Displayable> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public void display(TerminalDisplay display, int x, int y) {
        String[] lines = background.isBlank() ? new String[0] : background.split("\\R", -1);
        for (int row = 0; row < lines.length; row++) {
            display.write(lines[row], x, y + row);
        }
        display.write(name, 1, 0);
        for (Displayable element : elements) {
            element.display(display, x, y);
        }
    }

    public static Map load(String id) {
        try {
            return Serialization.load(
                    "maps/" + id,
                    Map.class,
                    false,
                    java.util.Map.of("elements", Displayable.class),
                    mapElementTags()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load map: " + id, e);
        }
    }

    private static java.util.Map<Class<?>, String> mapElementTags() {
        java.util.Map<Class<?>, String> tags = new LinkedHashMap<>();
        tags.put(Door.class, "!door");
        tags.put(Key.class, "!key");
        tags.put(NPC.class, "!npc");
        tags.put(Weapon.class, "!weapon");
        return tags;
    }

    @Getter
    @Setter
    public static final class Dimensions {
        private int width;
        private int height;

        public Dimensions() {
            // Required by SnakeYAML JavaBean constructor
        }

        public Dimensions(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public int getX(){
        return -1;
    }

    public int getY(){
        return -1;
    }
}
