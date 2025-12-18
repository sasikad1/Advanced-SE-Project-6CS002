// ScoreDisplay.java
package base;

public class ScoreDisplay implements Observer {
    @Override
    public void update(GameState gameState) {
        // Update score display (could be GUI component)
        System.out.println("Score updated: " + gameState.getScore());
    }
}