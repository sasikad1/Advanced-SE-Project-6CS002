package base;

class PlaceDominoCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        return gameEngine.handlePlaceDomino(); // Now this will work
    }
}