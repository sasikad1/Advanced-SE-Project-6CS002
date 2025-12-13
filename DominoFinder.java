package base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DominoFinder {

    public static Domino findDominoAt(List<Domino> dominoes, int x, int y) {
        for (Domino d : dominoes) {
            if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
                return d;
            }
        }
        return null;
    }

    public static Domino findDominoByValues(List<Domino> dominoes, int value1, int value2) {
        for (Domino d : dominoes) {
            if ((d.low == value1 && d.high == value2) ||
                    (d.high == value1 && d.low == value2)) {
                return d;
            }
        }
        return null;
    }

    public static Map<Domino, List<Location>> createLocationMap(List<Domino> guessDominoes,
                                                                int[][] grid) {
        Map<Domino, List<Location>> map = new HashMap<>();

        for (int r = 0; r < GameConstants.GRID_LAST_ROW; r++) {
            for (int c = 0; c < GameConstants.GRID_LAST_COL; c++) {
                Domino hd = findDominoByValues(guessDominoes, grid[r][c], grid[r][c + 1]);
                Domino vd = findDominoByValues(guessDominoes, grid[r][c], grid[r + 1][c]);

                addToMap(map, hd, new Location(r, c, Location.DIRECTION.HORIZONTAL));
                addToMap(map, vd, new Location(r, c, Location.DIRECTION.VERTICAL));
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