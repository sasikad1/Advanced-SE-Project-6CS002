package base;

import java.awt.Graphics;
import java.util.Collections;

public class GameEngine {
    private GameState gameState;
    private DominoManager dominoManager;
    private GridManager gridManager;
    private ScoreManager scoreManager;
    private CheatSystem cheatSystem;
    private PictureFrame pictureFrame;
    private IOSpecialist io;

    public GameEngine(IOSpecialist io) {
        this.io = io;
        this.gameState = new GameState();
        this.dominoManager = new DominoManager();
        this.gridManager = new GridManager();
        this.scoreManager = new ScoreManager();
        this.cheatSystem = new CheatSystem(this, io);
        this.pictureFrame = new PictureFrame();
    }

    public void initializeGame(int difficulty) {
        gameState.resetGame();

        dominoManager.generateDominoes();
        dominoManager.generateGuesses();

        // Setup based on difficulty
        switch (difficulty) {
            case GameConstants.DIFFICULTY_EASY:
                setupEasyDifficulty();
                break;
            case GameConstants.DIFFICULTY_MEDIUM:
                setupMediumDifficulty();
                break;
            case GameConstants.DIFFICULTY_HARD:
                setupHardDifficulty();
                break;
        }

        prepareGameState();
    }

    private void setupEasyDifficulty() {
        dominoManager.placeDominoesInGrid();
        gridManager.collateGrid(dominoManager.getDominoes());
    }

    private void setupMediumDifficulty() {
        dominoManager.placeDominoesInGrid();
        dominoManager.rotateDominoes();
        gridManager.collateGrid(dominoManager.getDominoes());
    }

    private void setupHardDifficulty() {
        dominoManager.placeDominoesInGrid();
        dominoManager.rotateDominoes();
        dominoManager.rotateDominoes();
        dominoManager.rotateDominoes();
        dominoManager.invertSomeDominoes();
        gridManager.collateGrid(dominoManager.getDominoes());
    }

    private void prepareGameState() {
        gameState.setDominoes(dominoManager.getDominoes());
        gameState.setGuesses(dominoManager.getGuesses());
        gameState.setGrid(gridManager.getGrid());
        gameState.setGuessGrid(gridManager.getGuessGrid());
        gameState.setMode(1);
        gameState.setStartTime(System.currentTimeMillis());

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
            case GameConstants.PLAY_PRINT_GRID:
                gridManager.printMainGrid();
                break;
            case GameConstants.PLAY_PRINT_BOX:
                gridManager.printGuessGrid();
                break;
            case GameConstants.PLAY_PRINT_DOMINOES:
                printGuesses();
                break;
            case GameConstants.PLAY_PLACE_DOMINO:
                handlePlaceDomino();
                break;
            case GameConstants.PLAY_UNPLACE_DOMINO:
                handleUnplaceDomino();
                break;
            case GameConstants.PLAY_ASSISTANCE:
                cheatSystem.handleCheatMenu();
                break;
            case GameConstants.PLAY_CHECK_SCORE:
                scoreManager.displayScore(gameState.getPlayerName(), gameState.getScore());
                break;
            default:
                System.out.println("Invalid choice");
        }
        return true;
    }

    private void handlePlaceDomino() {
        System.out.println("Where will the top left of the domino be?");

        int[] coords = InputValidator.getValidatedCoordinates(io);
        int x = coords[0];
        int y = coords[1];

        boolean horizontal = InputValidator.getYesNoResponse(io, "Horizontal or Vertical (H or V)?");
        int x2 = horizontal ? x + GameConstants.DOMINO_WIDTH : x;
        int y2 = horizontal ? y : y + GameConstants.DOMINO_HEIGHT;

        if (!gridManager.canPlaceDomino(x, y, x2, y2)) {
            System.out.println("Problems placing the domino with that position and direction");
            return;
        }

        int value1 = gridManager.getGridValue(x, y, false);
        int value2 = gridManager.getGridValue(x2, y2, false);

        Domino domino = dominoManager.findDominoByValues(value1, value2, true);
        if (domino == null) {
            System.out.println("There is no such domino");
            return;
        }

        if (domino.placed) {
            System.out.println("That domino has already been placed:");
            System.out.println(domino);
            return;
        }

        // Place the domino
        if (value1 == domino.high && value2 == domino.low) {
            domino.place(x, y, x2, y2);
        } else {
            domino.place(x2, y2, x, y);
        }

        gridManager.placeDominoInGuessGrid(domino, x, y, x2, y2);
        gameState.addToScore(GameConstants.SCORE_CORRECT);

        updateGUI();
    }

    private void handleUnplaceDomino() {
        System.out.println("Enter a position that the domino occupies");
        int[] coords = InputValidator.getValidatedCoordinates(io);

        Domino domino = dominoManager.findDominoAt(coords[0], coords[1], true);
        if (domino == null) {
            System.out.println("Couldn't find a domino there");
            return;
        }

        domino.placed = false;
        gridManager.removeDominoFromGuessGrid(domino);
        gameState.addToScore(GameConstants.SCORE_WRONG);

        updateGUI();
    }

    private void endGameSession() {
        gameState.setMode(0);
        gridManager.printMainGrid();

        // Calculate time bonus
        int timeBonus = scoreManager.calculateTimeBonus(gameState.getStartTime());
        gameState.addToScore(timeBonus);

        // Save score
        scoreManager.saveScore(gameState.getPlayerName(), gameState.getScore());

        // Display solution
        System.out.println("Here is the solution:");
        System.out.println();
        printDominoes();

        System.out.println("you scored " + gameState.getScore());
    }

    // Public methods for external access
    public GameState getGameState() {
        return gameState;
    }

    public Domino findDominoAt(int x, int y, boolean fromGuesses) {
        return dominoManager.findDominoAt(x, y, fromGuesses);
    }

    public Domino findDominoByValues(int value1, int value2, boolean fromGuesses) {
        return dominoManager.findDominoByValues(value1, value2, fromGuesses);
    }

    public void setPlayerName(String playerName) {
        gameState.setPlayerName(playerName);
    }

    // GUI methods
    private void initializeGUI() {
        pictureFrame.PictureFrame(this);  // Now passes GameEngine instead of Main
        updateGUI();
    }

    private void updateGUI() {
        if (pictureFrame.dp != null) {
            pictureFrame.dp.repaint();
        }
    }

    public void drawDominoes(Graphics g) {
        if (gameState.getDominoes() != null) {
            for (Domino d : gameState.getDominoes()) {
                pictureFrame.dp.drawDomino(g, d);
            }
        }
    }

    public void drawGuesses(Graphics g) {
        if (gameState.getGuesses() != null) {
            for (Domino d : gameState.getGuesses()) {
                pictureFrame.dp.drawDomino(g, d);
            }
        }
    }

    // Display methods
    private void printGuesses() {
        if (gameState.getGuesses() != null) {
            Collections.sort(gameState.getGuesses());
            for (Domino d : gameState.getGuesses()) {
                System.out.println(d);
            }
        }
    }

    private void printDominoes() {
        if (gameState.getDominoes() != null) {
            Collections.sort(gameState.getDominoes());
            for (Domino d : gameState.getDominoes()) {
                System.out.println(d);
            }
        }
    }
}