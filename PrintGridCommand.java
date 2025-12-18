package base;

class PrintGridCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        gameEngine.getGridManager().printMainGrid();
        return true;
    }
}
