package base;

public class GameInitializer {
    private DominoManager dominoManager;
    private GridManager gridManager;

    public GameInitializer() {
        this.dominoManager = new DominoManager();
        this.gridManager = new GridManager();
    }

    public void initializeGame(int difficulty, GameState gameState) {
        resetGameState(gameState);
        setupDominoes(difficulty);
        updateGameState(gameState);
    }

    private void resetGameState(GameState gameState) {
        gameState.resetGame();
        dominoManager.generateDominoes();
        dominoManager.generateGuesses();
    }

    private void setupDominoes(int difficulty) {
        dominoManager.placeDominoesInGrid();

        switch (difficulty) {
            case GameConstants.DIFFICULTY_MEDIUM:
                dominoManager.rotateDominoes();
                break;
            case GameConstants.DIFFICULTY_HARD:
                dominoManager.rotateDominoes();
                dominoManager.rotateDominoes();
                dominoManager.rotateDominoes();
                dominoManager.invertSomeDominoes();
                break;
        }

        gridManager.collateGrid(dominoManager.getDominoes());
    }

    private void updateGameState(GameState gameState) {
        gameState.setDominoes(dominoManager.getDominoes());
        gameState.setGuesses(dominoManager.getGuesses());
        gameState.setGrid(gridManager.getGrid());
        gameState.setGuessGrid(gridManager.getGuessGrid());
        gameState.setStartTime(System.currentTimeMillis());
        gameState.setMode(1);
    }
}