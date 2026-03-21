package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;
import lombok.Getter;

public class Player implements Displayable {
	@Getter
	private final String name;

	@Getter
	private final String icon;

	public Player(String name, String icon) {
		this.name = name;
		this.icon = icon;
	}

	public Player(String name) {
		this(name, "@");
	}

	public void display(TerminalDisplay display, int x, int y) {
		display.write(icon, x, y);
	}

}
