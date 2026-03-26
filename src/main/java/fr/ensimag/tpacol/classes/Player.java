package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import fr.ensimag.tpacol.states.Position;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public class Player implements Displayable {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RED = "\u001B[31m";

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
	@NonNull
	private Inventory inventory = new Inventory();

	@Getter
	@Setter
	private String color = ANSI_GREEN;

	@Getter
	@Setter
	private int maxHearts = 3;

	@Getter
	@Setter
	private int hearts = 3;

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

	public static Map<Class<?>, String> getClassTags() {
		return new LinkedHashMap<>(Inventory.getClassTags());
	}

	public void moveTo(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	public void moveLeftOf(int x, int y) {
		moveTo(Math.max(0, x - 1), y);
	}

	public boolean hasKey(int keyId) {
		return inventory.getItems().stream()
				.filter(Key.class::isInstance)
				.map(Key.class::cast)
				.anyMatch(key -> key.getId() == keyId);
	}

	public void teleportTo(String map, int x, int y) {
		position.setMap(map);
		position.setX(x);
		position.setY(y);
	}

	public int loseHeart() {
		hearts = Math.max(0, hearts - 1);
		return hearts;
	}

	public boolean isDead() {
		return hearts <= 0;
	}

	public String getHeartsHud() {
		StringBuilder heartsBuilder = new StringBuilder();
		heartsBuilder.append("♥".repeat(Math.max(0, hearts)));

		if (heartsBuilder.isEmpty()) {
			heartsBuilder.append("x");
		}

		return ANSI_RED + heartsBuilder + ANSI_RESET;
	}

	public void display(TerminalDisplay display, int x, int y) {
		String tint = color == null ? "" : color;
		display.write(tint + icon + ANSI_RESET, x + position.getX(), y + position.getY());
	}

}
