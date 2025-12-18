package base;

class UnplaceDominoCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        return gameEngine.handleUnplaceDomino(); // Now this will work
    }
}