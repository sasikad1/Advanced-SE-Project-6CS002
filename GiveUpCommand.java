package base;

class GiveUpCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        return false;
    }
}