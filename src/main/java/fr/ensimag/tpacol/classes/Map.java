package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Serialization;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    private final ArrayList<Displayable> elements;

    public Map() {
        this.elements = new ArrayList<>();
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
            return Serialization.load("maps/" + id, Map.class, false);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load map: " + id, e);
        }
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
}
