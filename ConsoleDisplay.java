// ConsoleDisplay.java
package base;

public class ConsoleDisplay implements Observer {
    @Override
    public void update(GameState gameState) {
        System.out.println("\n=== Game State Updated ===");
        System.out.println("Player: " + gameState.getPlayerName());
        System.out.println("Score: " + gameState.getScore());
        System.out.println("Cheat Flag: " + gameState.getCheatFlag());
        System.out.println("=========================\n");
    }
}