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
                Domino domino = new Domino(high, low);
                dominoes.add(domino);
                domino.place(x, y, x + 1, y);
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
            if ((d.lowValueX == x && d.lowValueY == y) || (d.highValueX == x && d.highValueY == y)) {
                return d;
            }
        }
        return null;
    }

    public Domino findDominoByValues(int value1, int value2, boolean fromGuesses) {
        List<Domino> searchList = fromGuesses ? guesses : dominoes;
        for (Domino d : searchList) {
            if ((d.lowValue == value1 && d.highValue == value2) ||
                    (d.highValue == value1 && d.lowValue == value2)) {
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

    private void tryToRotateDominoAt(int currentX, int currentY) {
        Domino d = findDominoAt(currentX, currentY, false);
        if (d != null && isTopLeftOfDomino(currentX, currentY, d)) {
            if (d.isHorizontal()) {
                if (Math.random() < 0.5 && isCellBelowTopLeftOfHorizontalDomino(currentX, currentY)) {
                    Domino adjacentDomino = findDominoAt(currentX, currentY + 1, false);
                    if (adjacentDomino != null) {
                        adjacentDomino.highValueX = currentX; adjacentDomino.lowValueX = currentX;
                        d.highValueX = currentX + 1; d.lowValueX = currentX + 1;
                        adjacentDomino.lowValueY = currentY + 1; adjacentDomino.highValueY = currentY;
                        d.lowValueY = currentY + 1; d.highValueY = currentY;
                    }
                }
            } else {
                if (Math.random() < 0.5 && isCellToRightTopLeftOfVerticalDomino(currentX, currentY)) {
                    Domino e = findDominoAt(currentX + 1, currentY, false);
                    if (e != null) {
                        e.highValueX = currentX; e.lowValueX = currentX + 1;
                        d.highValueX = currentX; d.lowValueX = currentX + 1;
                        e.lowValueY = currentY + 1; e.highValueY = currentY + 1;
                        d.lowValueY = currentY; d.highValueY = currentY;
                    }
                }
            }
        }
    }

    private boolean isTopLeftOfDomino(int currentX, int currentY, Domino domino) {
        return (currentX == Math.min(domino.lowValueX, domino.highValueX)) && (currentY == Math.min(domino.lowValueY, domino.highValueY));
    }

    private boolean isCellToRightTopLeftOfVerticalDomino(int currentX, int currentY) {
        Domino e = findDominoAt(currentX + 1, currentY, false);
        return e != null && isTopLeftOfDomino(currentX + 1, currentY, e) && !e.isHorizontal();
    }

    private boolean isCellBelowTopLeftOfHorizontalDomino(int currentX, int currentY) {
        Domino e = findDominoAt(currentX, currentY + 1, false);
        return e != null && isTopLeftOfDomino(currentX, currentY + 1, e) && e.isHorizontal();
    }
}