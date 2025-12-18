package base;


class PrintDominoesCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        gameEngine.printGuesses(); // Now this will work
        return true;
    }
}