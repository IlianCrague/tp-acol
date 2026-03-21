package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Map implements Displayable {

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

	public void display(TerminalDisplay display, int x, int y) {
		for (Displayable element : elements) {
			element.display(display, x, y);
		}
	}
}
