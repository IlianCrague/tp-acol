package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;
import lombok.Setter;

public class NPC implements Displayable {
	@Getter
	private final String name;

	@Getter
	private final String icon;

	@Getter
	@Setter
	private int x;

	@Getter
	@Setter
	private int y;

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
		display.write(icon, x + this.x, y + this.y);
	}
}
