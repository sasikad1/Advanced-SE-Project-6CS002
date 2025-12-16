package base;

import java.util.List;

public class GridManager {
    private int[][] grid;
    private int[][] guessGrid;

    public GridManager() {
        grid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLUMNS];
        guessGrid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLUMNS];
        initializeGrids();
    }

    private void initializeGrids() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLUMNS; c++) {
                grid[r][c] = GameConstants.EMPTY_CELL;
                guessGrid[r][c] = GameConstants.EMPTY_CELL;
            }
        }
    }

    public void collateGrid(List<Domino> dominoes) {
        initializeGrid();
        for (Domino domino : dominoes) {
            if (domino.placed) {
                grid[domino.highValueY][domino.highValueX] = domino.highValue;
                grid[domino.lowValueY][domino.lowValueX] = domino.lowValue;
            }
        }
    }

    public void collateGuessGrid(List<Domino> guesses) {
        initializeGuessGrid();
        for (Domino d : guesses) {
            if (d.placed) {
                guessGrid[d.highValueY][d.highValueX] = d.highValue;
                guessGrid[d.lowValueY][d.lowValueX] = d.lowValue;
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
        return x >= 0 && x < GameConstants.GRID_COLUMNS &&
                y >= 0 && y < GameConstants.GRID_ROWS;
    }

    public boolean isPositionEmpty(int x, int y, boolean inGuessGrid) {
        int[][] targetGrid = inGuessGrid ? guessGrid : grid;
        return targetGrid[y][x] == GameConstants.EMPTY_CELL;
    }

    public boolean canPlaceDomino(int xPosition1, int yPosition1, int xPosition2, int yPosition2) {
        return isValidPosition(xPosition1, yPosition1) && isValidPosition(xPosition2, yPosition2) &&
                isPositionEmpty(xPosition1, yPosition1, true) && isPositionEmpty(xPosition2, yPosition2, true);
    }

    public void placeDominoInGuessGrid(Domino domino, int xPosition1, int yPosition1, int xPosition2, int yPosition2) {
        guessGrid[yPosition1][xPosition1] = domino.highValue;
        guessGrid[yPosition2][xPosition2] = domino.lowValue;
    }

    public void removeDominoFromGuessGrid(Domino domino) {
        guessGrid[domino.highValueY][domino.highValueX] = GameConstants.EMPTY_CELL;
        guessGrid[domino.lowValueY][domino.lowValueX] = GameConstants.EMPTY_CELL;
    }

    public int getGridValue(int x, int y, boolean fromGuessGrid) {
        int[][] targetGrid = fromGuessGrid ? guessGrid : grid;
        return targetGrid[y][x];
    }

    public int[][] getGrid() { return grid; }
    public int[][] getGuessGrid() { return guessGrid; }

    private void initializeGrid() {
        for (int row = 0; row < GameConstants.GRID_ROWS; row++) {
            for (int column = 0; column < GameConstants.GRID_COLUMNS; column++) {
                grid[row][column] = GameConstants.EMPTY_CELL;
            }
        }
    }

    private void initializeGuessGrid() {
        for (int row = 0; row < GameConstants.GRID_ROWS; row++) {
            for (int column = 0; column < GameConstants.GRID_COLUMNS; column++) {
                guessGrid[row][column] = GameConstants.EMPTY_CELL;
            }
        }
    }
}