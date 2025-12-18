package base;

class CheckScoreCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        new ScoreManager().displayScore(
                gameEngine.getGameState().getPlayerName(),
                gameEngine.getGameState().getScore()
        );
        return true;
    }
}