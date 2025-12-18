package base;

class FindDominoCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        system.handleFindDomino();
    }
}