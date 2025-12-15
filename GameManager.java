package base;

public class GameManager {
    private GameEngine gameEngine;
    private MenuManager menuManager;
    private ScoreManager scoreManager;
    private IOSpecialist io;

    public GameManager(IOSpecialist io) {
        this.io = io;
        this.gameEngine = new GameEngine(io);
        this.menuManager = new MenuManager(io);
        this.scoreManager = new ScoreManager();
    }

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

    private void displayWelcome() {
        System.out.println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
        System.out.println("Version 2.1 (c), Kevan Buckley, 2014");
        System.out.println();
    }

    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case GameConstants.MENU_QUIT:
                handleQuit();
                return false;
            case GameConstants.MENU_PLAY:
                handlePlayGame();
                break;
            case GameConstants.MENU_HIGH_SCORES:
                scoreManager.displayHighScores();
                break;
            case GameConstants.MENU_RULES:
                menuManager.displayRules();
                break;
            case GameConstants.MENU_INSPIRATION:
                menuManager.displayInspiration();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
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
        gameEngine.initializeGame(difficulty);  // ✅ difficulty argument pass කරන්න
        gameEngine.startGameSession();
    }
}