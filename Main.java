package base;

import java.awt.Graphics;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {
    // ============ FIELDS ============
    private String playerName;
    public List<Domino> _d;
    public List<Domino> _g;
    public int[][] grid = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
    public int[][] gg = new int[GameConstants.GRID_ROWS][GameConstants.GRID_COLS];
    int mode = -1;
    int cheatFlag = 0;
    int score;
    long startTime;

    PictureFrame pf;
    private IOSpecialist io;

    // ============ REFACTORED MAIN METHOD ============
    public void run() {
        initializeGame();
        displayWelcomeMessage();
        getPlayerName();
        greetPlayer();

        boolean continueRunning = true;
        while (continueRunning) {
            displayMainMenu();
            int menuChoice = getMenuChoice();
            continueRunning = handleMenuChoice(menuChoice);
        }
    }

    // ============ EXTRACTED METHODS ============

    private void initializeGame() {
        io = new IOSpecialist();
        pf = new PictureFrame();
        score = 0;
    }

    private void displayWelcomeMessage() {
        System.out.println("Welcome To Abominodo - The Best Dominoes Puzzle Game in the Universe");
        System.out.println("Version 2.1 (c), Kevan Buckley, 2014");
        System.out.println();
    }

    private void getPlayerName() {
        System.out.println(MultiLingualStringTable.getMessage(0));
        playerName = io.getString();
    }

    private void greetPlayer() {
        System.out.printf("%s %s. %s", MultiLingualStringTable.getMessage(1),
                playerName, MultiLingualStringTable.getMessage(2));
    }

    private void displayMainMenu() {
        System.out.println();
        printMenuHeader("Main Menu");
        System.out.println(GameConstants.MENU_PLAY + ") Play");
        System.out.println(GameConstants.MENU_HIGH_SCORES + ") View high scores");
        System.out.println(GameConstants.MENU_RULES + ") View rules");
        System.out.println(GameConstants.MENU_INSPIRATION + ") Get inspiration");
        System.out.println(GameConstants.MENU_QUIT + ") Quit");
    }

    private void printMenuHeader(String title) {
        String underline = title.replaceAll(".", "=");
        System.out.println(underline);
        System.out.println(title);
        System.out.println(underline);
    }

    private int getMenuChoice() {
        return InputValidator.getValidatedInt(io,
                GameConstants.MIN_MENU_CHOICE,
                GameConstants.MAX_MENU_CHOICE,
                null);
    }

    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case GameConstants.MENU_QUIT:
                handleQuit();
                return false;
            case GameConstants.MENU_PLAY:
                handlePlay();
                break;
            case GameConstants.MENU_HIGH_SCORES:
                handleHighScores();
                break;
            case GameConstants.MENU_RULES:
                handleRules();
                break;
            case GameConstants.MENU_INSPIRATION:
                handleInspiration();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private void handleQuit() {
        if (_d == null) {
            System.out.println("It is a shame that you did not want to play");
        } else {
            System.out.println("Thank you for playing");
        }
        System.exit(0);
    }

    private void handlePlay() {
        displayDifficultyMenu();
        int difficulty = getDifficultyChoice();
        setupGame(difficulty);
        startGameSession();
    }

    private void displayDifficultyMenu() {
        System.out.println();
        printMenuHeader("Select Difficulty");
        System.out.println(GameConstants.DIFFICULTY_EASY + ") Simples");
        System.out.println(GameConstants.DIFFICULTY_MEDIUM + ") Not-so-simples");
        System.out.println(GameConstants.DIFFICULTY_HARD + ") Super-duper-shuffled");
    }

    private int getDifficultyChoice() {
        int choice = GameConstants.INVALID_DIFFICULTY;
        while (!isValidDifficultyChoice(choice)) {
            choice = InputValidator.getValidatedInt(io,
                    GameConstants.DIFFICULTY_EASY,
                    GameConstants.DIFFICULTY_HARD,
                    null);
        }
        return choice;
    }

    private boolean isValidDifficultyChoice(int choice) {
        return choice == GameConstants.DIFFICULTY_EASY ||
                choice == GameConstants.DIFFICULTY_MEDIUM ||
                choice == GameConstants.DIFFICULTY_HARD;
    }

    private void setupGame(int difficulty) {
        initializeDominoes();

        switch (difficulty) {
            case GameConstants.DIFFICULTY_EASY:
                setupEasyDifficulty();
                break;
            case GameConstants.DIFFICULTY_MEDIUM:
                setupMediumDifficulty();
                break;
            case GameConstants.DIFFICULTY_HARD:
                setupHardDifficulty();
                break;
        }

        prepareGameState();
    }

    private void initializeDominoes() {
        generateDominoes();
        shuffleDominoesOrder();
    }

    private void setupEasyDifficulty() {
        placeDominoes();
        collateGrid();
    }

    private void setupMediumDifficulty() {
        placeDominoes();
        rotateDominoes();
        collateGrid();
    }

    private void setupHardDifficulty() {
        placeDominoes();
        rotateDominoes();
        rotateDominoes();
        rotateDominoes();
        invertSomeDominoes();
        collateGrid();
    }

    private void prepareGameState() {
        printGrid();
        generateGuesses();
        collateGuessGrid();
        mode = 1;
        cheatFlag = 0;
        score = 0;
        startTime = System.currentTimeMillis();
        initializeGUI();
    }

    private void startGameSession() {
        boolean playing = true;
        while (playing) {
            displayPlayMenu();
            int choice = getPlayMenuChoice();
            playing = handlePlayMenuChoice(choice);
        }
        endGameSession();
    }

    private void displayPlayMenu() {
        System.out.println();
        printMenuHeader("Play Menu");
        System.out.println(GameConstants.PLAY_PRINT_GRID + ") Print the grid");
        System.out.println(GameConstants.PLAY_PRINT_BOX + ") Print the box");
        System.out.println(GameConstants.PLAY_PRINT_DOMINOES + ") Print the dominos");
        System.out.println(GameConstants.PLAY_PLACE_DOMINO + ") Place a domino");
        System.out.println(GameConstants.PLAY_UNPLACE_DOMINO + ") Unplace a domino");
        System.out.println(GameConstants.PLAY_ASSISTANCE + ") Get some assistance");
        System.out.println(GameConstants.PLAY_CHECK_SCORE + ") Check your score");
        System.out.println(GameConstants.PLAY_GIVE_UP + ") Give up");
        System.out.println("What do you want to do " + playerName + "?");
    }

    private int getPlayMenuChoice() {
        return InputValidator.getValidatedInt(io,
                GameConstants.PLAY_GIVE_UP,
                GameConstants.PLAY_CHECK_SCORE,
                null);
    }

    private boolean isValidPlayMenuChoice(int choice) {
        return choice == GameConstants.PLAY_GIVE_UP ||
                choice == GameConstants.PLAY_PRINT_GRID ||
                choice == GameConstants.PLAY_PRINT_BOX ||
                choice == GameConstants.PLAY_PRINT_DOMINOES ||
                choice == GameConstants.PLAY_PLACE_DOMINO ||
                choice == GameConstants.PLAY_UNPLACE_DOMINO ||
                choice == GameConstants.PLAY_ASSISTANCE ||
                choice == GameConstants.PLAY_CHECK_SCORE;
    }

    private boolean handlePlayMenuChoice(int choice) {
        switch (choice) {
            case GameConstants.PLAY_GIVE_UP:
                return false;
            case GameConstants.PLAY_PRINT_GRID:
                handlePrintGrid();
                break;
            case GameConstants.PLAY_PRINT_BOX:
                handlePrintBox();
                break;
            case GameConstants.PLAY_PRINT_DOMINOES:
                handlePrintDominoes();
                break;
            case GameConstants.PLAY_PLACE_DOMINO:
                handlePlaceDomino();
                break;
            case GameConstants.PLAY_UNPLACE_DOMINO:
                handleUnplaceDomino();
                break;
            case GameConstants.PLAY_ASSISTANCE:
                handleAssistance();
                break;
            case GameConstants.PLAY_CHECK_SCORE:
                handleCheckScore();
                break;
            default:
                System.out.println("Invalid choice");
        }
        return true;
    }

    private void handleHighScores() {
        System.out.println();
        printMenuHeader("High Scores");

        File f = new File("score.txt");
        if (!(f.exists() && f.isFile() && f.canRead())) {
            System.out.println("Creating new score table");
            try {
                PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
                String n = playerName.replaceAll(",", "_");
                pw.print("Hugh Jass");
                pw.print(",");
                pw.print("__id");
                pw.print(",");
                pw.println(1281625395123L);
                pw.print("Ivana Tinkle");
                pw.print(",");
                pw.print(1100);
                pw.print(",");
                pw.println(1281625395123L);
                pw.flush();
                pw.close();
            } catch (Exception e) {
                System.out.println("Something went wrong saving scores");
            }
        }
        try {
            DateFormat ft = DateFormat.getDateInstance(DateFormat.LONG);
            BufferedReader r = new BufferedReader(new FileReader(f));
            while (true) {
                String lin = r.readLine();
                if (lin == null || lin.length() == 0)
                    break;
                String[] parts = lin.split(",");
                System.out.printf("%20s %6s %s\n", parts[0], parts[1], ft
                        .format(new Date(Long.parseLong(parts[2]))));
            }
        } catch (Exception e) {
            System.out.println("Malfunction!!");
        }
    }

    private void handleRules() {
        System.out.println();
        printMenuHeader("Rules");
        System.out.println("Rules by __student");

        JFrame f = new JFrame("Rules by __student");
        f.setSize(new java.awt.Dimension(500, 500));
        JEditorPane w;
        try {
            w = new JEditorPane("http://www.scit.wlv.ac.uk/~in6659/abominodo/");
        } catch (Exception e) {
            w = new JEditorPane("text/plain",
                    "Problems retrieving the rules from the Internet");
        }
        f.setContentPane(new JScrollPane(w));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void handleInspiration() {
        int index = (int) (Math.random() * (_Q.stuff.length / 3));
        String what = _Q.stuff[index * 3];
        String who = _Q.stuff[1 + index * 3];
        System.out.printf("%s said \"%s\"", who, what);
        System.out.println();
        System.out.println();
    }

    // ============ GAME ACTION HANDLERS ============

    private void handlePrintGrid() {
        printGrid();
    }

    private void handlePrintBox() {
        printGuessGrid();
    }

    private void handlePrintDominoes() {
        Collections.sort(_g);
        printGuesses();
    }

    private void handleCheckScore() {
        System.out.printf("%s your score is %d\n", playerName, score);
    }

    private void handlePlaceDomino() {
        System.out.println("Where will the top left of the domino be?");

        int[] coords = InputValidator.getValidatedCoordinates(io);
        int x = coords[0];
        int y = coords[1];

        System.out.println("Horizontal or Vertical (H or V)?");
        boolean horiz;
        int y2, x2;
        Location lotion;

        horiz = InputValidator.getYesNoResponse(io, "Horizontal or Vertical (H or V)?");
        if (horiz) {
            lotion = new Location(x, y, Location.DIRECTION.HORIZONTAL);
            System.out.println("Direction to place is " + lotion.d);
            x2 = x + GameConstants.DOMINO_WIDTH;
            y2 = y;
        } else {
            lotion = new Location(x, y, Location.DIRECTION.VERTICAL);
            System.out.println("Direction to place is " + lotion.d);
            x2 = x;
            y2 = y + GameConstants.DOMINO_HEIGHT;
        }

        if (x2 > GameConstants.GRID_MAX_COL_INDEX || y2 > GameConstants.GRID_MAX_ROW_INDEX) {
            System.out.println("Problems placing the domino with that position and direction");
        } else {
            Domino d = DominoFinder.findDominoByValues(_g, grid[y][x], grid[y2][x2]);
            if (d == null) {
                System.out.println("There is no such domino");
                return;
            }
            if (d.placed) {
                System.out.println("That domino has already been placed :");
                System.out.println(d);
                return;
            }
            if (gg[y][x] != GameConstants.EMPTY_CELL || gg[y2][x2] != GameConstants.EMPTY_CELL) {
                System.out.println("Those coordinates are not vacant");
                return;
            }
            gg[y][x] = grid[y][x];
            gg[y2][x2] = grid[y2][x2];
            if (grid[y][x] == d.high && grid[y2][x2] == d.low) {
                d.place(x, y, x2, y2);
            } else {
                d.place(x2, y2, x, y);
            }
            score += GameConstants.SCORE_CORRECT;
            collateGuessGrid();
            pf.dp.repaint();
        }
    }

    private void handleUnplaceDomino() {
        System.out.println("Enter a position that the domino occupies");

        int[] coords = InputValidator.getValidatedCoordinates(io);
        int x = coords[0];
        int y = coords[1];

        Domino lkj = DominoFinder.findDominoAt(_g, x, y);
        if (lkj == null) {
            System.out.println("Couldn't find a domino there");
        } else {
            lkj.placed = false;
            gg[lkj.hy][lkj.hx] = GameConstants.EMPTY_CELL;
            gg[lkj.ly][lkj.lx] = GameConstants.EMPTY_CELL;
            score += GameConstants.SCORE_WRONG;
            collateGuessGrid();
            pf.dp.repaint();
        }
    }

    private void handleAssistance() {
        displayCheatMenu();
        int choice = InputValidator.getValidatedInt(io,
                GameConstants.CHEAT_CHANGE_MIND,
                GameConstants.CHEAT_FIND_POSSIBILITIES,
                null);
        handleCheatChoice(choice);
    }

    private void displayCheatMenu() {
        System.out.println();
        printMenuHeader("So you want to cheat, huh?");
        System.out.println(GameConstants.CHEAT_FIND_DOMINO + ") Find a particular Domino (costs you " + GameConstants.CHEAT_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_LOCATION + ") Which domino is at ... (costs you " + GameConstants.CHEAT_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_CERTAINTIES + ") Find all certainties (costs you " + GameConstants.CERTAINTIES_COST + ")");
        System.out.println(GameConstants.CHEAT_FIND_POSSIBILITIES + ") Find all possibilities (costs you " + GameConstants.POSSIBILITIES_COST + ")");
        System.out.println(GameConstants.CHEAT_CHANGE_MIND + ") You have changed your mind about cheating");
        System.out.println("What do you want to do?");
    }

    private void handleCheatChoice(int choice) {
        switch (choice) {
            case GameConstants.CHEAT_CHANGE_MIND:
                handleChangedMind();
                break;
            case GameConstants.CHEAT_FIND_DOMINO:
                handleFindDomino();
                break;
            case GameConstants.CHEAT_FIND_LOCATION:
                handleFindLocation();
                break;
            case GameConstants.CHEAT_FIND_CERTAINTIES:
                handleFindCertainties();
                break;
            case GameConstants.CHEAT_FIND_POSSIBILITIES:
                handleFindPossibilities();
                break;
        }
    }

    private void handleChangedMind() {
        switch (cheatFlag) {
            case 0:
                System.out.println("Well done");
                System.out.println("You get a " + GameConstants.HONESTY_BONUS + " point bonus for honesty");
                score += GameConstants.HONESTY_BONUS;
                cheatFlag++;
                break;
            case 1:
                System.out.println("So you though you could get the " + GameConstants.HONESTY_BONUS + " point bonus twice");
                System.out.println("You need to check your score");
                if (score > 0) {
                    score = -score;
                } else {
                    score -= GameConstants.DISHONESTY_PENALTY;
                }
                playerName = playerName + "(scoundrel)";
                cheatFlag++;
                break;
            default:
                System.out.println("Some people just don't learn");
                playerName = playerName.replace("scoundrel", "pathetic scoundrel");
                for (int i = 0; i < GameConstants.SCORUNDREL_PENALTY; i++) {
                    score--;
                }
        }
    }

    private void handleFindDomino() {
        score -= GameConstants.CHEAT_COST;
        System.out.println("Which domino?");
        System.out.println("Number on one side?");
        int value1 = InputValidator.getValidatedInt(io, 0, GameConstants.MAX_DOMINO_VALUE, null);

        System.out.println("Number on the other side?");
        int value2 = InputValidator.getValidatedInt(io, 0, GameConstants.MAX_DOMINO_VALUE, null);

        Domino dd = DominoFinder.findDominoByValues(_d, value1, value2);
        System.out.println(dd);
    }

    private void handleFindLocation() {
        score -= GameConstants.CHEAT_COST;
        System.out.println("Which location?");

        int[] coords = InputValidator.getValidatedCoordinates(io);
        int x = coords[0];
        int y = coords[1];

        Domino lkj2 = DominoFinder.findDominoAt(_d, x, y);
        System.out.println(lkj2);
    }

    private void handleFindCertainties() {
        score -= GameConstants.CERTAINTIES_COST;
        Map<Domino, List<Location>> map = DominoFinder.createLocationMap(_g, grid);
        printCertainties(map);
    }

    private void handleFindPossibilities() {
        score -= GameConstants.POSSIBILITIES_COST;
        Map<Domino, List<Location>> map = DominoFinder.createLocationMap(_g, grid);
        printPossibilities(map);
    }

    private void printCertainties(Map<Domino, List<Location>> map) {
        for (Domino key : map.keySet()) {
            List<Location> locs = map.get(key);
            if (locs.size() == 1) {
                Location loc = locs.get(0);
                System.out.printf("[%d%d]", key.high, key.low);
                System.out.println(loc);
            }
        }
    }

    private void printPossibilities(Map<Domino, List<Location>> map) {
        for (Domino key : map.keySet()) {
            System.out.printf("[%d%d]", key.high, key.low);
            List<Location> locs = map.get(key);
            for (Location loc : locs) {
                System.out.print(loc);
            }
            System.out.println();
        }
    }

    // ============ ORIGINAL GAME LOGIC METHODS ============

    private void generateDominoes() {
        _d = new LinkedList<Domino>();
        int count = 0;
        int x = 0;
        int y = 0;
        for (int l = 0; l <= GameConstants.MAX_DOMINO_VALUE; l++) {
            for (int h = l; h <= GameConstants.MAX_DOMINO_VALUE; h++) {
                Domino d = new Domino(h, l);
                _d.add(d);
                d.place(x, y, x + 1, y);
                count++;
                x += 2;
                if (x > GameConstants.GRID_LAST_ROW) {
                    x = 0;
                    y++;
                }
            }
        }
        if (count != GameConstants.DOMINO_COUNT) {
            System.out.println("something went wrong generating dominoes");
            System.exit(0);
        }
    }

    private void generateGuesses() {
        _g = new LinkedList<Domino>();
        int count = 0;
        int x = 0;
        int y = 0;
        for (int l = 0; l <= GameConstants.MAX_DOMINO_VALUE; l++) {
            for (int h = l; h <= GameConstants.MAX_DOMINO_VALUE; h++) {
                Domino d = new Domino(h, l);
                _g.add(d);
                count++;
            }
        }
        if (count != GameConstants.DOMINO_COUNT) {
            System.out.println("something went wrong generating dominoes");
            System.exit(0);
        }
    }

    void collateGrid() {
        for (Domino d : _d) {
            if (!d.placed) {
                grid[d.hy][d.hx] = GameConstants.EMPTY_CELL;
                grid[d.ly][d.lx] = GameConstants.EMPTY_CELL;
            } else {
                grid[d.hy][d.hx] = d.high;
                grid[d.ly][d.lx] = d.low;
            }
        }
    }

    void collateGuessGrid() {
        for (int r = 0; r < GameConstants.GRID_ROWS; r++) {
            for (int c = 0; c < GameConstants.GRID_COLS; c++) {
                gg[r][c] = GameConstants.EMPTY_CELL;
            }
        }
        for (Domino d : _g) {
            if (d.placed) {
                gg[d.hy][d.hx] = d.high;
                gg[d.ly][d.lx] = d.low;
            }
        }
    }

    private void printGrid() {
        GridPrinter.printGrid(grid);
    }

    private void printGuessGrid() {
        GridPrinter.printGrid(gg);
    }

    private void printGuesses() {
        for (Domino d : _g) {
            System.out.println(d);
        }
    }

    private void printDominoes() {
        for (Domino d : _d) {
            System.out.println(d);
        }
    }

    private void shuffleDominoesOrder() {
        List<Domino> shuffled = new LinkedList<Domino>();

        while (_d.size() > 0) {
            int n = (int) (Math.random() * _d.size());
            shuffled.add(_d.get(n));
            _d.remove(n);
        }

        _d = shuffled;
    }

    private void invertSomeDominoes() {
        for (Domino d : _d) {
            if (Math.random() > 0.5) {
                d.invert();
            }
        }
    }

    private void placeDominoes() {
        int x = 0;
        int y = 0;
        int count = 0;
        for (Domino d : _d) {
            count++;
            d.place(x, y, x + 1, y);
            x += 2;
            if (x > GameConstants.GRID_LAST_ROW) {
                x = 0;
                y++;
            }
        }
        if (count != GameConstants.DOMINO_COUNT) {
            System.out.println("something went wrong generating dominoes");
            System.exit(0);
        }
    }

    private void rotateDominoes() {
        for (int x = 0; x < GameConstants.GRID_ROWS; x++) {
            for (int y = 0; y < GameConstants.GRID_LAST_ROW; y++) {
                tryToRotateDominoAt(x, y);
            }
        }
    }

    // ============ DOMINO MANIPULATION METHODS ============

    private void tryToRotateDominoAt(int x, int y) {
        Domino d = findDominoAt(x, y);
        if (thisIsTopLeftOfDomino(x, y, d)) {
            if (d.ishl()) {
                boolean weFancyARotation = Math.random() < 0.5;
                if (weFancyARotation) {
                    if (theCellBelowIsTopLeftOfHorizontalDomino(x, y)) {
                        Domino e = findDominoAt(x, y + 1);
                        e.hx = x;
                        e.lx = x;
                        d.hx = x + 1;
                        d.lx = x + 1;
                        e.ly = y + 1;
                        e.hy = y;
                        d.ly = y + 1;
                        d.hy = y;
                    }
                }
            } else {
                boolean weFancyARotation = Math.random() < 0.5;
                if (weFancyARotation) {
                    if (theCellToTheRightIsTopLeftOfVerticalDomino(x, y)) {
                        Domino e = findDominoAt(x + 1, y);
                        e.hx = x;
                        e.lx = x + 1;
                        d.hx = x;
                        d.lx = x + 1;
                        e.ly = y + 1;
                        e.hy = y + 1;
                        d.ly = y;
                        d.hy = y;
                    }
                }
            }
        }
    }

    private boolean theCellToTheRightIsTopLeftOfVerticalDomino(int x, int y) {
        Domino e = findDominoAt(x + 1, y);
        return thisIsTopLeftOfDomino(x + 1, y, e) && !e.ishl();
    }

    private boolean theCellBelowIsTopLeftOfHorizontalDomino(int x, int y) {
        Domino e = findDominoAt(x, y + 1);
        return thisIsTopLeftOfDomino(x, y + 1, e) && e.ishl();
    }

    private boolean thisIsTopLeftOfDomino(int x, int y, Domino d) {
        return (x == Math.min(d.lx, d.hx)) && (y == Math.min(d.ly, d.hy));
    }

    private Domino findDominoAt(int x, int y) {
        return DominoFinder.findDominoAt(_d, x, y);
    }

    private Domino findGuessAt(int x, int y) {
        return DominoFinder.findDominoAt(_g, x, y);
    }

    private Domino findGuessByLH(int x, int y) {
        return DominoFinder.findDominoByValues(_g, x, y);
    }

    private Domino findDominoByLH(int x, int y) {
        return DominoFinder.findDominoByValues(_d, x, y);
    }

    // ============ UTILITY METHODS ============

    private void endGameSession() {
        mode = 0;
        printGrid();
        pf.dp.repaint();
        long now = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int gap = (int) (now - startTime);
        int bonus = GameConstants.TIME_BONUS_THRESHOLD - gap;
        score += bonus > 0 ? bonus / 1000 : 0;
        recordTheScore();
        System.out.println("Here is the solution:");
        System.out.println();
        Collections.sort(_d);
        printDominoes();
        System.out.println("you scored " + score);
    }

    private void initializeGUI() {
        pf.PictureFrame(this);
        pf.dp.repaint();
    }

    private void recordTheScore() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("score.txt", true));
            String n = playerName.replaceAll(",", "_");
            pw.print(n);
            pw.print(",");
            pw.print(score);
            pw.print(",");
            pw.println(System.currentTimeMillis());
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.println("Something went wrong saving scores");
        }
    }

    public static int gecko(int nn_) {
        if (nn_ == (32 & 16)) {
            return GameConstants.INVALID_DIFFICULTY;
        } else {
            if (nn_ < 0) {
                return gecko(nn_ + 1 | 0);
            } else {
                return gecko(nn_ - 1 | 0);
            }
        }
    }

    public void drawDominoes(Graphics g) {
        for (Domino d : _d) {
            pf.dp.drawDomino(g, d);
        }
    }

    public void drawGuesses(Graphics g) {
        for (Domino d : _g) {
            pf.dp.drawDomino(g, d);
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}