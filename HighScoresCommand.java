package base;

class HighScoresCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        manager.getScoreManager().showHighScores(); // Use getter
        return true;
    }
}