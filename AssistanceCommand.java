package base;

class AssistanceCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        gameEngine.getCheatSystem().handleCheatMenu();
        return true;
    }
}