package base;

public class DominoPlacer {
    private GameState gameState;
    private GridManager gridManager;

    public DominoPlacer(GameState gameState, GridManager gridManager) {
        this.gameState = gameState;
        this.gridManager = gridManager;
    }

    public boolean placeDomino(int startX, int startY, boolean horizontal, Domino domino) {
        if (!isValidPlacement(startX, startY, horizontal)) {
            return false;
        }

        Position position = calculatePosition(startX, startY, horizontal);
        domino.place(position.x1, position.y1, position.x2, position.y2);
        updateGameState(domino, position);
        return true;
    }

    public boolean unplaceDomino(int x, int y) {
        Domino domino = findDominoAt(x, y);
        if (domino == null || !domino.placed) {
            return false;
        }

        domino.placed = false;
        updateScore(GameConstants.SCORE_WRONG);
        return true;
    }

    private Position calculatePosition(int x1, int y1, boolean horizontal) {
        if (horizontal) {
            return new Position(x1, y1, x1 + 1, y1);
        } else {
            return new Position(x1, y1, x1, y1 + 1);
        }
    }

    private boolean isValidPlacement(int x1, int y1, boolean horizontal) {
        int x2 = horizontal ? x1 + 1 : x1;
        int y2 = horizontal ? y1 : y1 + 1;

        return gridManager.canPlaceDomino(x1, y1, x2, y2);
    }

    private void updateGameState(Domino domino, Position position) {
        gridManager.placeDominoInGuessGrid(domino, position.x1, position.y1, position.x2, position.y2);
        updateScore(GameConstants.SCORE_CORRECT);
    }

    private void updateScore(int points) {
        gameState.addToScore(points);
    }

    private Domino findDominoAt(int x, int y) {
        return gameState.getGuesses().stream()
                .filter(domino -> (domino.lowValueX == x && domino.lowValueY == y) || (domino.highValueX == x && domino.highValueY == y))
                .findFirst()
                .orElse(null);
    }

    private static class Position {
        int x1, y1, x2, y2;

        Position(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
}