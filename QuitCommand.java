package base;

class QuitCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        manager.handleQuit();
        return false;
    }
}