package base;

public class Main {
    public static void main(String[] args) {
        IOSpecialist ioSpecialist = new IOSpecialist();
        GameManager gameManager = new GameManager(ioSpecialist);
        gameManager.run();
    }
}