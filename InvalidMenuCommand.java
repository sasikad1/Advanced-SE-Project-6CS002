package base;

class InvalidMenuCommand implements MenuCommandInterface {
    public boolean execute(GameManager manager) {
        System.out.println("Invalid menu choice. Please try again.");
        return true;
    }
}