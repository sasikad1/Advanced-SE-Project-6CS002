package base;

class RulesCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        manager.getMenuManager().displayRules(); // Use getter
        return true;
    }
}
