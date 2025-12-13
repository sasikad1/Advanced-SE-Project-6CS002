package base;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

public class ScoreManager {
    private static final String SCORE_FILE = "score.txt";

    public void saveScore(String playerName, int score) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE, true));
            String safeName = playerName.replaceAll(",", "_");
            pw.print(safeName);
            pw.print(",");
            pw.print(score);
            pw.print(",");
            pw.println(System.currentTimeMillis());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Something went wrong saving scores");
        }
    }

    public void displayHighScores() {
        System.out.println();
        printHeader("High Scores");

        File scoreFile = new File(SCORE_FILE);
        if (!scoreFile.exists() || !scoreFile.canRead()) {
            createDefaultScoreFile();
        }

        try {
            DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG);
            BufferedReader reader = new BufferedReader(new FileReader(scoreFile));
            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0];
                    String score = parts[1];
                    long timestamp = Long.parseLong(parts[2]);
                    String date = dateFormatter.format(new Date(timestamp));
                    System.out.printf("%20s %6s %s\n", name, score, date);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading score file");
        }
    }

    private void createDefaultScoreFile() {
        System.out.println("Creating new score table");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE, true));
            // Add default scores
            pw.println("Hugh Jass,__id,1281625395123");
            pw.println("Ivana Tinkle,1100,1281625395123");
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Failed to create score file");
        }
    }

    public int calculateTimeBonus(long startTime) {
        long now = System.currentTimeMillis();
        int elapsed = (int) (now - startTime);
        int bonus = GameConstants.TIME_BONUS_THRESHOLD - elapsed;
        return bonus > 0 ? bonus / 1000 : 0;
    }

    public void displayScore(String playerName, int score) {
        System.out.printf("%s your score is %d\n", playerName, score);
    }

    private void printHeader(String title) {
        String underline = title.replaceAll(".", "=");
        System.out.println(underline);
        System.out.println(title);
        System.out.println(underline);
    }
}