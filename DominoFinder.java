package base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DominoFinder {

    public static Domino findDominoAt(List<Domino> dominoes, int xPosition, int yPosition) {
        for (Domino d : dominoes) {
            if ((d.lowValueX == xPosition && d.lowValueY == yPosition) || (d.highValueX == xPosition && d.highValueY == yPosition)) {
                return d;
            }
        }
        return null;
    }

    public static Domino findDominoByValues(List<Domino> dominoes, int value1, int value2) {
        for (Domino domino : dominoes) {
            if ((domino.lowValue == value1 && domino.highValue == value2) ||
                    (domino.highValue == value1 && domino.lowValue == value2)) {
                return domino;
            }
        }
        return null;
    }

    public static Map<Domino, List<Location>> createLocationMap(List<Domino> guessDominoes,
                                                                int[][] grid) {
        Map<Domino, List<Location>> map = new HashMap<>();

        for (int row = 0; row < GameConstants.GRID_LAST_ROW; row++) {
            for (int column = 0; column < GameConstants.GRID_LAST_COL; column++) {
                Domino horizontalDomino = findDominoByValues(guessDominoes, grid[row][column], grid[row][column + 1]);
                Domino verticalDomino = findDominoByValues(guessDominoes, grid[row][column], grid[row + 1][column]);

                addToMap(map, horizontalDomino, new Location(row, column, Location.DIRECTION.HORIZONTAL));
                addToMap(map, verticalDomino, new Location(row, column, Location.DIRECTION.VERTICAL));
            }
        }
        return map;
    }

    private static void addToMap(Map<Domino, List<Location>> map, Domino domino, Location location) {
        if (domino == null) return;

        List<Location> locations = map.get(domino);
        if (locations == null) {
            locations = new LinkedList<>();
            map.put(domino, locations);
        }
        locations.add(location);
    }
}