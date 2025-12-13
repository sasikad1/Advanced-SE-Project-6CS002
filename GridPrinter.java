package base;

public class GridPrinter {

    public static void printGrid(int[][] grid) {
        printGrid(grid, GameConstants.EMPTY_CELL);
    }

    public static void printGrid(int[][] grid, int emptyValue) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != emptyValue) {
                    System.out.printf("%d", grid[row][col]);
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}