package fr.ensimag.tpacol.classes;

import fr.ensimag.tpacol.Displayable;
import fr.ensimag.tpacol.TerminalDisplay;

import java.util.Scanner;

public class Fight implements Displayable {

    private Player player;

    private NPC foo;

    private int NPC_HP;
    private int player_HP;

    private Scanner scanner;

    public Fight(Player player, NPC foo){
        this.foo = foo;
        this.player = player;
        NPC_HP = foo.getMaxHP();
        player_HP = player.getMaxHP();
        scanner = new Scanner(System.in);
    }

    public void handleFight(TerminalDisplay display) {

        while(NPC_HP > 0 && player_HP > 0 ){
            playerTurn();
            NPC_Turn();

            display(display, 0, 0);
        }

        if (NPC_HP <= 0){ //player won
            handleXPGain();
        }else{ //player lost
            //TODO
            //System.out.println("you lost duh");
        }
    }


    public void display(TerminalDisplay display, int x, int y) {

    }

    private void playerTurn(){
        System.out.println("What do you want to do ? (1 attack 2 other)");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("1")){
            System.out.println("attaque");
            //NPC_HP -= 10;
        }

    }

    private void NPC_Turn(){
        //player_HP -= 5;
        System.out.println("Tour du NPC");
    }

    private void handleXPGain(){
        //TODO
    }

}
