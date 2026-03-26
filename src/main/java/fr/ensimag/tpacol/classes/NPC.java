package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.Interactable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class NPC implements Displayable, Interactable {
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

	@Getter
	@Setter
	private int maxHP;

	public NPC() {
		// Required by SnakeYAML JavaBean construction
		this.icon = "N";
	}

	public NPC(String name, String icon, int x, int y, int maxHP) {
		this.name = name;
		this.icon = icon;
		this.x = x;
		this.y = y;
		this.maxHP = maxHP;
	}

	public NPC(String name, int x, int y, int hp) {
		this(name, "N", x, y, hp);
	}

	public void display(TerminalDisplay display, int x, int y) {
		display.write(colorize(icon), x + this.x, y + this.y);
	}

	@Override
	public String getTeleportColor() {
		return ANSI_MAGENTA;
	}

	@Override
	public String getTeleportLabel() {
		return name;
	}
}
