package base;

class InvalidCheatCommand implements CheatCommandInterface {
    public void execute(CheatSystem system) {
        System.out.println("Invalid cheat choice");
    }
}