package base;

import java.util.List;

public class GridManager {
    private int[][] grid;
    private int[][] guessGrid;

    public GridManager() {
        grid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
        guessGrid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
        initializeGrids();
    }

    private void initializeGrids() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLS; c++) {
                grid[r][c] = GameConstants.EMPTY_CELL;
                guessGrid[r][c] = GameConstants.EMPTY_CELL;
            }
        }
    }

    public void collateGrid(List<Domino> dominoes) {
        initializeGrid();
        for (Domino d : dominoes) {
            if (d.placed) {
                grid[d.hy][d.hx] = d.high;
                grid[d.ly][d.lx] = d.low;
            }
        }
    }

    public void collateGuessGrid(List<Domino> guesses) {
        initializeGuessGrid();
        for (Domino d : guesses) {
            if (d.placed) {
                guessGrid[d.hy][d.hx] = d.high;
                guessGrid[d.ly][d.lx] = d.low;
            }
        }
    }

    public void printGrid(int[][] gridToPrint) {
        GridPrinter.printGrid(gridToPrint);
    }

    public void printMainGrid() {
        printGrid(grid);
    }

    public void printGuessGrid() {
        printGrid(guessGrid);
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < GameConstants.GRID_COLS &&
                y >= 0 && y < GameConstants.GRID_ROWS;
    }

    public boolean isPositionEmpty(int x, int y, boolean inGuessGrid) {
        int[][] targetGrid = inGuessGrid ? guessGrid : grid;
        return targetGrid[y][x] == GameConstants.EMPTY_CELL;
    }

    public boolean canPlaceDomino(int x1, int y1, int x2, int y2) {
        return isValidPosition(x1, y1) && isValidPosition(x2, y2) &&
                isPositionEmpty(x1, y1, true) && isPositionEmpty(x2, y2, true);
    }

    public void placeDominoInGuessGrid(Domino domino, int x1, int y1, int x2, int y2) {
        guessGrid[y1][x1] = domino.high;
        guessGrid[y2][x2] = domino.low;
    }

    public void removeDominoFromGuessGrid(Domino domino) {
        guessGrid[domino.hy][domino.hx] = GameConstants.EMPTY_CELL;
        guessGrid[domino.ly][domino.lx] = GameConstants.EMPTY_CELL;
    }

    public int getGridValue(int x, int y, boolean fromGuessGrid) {
        int[][] targetGrid = fromGuessGrid ? guessGrid : grid;
        return targetGrid[y][x];
    }

    public int[][] getGrid() { return grid; }
    public int[][] getGuessGrid() { return guessGrid; }

    private void initializeGrid() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLS; c++) {
                grid[r][c] = GameConstants.EMPTY_CELL;
            }
        }
    }

    private void initializeGuessGrid() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLS; c++) {
                guessGrid[r][c] = GameConstants.EMPTY_CELL;
            }
        }
    }
}