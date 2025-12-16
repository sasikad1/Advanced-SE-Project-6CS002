package base;

public class InputValidator {

    public static int getValidatedInt(IOSpecialist io, int min, int max, String prompt) {
        if (prompt != null && !prompt.isEmpty()) {
            System.out.println(prompt);
        }

        int value = GameConstants.INVALID_CHOICE;
        while (value < min || value > max) {
            try {
                String input = io.getString();
                value = Integer.parseInt(input);
            } catch (Exception exception) {
                value = GameConstants.INVALID_CHOICE;
            }
        }
        return value;
    }

    public static int[] getValidatedCoordinates(IOSpecialist io) {
        System.out.println("Column?");
        int column = getValidatedInt(io, GameConstants.MIN_COLUMN,
                GameConstants.MAX_COLUMN, null);

        System.out.println("Row?");
        int row = getValidatedInt(io, GameConstants.MIN_ROW,
                GameConstants.MAX_ROW, null);

        return new int[]{column - 1, row - 1}; // Convert to 0-based
    }

    public static boolean getYesNoResponse(IOSpecialist io, String prompt) {
        System.out.println(prompt);
        while (true) {
            String response = io.getString().toUpperCase();
            if (response.startsWith("H")) return true;
            if (response.startsWith("V")) return false;
            System.out.println("Enter H or V");
        }
    }
}