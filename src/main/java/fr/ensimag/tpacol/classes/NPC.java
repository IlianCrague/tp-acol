package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Interactable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class NPC implements Interactable {
	private static final String ANSI_MAGENTA = "\u001B[35m";

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String icon;

	@Getter
	@Setter
	private int x;

	@Getter
	@Setter
	private int y;

	public NPC() {
		// Required by SnakeYAML JavaBean construction
		this.icon = "N";
	}

	public NPC(String name, String icon, int x, int y) {
		this.name = name;
		this.icon = icon;
		this.x = x;
		this.y = y;
	}

	public NPC(String name, int x, int y) {
		this(name, "N", x, y);
	}

	public void display(TerminalDisplay display, int x, int y) {
		display.write(colorize(icon), x + this.x, y + this.y);
	}

	@Override
	public String getColor() {
		return ANSI_MAGENTA;
	}

	@Override
	public String getTeleportLabel() {
		return name;
	}

	@Override
	public String interact(Player player, Map currentMap) {
		player.moveLeftOf(getX(), getY());
		if ("Bandit".equalsIgnoreCase(name)) {
			int remainingHearts = player.loseHeart();
			return "Bandit attack! Hearts left: " + remainingHearts;
		}
		return "You talk to " + name;
	}
}
