package fr.ensimag.tpacol;

public class Main {
    public static void main(String[] args) throws Exception {
        var terminalDisplay = new TerminalDisplay(40, 20);

        var game = new Game(terminalDisplay);
        game.run();
    }
}