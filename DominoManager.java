package base;

import java.util.LinkedList;
import java.util.List;

public class DominoManager {
    private List<Domino> dominoes;
    private List<Domino> guesses;

    public DominoManager() {
        dominoes = new LinkedList<>();
        guesses = new LinkedList<>();
    }

    public void generateDominoes() {
        dominoes.clear();
        int count = 0;
        int x = 0;
        int y = 0;

        for (int low = 0; low <= GameConstants.MAX_DOMINO_VALUE; low++) {
            for (int high = low; high <= GameConstants.MAX_DOMINO_VALUE; high++) {
                Domino d = new Domino(high, low);
                dominoes.add(d);
                d.place(x, y, x + 1, y);
                count++;
                x += 2;
                if (x > GameConstants.GRID_LAST_ROW) {
                    x = 0;
                    y++;
                }
            }
        }

        if (count != GameConstants.DOMINO_COUNT) {
            throw new IllegalStateException("Failed to generate dominoes correctly");
        }
    }

    public void generateGuesses() {
        guesses.clear();

        for (int low = 0; low <= GameConstants.MAX_DOMINO_VALUE; low++) {
            for (int high = low; high <= GameConstants.MAX_DOMINO_VALUE; high++) {
                Domino d = new Domino(high, low);
                guesses.add(d);
            }
        }
    }

    public void shuffleDominoes() {
        List<Domino> shuffled = new LinkedList<>();
        while (!dominoes.isEmpty()) {
            int index = (int) (Math.random() * dominoes.size());
            shuffled.add(dominoes.get(index));
            dominoes.remove(index);
        }
        dominoes = shuffled;
    }

    public void invertSomeDominoes() {
        for (Domino d : dominoes) {
            if (Math.random() > 0.5) {
                d.invert();
            }
        }
    }

    public void placeDominoesInGrid() {
        int x = 0;
        int y = 0;
        for (Domino d : dominoes) {
            d.place(x, y, x + 1, y);
            x += 2;
            if (x > GameConstants.GRID_LAST_ROW) {
                x = 0;
                y++;
            }
        }
    }

    public Domino findDominoAt(int x, int y, boolean fromGuesses) {
        List<Domino> searchList = fromGuesses ? guesses : dominoes;
        for (Domino d : searchList) {
            if ((d.lx == x && d.ly == y) || (d.hx == x && d.hy == y)) {
                return d;
            }
        }
        return null;
    }

    public Domino findDominoByValues(int value1, int value2, boolean fromGuesses) {
        List<Domino> searchList = fromGuesses ? guesses : dominoes;
        for (Domino d : searchList) {
            if ((d.low == value1 && d.high == value2) ||
                    (d.high == value1 && d.low == value2)) {
                return d;
            }
        }
        return null;
    }

    public List<Domino> getDominoes() { return dominoes; }
    public List<Domino> getGuesses() { return guesses; }

    public void rotateDominoes() {
        for (int x = 0; x < GameConstants.GRID_ROWS; x++) {
            for (int y = 0; y < GameConstants.GRID_LAST_ROW; y++) {
                tryToRotateDominoAt(x, y);
            }
        }
    }

    private void tryToRotateDominoAt(int x, int y) {
        Domino d = findDominoAt(x, y, false);
        if (d != null && isTopLeftOfDomino(x, y, d)) {
            if (d.ishl()) {
                if (Math.random() < 0.5 && isCellBelowTopLeftOfHorizontalDomino(x, y)) {
                    Domino e = findDominoAt(x, y + 1, false);
                    if (e != null) {
                        e.hx = x; e.lx = x;
                        d.hx = x + 1; d.lx = x + 1;
                        e.ly = y + 1; e.hy = y;
                        d.ly = y + 1; d.hy = y;
                    }
                }
            } else {
                if (Math.random() < 0.5 && isCellToRightTopLeftOfVerticalDomino(x, y)) {
                    Domino e = findDominoAt(x + 1, y, false);
                    if (e != null) {
                        e.hx = x; e.lx = x + 1;
                        d.hx = x; d.lx = x + 1;
                        e.ly = y + 1; e.hy = y + 1;
                        d.ly = y; d.hy = y;
                    }
                }
            }
        }
    }

    private boolean isTopLeftOfDomino(int x, int y, Domino d) {
        return (x == Math.min(d.lx, d.hx)) && (y == Math.min(d.ly, d.hy));
    }

    private boolean isCellToRightTopLeftOfVerticalDomino(int x, int y) {
        Domino e = findDominoAt(x + 1, y, false);
        return e != null && isTopLeftOfDomino(x + 1, y, e) && !e.ishl();
    }

    private boolean isCellBelowTopLeftOfHorizontalDomino(int x, int y) {
        Domino e = findDominoAt(x, y + 1, false);
        return e != null && isTopLeftOfDomino(x, y + 1, e) && e.ishl();
    }
}