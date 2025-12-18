package base;

class InvalidPlayCommand implements PlayMenuCommandInterface {
    public boolean execute(GameEngine gameEngine) {
        System.out.println("Invalid play menu choice");
        return true;
    }
}