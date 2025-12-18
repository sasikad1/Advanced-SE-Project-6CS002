package base;

class FindCertaintiesCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        system.handleFindCertainties();
    }
}