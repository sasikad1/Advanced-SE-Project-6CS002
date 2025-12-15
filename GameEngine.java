package base;

public class GameEngine {
    private GameState gameState;
    private GameInitializer gameInitializer;    // NEW: Initialization logic
    private DominoPlacer dominoPlacer;          // NEW: Placement logic
    private CheatSystem cheatSystem;
    private PictureFrame pictureFrame;
    private GridManager gridManager;            // NEW: Added for completeness
    private DominoManager dominoManager;        // NEW: Added for completeness
    private IOSpecialist io;

    public GameEngine(IOSpecialist io) {
        this.io = io;
        this.gameState = new GameState();
        this.gameInitializer = new GameInitializer();
        this.gridManager = new GridManager();
        this.dominoManager = new DominoManager();
        this.dominoPlacer = new DominoPlacer(gameState, gridManager);
        this.cheatSystem = new CheatSystem(this, io);
        this.pictureFrame = new PictureFrame();
    }

    // Method ගණන අඩු කරලා
    public void initializeGame(int difficulty) {
        gameInitializer.initializeGame(difficulty, gameState);
        initializeGUI();
    }

    public void startGameSession() {
        MenuManager menuManager = new MenuManager(io);
        boolean playing = true;

        while (playing) {
            menuManager.displayPlayMenu(gameState.getPlayerName());
            int choice = menuManager.getPlayMenuChoice();
            playing = handlePlayMenuChoice(choice);
        }

        endGameSession();
    }

    private boolean handlePlayMenuChoice(int choice) {
        switch (choice) {
            case GameConstants.PLAY_GIVE_UP:
                return false;
            case GameConstants.PLAY_PLACE_DOMINO:
                return handlePlaceDomino();
            case GameConstants.PLAY_UNPLACE_DOMINO:
                return handleUnplaceDomino();
            case GameConstants.PLAY_ASSISTANCE:
                cheatSystem.handleCheatMenu();
                break;
            default:
                handleDisplayChoice(choice);
        }
        return true;
    }

    private boolean handlePlaceDomino() {
        System.out.println("Where will the top left of the domino be?");
        int[] coords = InputValidator.getValidatedCoordinates(io);
        boolean horizontal = InputValidator.getYesNoResponse(io, "Horizontal or Vertical (H or V)?");

        Domino domino = getSelectedDomino(coords, horizontal);
        if (domino == null) {
            System.out.println("Invalid domino selection");
            return true;
        }

        boolean placed = dominoPlacer.placeDomino(coords[0], coords[1], horizontal, domino);
        if (!placed) {
            System.out.println("Problems placing the domino");
        }

        updateGUI();
        return true;
    }

    private boolean handleUnplaceDomino() {
        System.out.println("Enter a position that the domino occupies");
        int[] coords = InputValidator.getValidatedCoordinates(io);

        boolean unplaced = dominoPlacer.unplaceDomino(coords[0], coords[1]);
        if (!unplaced) {
            System.out.println("Couldn't find a domino there");
        }

        updateGUI();
        return true;
    }

    private void handleDisplayChoice(int choice) {
        switch (choice) {
            case GameConstants.PLAY_PRINT_GRID:
                gridManager.printMainGrid();
                break;
            case GameConstants.PLAY_PRINT_BOX:
                gridManager.printGuessGrid();
                break;
            case GameConstants.PLAY_PRINT_DOMINOES:
                printGuesses();
                break;
            case GameConstants.PLAY_CHECK_SCORE:
                new ScoreManager().displayScore(gameState.getPlayerName(), gameState.getScore());
                break;
        }
    }

    private Domino getSelectedDomino(int[] coords, boolean horizontal) {
        int x = coords[0];
        int y = coords[1];
        int x2 = horizontal ? x + 1 : x;
        int y2 = horizontal ? y : y + 1;

        int value1 = gridManager.getGridValue(x, y, false);
        int value2 = gridManager.getGridValue(x2, y2, false);

        return dominoManager.findDominoByValues(value1, value2, true);
    }

    private void endGameSession() {
        gameState.setMode(0);
        gridManager.printMainGrid();

        int timeBonus = new ScoreManager().calculateTimeBonus(gameState.getStartTime());
        gameState.addToScore(timeBonus);
        new ScoreManager().saveScore(gameState.getPlayerName(), gameState.getScore());

        System.out.println("Here is the solution:");
        printDominoes();
        System.out.println("you scored " + gameState.getScore());
    }

    // Helper methods for displaying
    private void printGuesses() {
        if (gameState.getGuesses() != null) {
            java.util.Collections.sort(gameState.getGuesses());
            for (Domino d : gameState.getGuesses()) {
                System.out.println(d);
            }
        }
    }

    private void printDominoes() {
        if (gameState.getDominoes() != null) {
            java.util.Collections.sort(gameState.getDominoes());
            for (Domino d : gameState.getDominoes()) {
                System.out.println(d);
            }
        }
    }

    // GUI methods
    private void initializeGUI() {
        pictureFrame.PictureFrame(this);
        updateGUI();
    }

    private void updateGUI() {
        if (pictureFrame.dp != null) {
            pictureFrame.dp.repaint();
        }
    }

    public void drawDominoes(java.awt.Graphics g) {
        if (gameState.getDominoes() != null) {
            for (Domino d : gameState.getDominoes()) {
                pictureFrame.dp.drawDomino(g, d);
            }
        }
    }

    public void drawGuesses(java.awt.Graphics g) {
        if (gameState.getGuesses() != null) {
            for (Domino d : gameState.getGuesses()) {
                pictureFrame.dp.drawDomino(g, d);
            }
        }
    }

    // Getters for external access
    public GameState getGameState() { return gameState; }

    public Domino findDominoAt(int x, int y, boolean fromGuesses) {
        return fromGuesses ?
                dominoManager.findDominoAt(x, y, true) :
                dominoManager.findDominoAt(x, y, false);
    }

    public Domino findDominoByValues(int value1, int value2, boolean fromGuesses) {
        return fromGuesses ?
                dominoManager.findDominoByValues(value1, value2, true) :
                dominoManager.findDominoByValues(value1, value2, false);
    }

    public void setPlayerName(String playerName) {
        gameState.setPlayerName(playerName);
    }
}