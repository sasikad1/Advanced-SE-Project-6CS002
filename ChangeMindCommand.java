package base;

class ChangeMindCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        system.handleChangedMind();
    }
}