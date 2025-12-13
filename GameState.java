package base;

import java.util.List;

public class GameState {
    private String playerName;
    private int score;
    private long startTime;
    private int mode;
    private int cheatFlag;
    private int[][] grid;
    private int[][] guessGrid;
    private List<Domino> dominoes;
    private List<Domino> guesses;

    public GameState() {
        this.grid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
        this.guessGrid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
        this.score = 0;
        this.mode = -1;
        this.cheatFlag = 0;
    }

    // Getters and Setters
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public void addToScore(int points) { this.score += points; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public int getMode() { return mode; }
    public void setMode(int mode) { this.mode = mode; }

    public int getCheatFlag() { return cheatFlag; }
    public void setCheatFlag(int cheatFlag) { this.cheatFlag = cheatFlag; }
    public void incrementCheatFlag() { this.cheatFlag++; }

    public int[][] getGrid() { return grid; }
    public void setGrid(int[][] grid) { this.grid = grid; }

    public int[][] getGuessGrid() { return guessGrid; }
    public void setGuessGrid(int[][] guessGrid) { this.guessGrid = guessGrid; }

    public List<Domino> getDominoes() { return dominoes; }
    public void setDominoes(List<Domino> dominoes) { this.dominoes = dominoes; }

    public List<Domino> getGuesses() { return guesses; }
    public void setGuesses(List<Domino> guesses) { this.guesses = guesses; }

    // Helper methods
    public void initializeGrids() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLS; c++) {
                grid[r][c] = GameConstants.EMPTY_CELL;
                guessGrid[r][c] = GameConstants.EMPTY_CELL;
            }
        }
    }

    public void resetGame() {
        score = 0;
        cheatFlag = 0;
        mode = -1;
        initializeGrids();
    }
}