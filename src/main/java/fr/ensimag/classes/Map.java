package fr.ensimag.classes;

import fr.ensimag.Displayable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Map {
	private final ArrayList<Displayable> elements;

	public Map() {
		this.elements = new ArrayList<>();
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
}
