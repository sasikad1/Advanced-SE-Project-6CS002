package base;

public class Main {
    private GameEngine gameEngine;
    private MenuManager menuManager;
    private ScoreManager scoreManager;

    public Main() {
        IOSpecialist io = new IOSpecialist();
        this.gameEngine = new GameEngine(io);
        this.menuManager = new MenuManager(io);
        this.scoreManager = new ScoreManager();
    }

    public void run() {
        menuManager.displayWelcome();
        String playerName = menuManager.getPlayerName();
        gameEngine.setPlayerName(playerName);
        menuManager.greetPlayer(playerName);

        boolean continueRunning = true;
        while (continueRunning) {
            menuManager.displayMainMenu();
            int menuChoice = menuManager.getMenuChoice();
            continueRunning = handleMenuChoice(menuChoice);
        }
    }

    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case GameConstants.MENU_QUIT:
                handleQuit();
                return false;
            case GameConstants.MENU_PLAY:
                handlePlay();
                break;
            case GameConstants.MENU_HIGH_SCORES:
                handleHighScores();
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

    private void handlePlay() {
        menuManager.displayDifficultyMenu();
        int difficulty = menuManager.getDifficultyChoice();
        gameEngine.initializeGame(difficulty);
        gameEngine.startGameSession();
    }

    private void handleHighScores() {
        scoreManager.displayHighScores();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}