package base;

import java.util.*;

public class CheatSystem {
    private GameEngine gameEngine;
    private MenuManager menuManager;
    private IOSpecialist io;
    private Map<Integer, CheatCommand> cheatCommands;

    public CheatSystem(GameEngine gameEngine, IOSpecialist io) {
        this.gameEngine = gameEngine;
        this.io = io;
        this.menuManager = new MenuManager(io);
        initializeCheatCommands();
    }

    private void initializeCheatCommands() {
        cheatCommands = new HashMap<>();

        // Cheat Menu Commands
        cheatCommands.put(GameConstants.CHEAT_CHANGE_MIND, new CheatCommand() {
            public void execute(CheatSystem system) {
                system.handleChangedMind();
            }
        });

        cheatCommands.put(GameConstants.CHEAT_FIND_DOMINO, new CheatCommand() {
            public void execute(CheatSystem system) {
                system.handleFindDomino();
            }
        });

        cheatCommands.put(GameConstants.CHEAT_FIND_LOCATION, new CheatCommand() {
            public void execute(CheatSystem system) {
                system.handleFindLocation();
            }
        });

        cheatCommands.put(GameConstants.CHEAT_FIND_CERTAINTIES, new CheatCommand() {
            public void execute(CheatSystem system) {
                system.handleFindCertainties();
            }
        });

        cheatCommands.put(GameConstants.CHEAT_FIND_POSSIBILITIES, new CheatCommand() {
            public void execute(CheatSystem system) {
                system.handleFindPossibilities();
            }
        });
    }

    public void handleCheatMenu() {
        menuManager.displayCheatMenu();
        int choice = menuManager.getCheatChoice();
        handleCheatChoice(choice);
    }

    private void handleCheatChoice(int choice) {
        CheatCommand command = cheatCommands.get(choice);
        if (command != null) {
            command.execute(this);
        } else {
            System.out.println("Invalid cheat choice");
        }
    }

    private void handleChangedMind() {
        GameState state = gameEngine.getGameState();
        int cheatFlag = state.getCheatFlag();

        switch (cheatFlag) {
            case 0:
                System.out.println("Well done");
                System.out.println("You get a " + GameConstants.HONESTY_BONUS + " point bonus for honesty");
                state.addToScore(GameConstants.HONESTY_BONUS);
                state.setCheatFlag(1);
                break;
            case 1:
                System.out.println("So you thought you could get the " + GameConstants.HONESTY_BONUS + " point bonus twice");
                System.out.println("You need to check your score");
                int currentScore = state.getScore();
                if (currentScore > 0) {
                    state.setScore(-currentScore);
                } else {
                    state.addToScore(-GameConstants.DISHONESTY_PENALTY);
                }
                state.setPlayerName(state.getPlayerName() + "(scoundrel)");
                state.setCheatFlag(2);
                break;
            default:
                System.out.println("Some people just don't learn");
                String name = state.getPlayerName().replace("scoundrel", "pathetic scoundrel");
                state.setPlayerName(name);
                for (int i = 0; i < GameConstants.SCORUNDREL_PENALTY; i++) {
                    state.addToScore(-1);
                }
        }
    }

    private void handleFindDomino() {
        GameState state = gameEngine.getGameState();
        state.addToScore(-GameConstants.CHEAT_COST);

        System.out.println("Which domino?");
        System.out.println("Number on one side?");
        int value1 = InputValidator.getValidatedInt(io, 0, GameConstants.MAX_DOMINO_VALUE, null);

        System.out.println("Number on the other side?");
        int value2 = InputValidator.getValidatedInt(io, 0, GameConstants.MAX_DOMINO_VALUE, null);

        Domino domino = gameEngine.findDominoByValues(value1, value2, false);
        System.out.println(domino != null ? domino : "Domino not found");
    }

    private void handleFindLocation() {
        GameState state = gameEngine.getGameState();
        state.addToScore(-GameConstants.CHEAT_COST);

        System.out.println("Which location?");
        int[] coords = InputValidator.getValidatedCoordinates(io);

        Domino domino = gameEngine.findDominoAt(coords[0], coords[1], false);
        System.out.println(domino != null ? domino : "No domino at that location");
    }

    private void handleFindCertainties() {
        GameState state = gameEngine.getGameState();
        state.addToScore(-GameConstants.CERTAINTIES_COST);

        Map<Domino, List<Location>> locationMap = createLocationMap();
        printCertainties(locationMap);
    }

    private void handleFindPossibilities() {
        GameState state = gameEngine.getGameState();
        state.addToScore(-GameConstants.POSSIBILITIES_COST);

        Map<Domino, List<Location>> locationMap = createLocationMap();
        printPossibilities(locationMap);
    }

    private Map<Domino, List<Location>> createLocationMap() {
        Map<Domino, List<Location>> map = new HashMap<>();
        GameState state = gameEngine.getGameState();
        int[][] grid = state.getGrid();

        for (int r = 0; r < GameConstants.GRID_LAST_ROW; r++) {
            for (int c = 0; c < GameConstants.GRID_LAST_COL; c++) {
                Domino horizontal = gameEngine.findDominoByValues(grid[r][c], grid[r][c + 1], true);
                Domino vertical = gameEngine.findDominoByValues(grid[r][c], grid[r + 1][c], true);

                addToMap(map, horizontal, new Location(r, c, Location.DIRECTION.HORIZONTAL));
                addToMap(map, vertical, new Location(r, c, Location.DIRECTION.VERTICAL));
            }
        }
        return map;
    }

    private void addToMap(Map<Domino, List<Location>> map, Domino domino, Location location) {
        if (domino == null) return;

        List<Location> locations = map.get(domino);
        if (locations == null) {
            locations = new LinkedList<>();
            map.put(domino, locations);
        }
        locations.add(location);
    }

    private void printCertainties(Map<Domino, List<Location>> map) {
        for (Map.Entry<Domino, List<Location>> entry : map.entrySet()) {
            List<Location> locations = entry.getValue();
            if (locations.size() == 1) {
                Domino domino = entry.getKey();
                Location location = locations.get(0);
                System.out.printf("[%d%d]", domino.high, domino.low);
                System.out.println(location);
            }
        }
    }

    private void printPossibilities(Map<Domino, List<Location>> map) {
        for (Map.Entry<Domino, List<Location>> entry : map.entrySet()) {
            Domino domino = entry.getKey();
            List<Location> locations = entry.getValue();
            System.out.printf("[%d%d]", domino.high, domino.low);
            for (Location location : locations) {
                System.out.print(location);
            }
            System.out.println();
        }
    }
}