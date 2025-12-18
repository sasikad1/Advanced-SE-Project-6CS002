// GameLogger.java
package base;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogger implements Observer {
    private static final String LOG_FILE = "game_log.txt";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void update(GameState gameState) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true));
            String timestamp = dateFormat.format(new Date());
            writer.printf("[%s] Player: %s, Score: %d, Mode: %d%n",
                    timestamp,
                    gameState.getPlayerName(),
                    gameState.getScore(),
                    gameState.getMode());
            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to log file");
        }
    }
}