package base;

public class Main {
    public static void main(String[] args) {
        IOSpecialist io = new IOSpecialist();
        GameManager gameManager = new GameManager(io);
        gameManager.run();
    }
}