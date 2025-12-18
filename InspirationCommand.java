package base;

class InspirationCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        manager.getMenuManager().displayInspiration(); // Use getter
        return true;
    }
}
