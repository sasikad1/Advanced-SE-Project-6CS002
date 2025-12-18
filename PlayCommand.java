package base;

class PlayCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        manager.handlePlayGame();
        return true;
    }
}