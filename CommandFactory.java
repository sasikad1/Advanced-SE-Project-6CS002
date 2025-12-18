package base;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    // Maps for storing command creators
    private static final Map<Integer, Supplier<MenuCommandInterface>> menuCommands = new HashMap<>();
    private static final Map<Integer, Supplier<PlayMenuCommandInterface>> playCommands = new HashMap<>();
    private static final Map<Integer, Supplier<CheatCommandInterface>> cheatCommands = new HashMap<>();

    // Static initializer block
    static {
        initializeMenuCommands();
        initializePlayCommands();
        initializeCheatCommands();
    }

    private static void initializeMenuCommands() {
        // Main menu commands
        menuCommands.put(GameConstants.MENU_PLAY, PlayCommand::new);
        menuCommands.put(GameConstants.MENU_HIGH_SCORES, HighScoresCommand::new);
        menuCommands.put(GameConstants.MENU_RULES, RulesCommand::new);
        menuCommands.put(GameConstants.MENU_INSPIRATION, InspirationCommand::new);
        menuCommands.put(GameConstants.MENU_QUIT, QuitCommand::new);
    }

    private static void initializePlayCommands() {
        // Play menu commands
        playCommands.put(GameConstants.PLAY_PRINT_GRID, PrintGridCommand::new);
        playCommands.put(GameConstants.PLAY_PRINT_BOX, PrintBoxCommand::new);
        playCommands.put(GameConstants.PLAY_PRINT_DOMINOES, PrintDominoesCommand::new);
        playCommands.put(GameConstants.PLAY_PLACE_DOMINO, PlaceDominoCommand::new);
        playCommands.put(GameConstants.PLAY_UNPLACE_DOMINO, UnplaceDominoCommand::new);
        playCommands.put(GameConstants.PLAY_ASSISTANCE, AssistanceCommand::new);
        playCommands.put(GameConstants.PLAY_CHECK_SCORE, CheckScoreCommand::new);
        playCommands.put(GameConstants.PLAY_GIVE_UP, GiveUpCommand::new);
    }

    private static void initializeCheatCommands() {
        // Cheat menu commands
        cheatCommands.put(GameConstants.CHEAT_CHANGE_MIND, ChangeMindCommand::new);
        cheatCommands.put(GameConstants.CHEAT_FIND_DOMINO, FindDominoCommand::new);
        cheatCommands.put(GameConstants.CHEAT_FIND_LOCATION, FindLocationCommand::new);
        cheatCommands.put(GameConstants.CHEAT_FIND_CERTAINTIES, FindCertaintiesCommand::new);
        cheatCommands.put(GameConstants.CHEAT_FIND_POSSIBILITIES, FindPossibilitiesCommand::new);
    }

    // Factory methods
    public static MenuCommandInterface createMenuCommand(int choice) {
        Supplier<MenuCommandInterface> supplier = menuCommands.get(choice);
        return supplier != null ? supplier.get() : new InvalidMenuCommand();
    }

    public static PlayMenuCommandInterface createPlayCommand(int choice) {
        Supplier<PlayMenuCommandInterface> supplier = playCommands.get(choice);
        return supplier != null ? supplier.get() : new InvalidPlayCommand();
    }

    public static CheatCommandInterface createCheatCommand(int choice) {
        Supplier<CheatCommandInterface> supplier = cheatCommands.get(choice);
        return supplier != null ? supplier.get() : new InvalidCheatCommand();
    }
}