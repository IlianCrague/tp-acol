package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import fr.ensimag.tpacol.states.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Player implements Displayable {
	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String icon;

	@Getter
	@Setter
	private Position position = new Position("Cargo_Bay", 0, 0);

	@Getter
	@Setter
	private ArrayList<Item> inventory = new ArrayList<>();

	public Player() {
		// Required by SnakeYAML JavaBean construction
	}

	public Player(String name, String icon) {
		this.name = name;
		this.icon = icon;
	}

	public Player(String name) {
		this(name, "@");
	}

	public void display(TerminalDisplay display, int x, int y) {
		display.write(icon, x + position.getX(), y + position.getY());
	}

}
