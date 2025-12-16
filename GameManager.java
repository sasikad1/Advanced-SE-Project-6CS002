// GameManager.java
package base;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private GameEngine gameEngine;
    private MenuManager menuManager;
    private ScoreManager scoreManager;
    private IOSpecialist io;
    private Map<Integer, MenuCommand> menuCommands;

    public GameManager(IOSpecialist io) {
        this.io = io;
        this.gameEngine = new GameEngine(io);
        this.menuManager = new MenuManager(io);
        this.scoreManager = new ScoreManager();
        initializeMenuCommands();
    }

    private void initializeMenuCommands() {
        menuCommands = new HashMap<>();

        // Menu Commands
        menuCommands.put(GameConstants.MENU_QUIT, new MenuCommand() {
            public boolean execute(GameManager manager) {
                manager.handleQuit();
                return false;
            }
        });

        menuCommands.put(GameConstants.MENU_PLAY, new MenuCommand() {
            public boolean execute(GameManager manager) {
                manager.handlePlayGame();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_HIGH_SCORES, new MenuCommand() {
            public boolean execute(GameManager manager) {
                manager.scoreManager.displayHighScores();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_RULES, new MenuCommand() {
            public boolean execute(GameManager manager) {
                manager.menuManager.displayRules();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_INSPIRATION, new MenuCommand() {
            public boolean execute(GameManager manager) {
                manager.menuManager.displayInspiration();
                return true;
            }
        });
    }

    private boolean handleMenuChoice(int choice) {
        MenuCommand command = menuCommands.get(choice);
        if (command != null) {
            return command.execute(this);
        }
        System.out.println("Invalid choice. Please try again.");
        return true;
    }

    // Rest of the existing code remains the same...
    public void run() {
        displayWelcome();
        String playerName = menuManager.getPlayerName();
        gameEngine.setPlayerName(playerName);
        menuManager.greetPlayer(playerName);

        boolean continueRunning = true;
        while (continueRunning) {
            menuManager.displayMainMenu();
            int choice = menuManager.getMenuChoice();
            continueRunning = handleMenuChoice(choice);
        }
    }

    // Other methods remain unchanged...
    private void displayWelcome() {
        System.out.println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
        System.out.println("Version 2.1 (c), Kevan Buckley, 2014");
        System.out.println();
    }

    private void handleQuit() {
        GameState state = gameEngine.getGameState();
        if (state.getDominoes() == null) {
            System.out.println("It is a shame that you did not want to play");
        } else {
            System.out.println("Thank you for playing");
        }
        System.exit(0);
    }

    private void handlePlayGame() {
        menuManager.displayDifficultyMenu();
        int difficulty = menuManager.getDifficultyChoice();
        gameEngine.initializeGame(difficulty);
        gameEngine.startGameSession();
    }
}