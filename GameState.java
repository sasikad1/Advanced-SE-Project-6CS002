package base;

import java.util.List;
import java.util.ArrayList;

public class GameState {
    private String playerName;
    private int score;
    private long startTime;
    private int mode;
    private int cheatFlag;
    private List<Domino> dominoes;
    private List<Domino> guesses;
    private int[][] grid;
    private int[][] guessGrid;

    // Constructor
    public GameState() {
        this.playerName = "";
        this.score = 0;
        this.startTime = 0;
        this.mode = -1;
        this.cheatFlag = 0;
        this.dominoes = new ArrayList<>();
        this.guesses = new ArrayList<>();
        this.grid = new int[7][8];
        this.guessGrid = new int[7][8];
        initializeGrids();
    }

    // Initialize grids with empty values
    private void initializeGrids() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = 9; // Empty cell value
                guessGrid[i][j] = 9;
            }
        }
    }

    // ============ GETTERS ============

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getMode() {
        return mode;
    }

    public int getCheatFlag() {
        return cheatFlag;
    }

    public List<Domino> getDominoes() {
        return new ArrayList<>(dominoes); // Return copy for encapsulation
    }

    public List<Domino> getGuesses() {
        return new ArrayList<>(guesses); // Return copy for encapsulation
    }

    public int[][] getGrid() {
        return copyGrid(grid);
    }

    public int[][] getGuessGrid() {
        return copyGrid(guessGrid);
    }

    public int getGridValue(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        return -1; // Invalid position
    }

    public int getGuessGridValue(int row, int col) {
        if (isValidPosition(row, col)) {
            return guessGrid[row][col];
        }
        return -1; // Invalid position
    }

    // ============ SETTERS ============

    public void setPlayerName(String playerName) {
        if (playerName != null && !playerName.trim().isEmpty()) {
            this.playerName = playerName.trim();
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void subtractScore(int points) {
        this.score -= points;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void setMode(int mode) {
        if (mode >= -1 && mode <= 1) {
            this.mode = mode;
        }
    }

    public void setCheatFlag(int cheatFlag) {
        this.cheatFlag = cheatFlag;
    }

    public void incrementCheatFlag() {
        this.cheatFlag++;
    }

    public void setDominoes(List<Domino> dominoes) {
        if (dominoes != null) {
            this.dominoes = new ArrayList<>(dominoes);
        }
    }

    public void setGuesses(List<Domino> guesses) {
        if (guesses != null) {
            this.guesses = new ArrayList<>(guesses);
        }
    }

    public void setGrid(int[][] grid) {
        if (grid != null && grid.length == 7 && grid[0].length == 8) {
            this.grid = copyGrid(grid);
        }
    }

    public void setGridValue(int row, int col, int value) {
        if (isValidPosition(row, col)) {
            grid[row][col] = value;
        }
    }

    public void setGuessGrid(int[][] guessGrid) {
        if (guessGrid != null && guessGrid.length == 7 && guessGrid[0].length == 8) {
            this.guessGrid = copyGrid(guessGrid);
        }
    }

    public void setGuessGridValue(int row, int col, int value) {
        if (isValidPosition(row, col)) {
            guessGrid[row][col] = value;
        }
    }

    // ============ HELPER METHODS ============

    /**
     * Check if a position is valid on the grid
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 7 && col >= 0 && col < 8;
    }

    /**
     * Reset the game state to initial values
     */
    public void reset() {
        this.score = 0;
        this.startTime = 0;
        this.mode = -1;
        this.cheatFlag = 0;
        this.dominoes.clear();
        this.guesses.clear();
        initializeGrids();
    }

    /**
     * Check if the game is in progress
     */
    public boolean isGameInProgress() {
        return mode == 1;
    }

    /**
     * Check if the game is showing solution
     */
    public boolean isShowingSolution() {
        return mode == 0;
    }

    /**
     * Get elapsed time since game started in milliseconds
     */
    public long getElapsedTime() {
        if (startTime == 0) {
            return 0;
        }
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Get elapsed time in seconds
     */
    public int getElapsedSeconds() {
        return (int) (getElapsedTime() / 1000);
    }

    /**
     * Calculate time bonus (max 60 seconds)
     */
    public int calculateTimeBonus() {
        int elapsedSeconds = getElapsedSeconds();
        return Math.max(0, 60 - elapsedSeconds);
    }

    /**
     * Calculate total score including time bonus
     */
    public int calculateTotalScore() {
        return score + calculateTimeBonus();
    }

    /**
     * Add a domino to the collection
     */
    public void addDomino(Domino domino) {
        if (domino != null) {
            dominoes.add(domino);
        }
    }

    /**
     * Add a guess domino
     */
    public void addGuess(Domino guess) {
        if (guess != null) {
            guesses.add(guess);
        }
    }

    /**
     * Find a domino by its coordinates
     */
    public Domino findDominoAt(int x, int y) {
        for (Domino domino : dominoes) {
            if ((domino.hx == x && domino.hy == y) ||
                    (domino.lx == x && domino.ly == y)) {
                return domino;
            }
        }
        return null;
    }

    /**
     * Find a guess by its coordinates
     */
    public Domino findGuessAt(int x, int y) {
        for (Domino guess : guesses) {
            if ((guess.hx == x && guess.hy == y) ||
                    (guess.lx == x && guess.ly == y)) {
                return guess;
            }
        }
        return null;
    }

    /**
     * Find domino by its values
     */
    public Domino findDominoByValues(int high, int low) {
        for (Domino domino : dominoes) {
            if ((domino.high == high && domino.low == low) ||
                    (domino.high == low && domino.low == high)) {
                return domino;
            }
        }
        return null;
    }

    /**
     * Find guess by its values
     */
    public Domino findGuessByValues(int high, int low) {
        for (Domino guess : guesses) {
            if ((guess.high == high && guess.low == low) ||
                    (guess.high == low && guess.low == high)) {
                return guess;
            }
        }
        return null;
    }

    /**
     * Check if a position is empty in the guess grid
     */
    public boolean isGuessPositionEmpty(int row, int col) {
        if (isValidPosition(row, col)) {
            return guessGrid[row][col] == 9;
        }
        return false;
    }

    /**
     * Check if all dominoes have been placed correctly
     */
    public boolean isGameComplete() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] != guessGrid[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the number of correctly placed dominoes
     */
    public int getCorrectDominoCount() {
        int count = 0;
        for (Domino guess : guesses) {
            if (guess.placed) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the percentage of game completion
     */
    public int getCompletionPercentage() {
        int totalCells = 7 * 8;
        int filledCells = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                if (guessGrid[i][j] != 9) {
                    filledCells++;
                }
            }
        }

        return (filledCells * 100) / totalCells;
    }

    /**
     * Create a deep copy of a grid
     */
    private int[][] copyGrid(int[][] source) {
        if (source == null) {
            return new int[7][8];
        }

        int[][] copy = new int[7][8];
        for (int i = 0; i < 7; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 8);
        }
        return copy;
    }

    /**
     * Create a snapshot of the current game state
     */
    public GameStateSnapshot createSnapshot() {
        return new GameStateSnapshot(
                playerName,
                score,
                mode,
                cheatFlag,
                copyGrid(grid),
                copyGrid(guessGrid)
        );
    }

    /**
     * Restore game state from a snapshot
     */
    public void restoreFromSnapshot(GameStateSnapshot snapshot) {
        if (snapshot != null) {
            this.playerName = snapshot.getPlayerName();
            this.score = snapshot.getScore();
            this.mode = snapshot.getMode();
            this.cheatFlag = snapshot.getCheatFlag();
            this.grid = snapshot.getGrid();
            this.guessGrid = snapshot.getGuessGrid();
        }
    }

    // ============ INNER CLASS FOR SNAPSHOT ============

    /**
     * Immutable snapshot of game state for undo/redo functionality
     */
    public static class GameStateSnapshot {
        private final String playerName;
        private final int score;
        private final int mode;
        private final int cheatFlag;
        private final int[][] grid;
        private final int[][] guessGrid;

        public GameStateSnapshot(String playerName, int score, int mode,
                                 int cheatFlag, int[][] grid, int[][] guessGrid) {
            this.playerName = playerName;
            this.score = score;
            this.mode = mode;
            this.cheatFlag = cheatFlag;
            this.grid = deepCopy(grid);
            this.guessGrid = deepCopy(guessGrid);
        }

        private int[][] deepCopy(int[][] source) {
            if (source == null) return new int[7][8];

            int[][] copy = new int[7][8];
            for (int i = 0; i < 7; i++) {
                System.arraycopy(source[i], 0, copy[i], 0, 8);
            }
            return copy;
        }

        // Getters
        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }
        public int getMode() { return mode; }
        public int getCheatFlag() { return cheatFlag; }
        public int[][] getGrid() { return deepCopy(grid); }
        public int[][] getGuessGrid() { return deepCopy(guessGrid); }
    }

    // ============ TO STRING METHOD ============

    @Override
    public String toString() {
        return String.format(
                "GameState{player='%s', score=%d, mode=%d, cheatFlag=%d, " +
                        "dominoes=%d, guesses=%d, completion=%d%%}",
                playerName, score, mode, cheatFlag,
                dominoes.size(), guesses.size(), getCompletionPercentage()
        );
    }
}