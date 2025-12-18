package base;

class FindPossibilitiesCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        system.handleFindPossibilities();
    }
}