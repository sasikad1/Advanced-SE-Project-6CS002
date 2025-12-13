package base;

public class GameConstants {
    // Existing constants
    public static final int GRID_ROWS = 7;
    public static final int GRID_COLS = 8;
    public static final int EMPTY_CELL = 9;
    public static final int SCORE_CORRECT = 1000;
    public static final int SCORE_WRONG = -1000;
    public static final int CHEAT_COST = 500;

    // NEW CONSTANTS for remaining magic numbers
    public static final int MAX_DOMINO_VALUE = 6;
    public static final int GRID_MAX_ROW_INDEX = 6;    // GRID_ROWS - 1
    public static final int GRID_MAX_COL_INDEX = 7;    // GRID_COLS - 1
    public static final int GRID_LAST_ROW = 6;         // for loops
    public static final int GRID_LAST_COL = 7;         // for loops
    public static final int DOMINO_COUNT = 28;

    // Difficulty levels
    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;

    // Menu options
    public static final int MENU_QUIT = 0;
    public static final int MENU_PLAY = 1;
    public static final int MENU_HIGH_SCORES = 2;
    public static final int MENU_RULES = 3;
    public static final int MENU_INSPIRATION = 5;

    // Input validation
    public static final int MIN_MENU_CHOICE = 0;
    public static final int MAX_MENU_CHOICE = 7;
    public static final int INVALID_CHOICE = -9;
    public static final int INVALID_DIFFICULTY = -7;

    // Game timing and scoring
    public static final int TIME_BONUS_THRESHOLD = 60000;
    public static final int TIME_BONUS_PER_SECOND = 1;
    public static final int HONESTY_BONUS = 3;
    public static final int DISHONESTY_PENALTY = 100;
    public static final int SCORUNDREL_PENALTY = 10000;

    // Cheat menu options
    public static final int CHEAT_FIND_DOMINO = 1;
    public static final int CHEAT_FIND_LOCATION = 2;
    public static final int CHEAT_FIND_CERTAINTIES = 3;
    public static final int CHEAT_FIND_POSSIBILITIES = 4;
    public static final int CHEAT_CHANGE_MIND = 0;

    // Cheat costs
    public static final int CERTAINTIES_COST = 2000;
    public static final int POSSIBILITIES_COST = 10000;

    // Grid boundaries for domino placement
    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 8;
    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 7;

    // Domino orientation
    public static final int DOMINO_WIDTH = 1;
    public static final int DOMINO_HEIGHT = 1;

    // Play menu options
    public static final int PLAY_PRINT_GRID = 1;
    public static final int PLAY_PRINT_BOX = 2;
    public static final int PLAY_PRINT_DOMINOES = 3;
    public static final int PLAY_PLACE_DOMINO = 4;
    public static final int PLAY_UNPLACE_DOMINO = 5;
    public static final int PLAY_ASSISTANCE = 6;
    public static final int PLAY_CHECK_SCORE = 7;
    public static final int PLAY_GIVE_UP = 0;
}