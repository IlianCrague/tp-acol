package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import fr.ensimag.tpacol.states.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Player implements Displayable {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN = "\u001B[32m";

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

	@Getter
	@Setter
	private int maxHP;

	@Getter
	@Setter
	private String color = ANSI_GREEN;

	public Player() {
		// Required by SnakeYAML JavaBean construction
	}

	public Player(String name, String icon, int maxHP) {
		this.name = name;
		this.icon = icon;
		this.maxHP = maxHP;
	}

	public Player(String name, int maxHP) {
		this(name, "@", maxHP);
	}

	public void moveTo(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void moveLeftOf(int x, int y) {
		moveTo(Math.max(0, x - 1), y);
	}

	public boolean hasKey(int keyId) {
		return inventory.stream()
				.filter(Key.class::isInstance)
				.map(Key.class::cast)
				.anyMatch(key -> key.getId() == keyId);
	}

	public void addToInventory(Item item) {
		inventory.add(item);
	}

	public void teleportTo(String map, int x, int y) {
		position.setMap(map);
		position.setX(x);
		position.setY(y);
	}

	public void display(TerminalDisplay display, int x, int y) {
		String tint = color == null ? "" : color;
		display.write(tint + icon + ANSI_RESET, x + position.getX(), y + position.getY());
	}

}
