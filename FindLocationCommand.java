package base;

class FindLocationCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        system.handleFindLocation();
    }
}
