// GameManager.java
package base;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private GameEngine gameEngine;
    private MenuManager menuManager;
    private ScoreManager scoreManager;
    private IOSpecialist io;
    private Map<Integer, MenuCommandInterface> menuCommands;

    public GameManager(IOSpecialist inputHandler) {
        this.io = inputHandler;
        this.gameEngine = new GameEngine(inputHandler);
        this.menuManager = new MenuManager(inputHandler);
        this.scoreManager = new ScoreManager();
    }

    // Add getter methods for command classes
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }


    private void initializeMenuCommands() {
        menuCommands = new HashMap<>();

        // Menu Commands
        menuCommands.put(GameConstants.MENU_QUIT, new MenuCommandInterface() {
            public boolean execute(GameManager manager) {
                manager.handleQuit();
                return false;
            }
        });

        menuCommands.put(GameConstants.MENU_PLAY, new MenuCommandInterface() {
            public boolean execute(GameManager manager) {
                manager.handlePlayGame();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_HIGH_SCORES, new MenuCommandInterface() {
            public boolean execute(GameManager manager) {
                manager.scoreManager.showHighScores();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_RULES, new MenuCommandInterface() {
            public boolean execute(GameManager manager) {
                manager.menuManager.displayRules();
                return true;
            }
        });

        menuCommands.put(GameConstants.MENU_INSPIRATION, new MenuCommandInterface() {
            public boolean execute(GameManager manager) {
                manager.menuManager.displayInspiration();
                return true;
            }
        });
    }

    private boolean handleMenuChoice(int choice) {
        // Use Command Factory to create command
        MenuCommandInterface command = CommandFactory.createMenuCommand(choice);
        return command.execute(this);
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

    public void handleQuit() {
        GameState state = gameEngine.getGameState();
        if (state.getDominoes() == null) {
            System.out.println("It is a shame that you did not want to play");
        } else {
            System.out.println("Thank you for playing");
        }
        System.exit(0);
    }

    public void handlePlayGame() {
        menuManager.displayDifficultyMenu();
        int difficulty = menuManager.getDifficultyChoice();
        gameEngine.initializeGame(difficulty);
        gameEngine.startGameSession();
    }
}