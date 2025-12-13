package base;

import javax.swing.*;
import java.awt.*;

public class MenuManager {
    private IOSpecialist io;

    public MenuManager(IOSpecialist io) {
        this.io = io;
    }

    public void displayWelcome() {
        System.out.println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
        System.out.println("Version 2.1 (c), Kevan Buckley, 2014");
        System.out.println();
    }

    public String getPlayerName() {
        System.out.println(MultiLingualStringTable.getMessage(0));
        return io.getString();
    }

    public void greetPlayer(String playerName) {
        System.out.printf("%s %s. %s",
                MultiLingualStringTable.getMessage(1),
                playerName,
                MultiLingualStringTable.getMessage(2));
    }

    public void displayMainMenu() {
        System.out.println();
        printMenuHeader("Main Menu");
        System.out.println(GameConstants.MENU_PLAY + ") Play");
        System.out.println(GameConstants.MENU_HIGH_SCORES + ") View high scores");
        System.out.println(GameConstants.MENU_RULES + ") View rules");
        System.out.println(GameConstants.MENU_INSPIRATION + ") Get inspiration");
        System.out.println(GameConstants.MENU_QUIT + ") Quit");
    }

    public void displayPlayMenu(String playerName) {
        System.out.println();
        printMenuHeader("Play Menu");
        System.out.println(GameConstants.PLAY_PRINT_GRID + ") Print the grid");
        System.out.println(GameConstants.PLAY_PRINT_BOX + ") Print the box");
        System.out.println(GameConstants.PLAY_PRINT_DOMINOES + ") Print the dominos");
        System.out.println(GameConstants.PLAY_PLACE_DOMINO + ") Place a domino");
        System.out.println(GameConstants.PLAY_UNPLACE_DOMINO + ") Unplace a domino");
        System.out.println(GameConstants.PLAY_ASSISTANCE + ") Get some assistance");
        System.out.println(GameConstants.PLAY_CHECK_SCORE + ") Check your score");
        System.out.println(GameConstants.PLAY_GIVE_UP + ") Give up");
        System.out.println("What do you want to do " + playerName + "?");
    }

    public void displayDifficultyMenu() {
        System.out.println();
        printMenuHeader("Select Difficulty");
        System.out.println(GameConstants.DIFFICULTY_EASY + ") Simples");
        System.out.println(GameConstants.DIFFICULTY_MEDIUM + ") Not-so-simples");
        System.out.println(GameConstants.DIFFICULTY_HARD + ") Super-duper-shuffled");
    }

    public void displayCheatMenu() {
        System.out.println();
        printMenuHeader("So you want to cheat, huh?");
        System.out.println(GameConstants.CHEAT_FIND_DOMINO + ") Find a particular Domino (costs you " + GameConstants.CHEAT_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_LOCATION + ") Which domino is at ... (costs you " + GameConstants.CHEAT_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_CERTAINTIES + ") Find all certainties (costs you " + GameConstants.CERTAINTIES_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_POSSIBILITIES + ") Find all possibilities (costs you " + GameConstants.POSSIBILITIES_COST + ")");
        System.out.println(GameConstants.CHEAT_CHANGE_MIND + ") You have changed your mind about cheating");
        System.out.println("What do you want to do?");
    }

    public void displayRules() {
        System.out.println();
        printMenuHeader("Rules");
        System.out.println("Rules by __student");

        JFrame f = new JFrame("Rules by __student");
        f.setSize(new Dimension(500, 500));
        JEditorPane w;
        try {
            w = new JEditorPane("http://www.scit.wlv.ac.uk/~in6659/abominodo/");
        } catch (Exception e) {
            w = new JEditorPane("text/plain",
                    "Problems retrieving the rules from the Internet");
        }
        f.setContentPane(new JScrollPane(w));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void displayInspiration() {
        int index = (int) (Math.random() * (_Q.stuff.length / 3));
        String what = _Q.stuff[index * 3];
        String who = _Q.stuff[1 + index * 3];
        System.out.printf("%s said \"%s\"", who, what);
        System.out.println();
        System.out.println();
    }

    private void printMenuHeader(String title) {
        String underline = title.replaceAll(".", "=");
        System.out.println(underline);
        System.out.println(title);
        System.out.println(underline);
    }

    public int getMenuChoice() {
        return InputValidator.getValidatedInt(io,
                GameConstants.MIN_MENU_CHOICE,
                GameConstants.MAX_MENU_CHOICE,
                null);
    }

    public int getPlayMenuChoice() {
        return InputValidator.getValidatedInt(io,
                GameConstants.PLAY_GIVE_UP,
                GameConstants.PLAY_CHECK_SCORE,
                null);
    }

    public int getDifficultyChoice() {
        int choice = GameConstants.INVALID_DIFFICULTY;
        while (!isValidDifficultyChoice(choice)) {
            choice = InputValidator.getValidatedInt(io,
                    GameConstants.DIFFICULTY_EASY,
                    GameConstants.DIFFICULTY_HARD,
                    null);
        }
        return choice;
    }

    public int getCheatChoice() {
        return InputValidator.getValidatedInt(io,
                GameConstants.CHEAT_CHANGE_MIND,
                GameConstants.CHEAT_FIND_POSSIBILITIES,
                null);
    }

    private boolean isValidDifficultyChoice(int choice) {
        return choice == GameConstants.DIFFICULTY_EASY ||
                choice == GameConstants.DIFFICULTY_MEDIUM ||
                choice == GameConstants.DIFFICULTY_HARD;
    }
}