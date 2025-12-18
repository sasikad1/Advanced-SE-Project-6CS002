package base;

class PrintBoxCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        gameEngine.getGridManager().printGuessGrid();
        return true;
    }
}
