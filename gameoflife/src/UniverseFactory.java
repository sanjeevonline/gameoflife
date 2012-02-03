import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating Universe.
 *
 * @author Sanjeev Kumar
 *
 */
public class UniverseFactory {

    /**
     * Constructs the Universe using the two dimensional input array.
     *
     * @param input
     */
    public static Universe createUniverse(final int rows, final int columns, final List<Cell> cells) {
       return createUniverse(GameOfLifeUtil.convertToArray(rows, columns, cells));
    }

    /**
     * Constructs the Universe by copying cells from provided universe.
     *
     * @param input
     */
    public static Universe createCopyOfUniverse(final Universe universe) {
       return createUniverse(convertUniverseToArray(universe));
    }

    /**
     * Convert universe to two dimensional array.
     *
     * @param universe
     * @return
     */
    private static char[][] convertUniverseToArray(final Universe universe) {
        return GameOfLifeUtil.convertToArray(universe.getRows(), universe.getColumns(), universe.getCells());
    }

    /**
     * Constructs the Universe using the two dimensional input array.
     *
     * @param input
     */
    public static Universe createUniverse(final char[][] input) {
        char[][] trimmed = trimDeadBoundaries(input);
        int rows = trimmed.length;
        int columns = trimmed[0].length;

        List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells.add(new Cell(i, j, (trimmed[i][j] == GameOfLifeUtil.ALIVE) ? State.ALIVE : State.DEAD));
            }
        }
       return new Universe(rows, columns, cells);
    }

    /**
     * Recursive function that trims dead boundary of the universe.
     *
     * @param input
     * @return
     */
    private static char[][] trimDeadBoundaries(final char[][] input) {
        if (input.length == 1 && input[0].length == 1 && input[0][0] == GameOfLifeUtil.DEAD) {
            return input;
        }
        if (input.length == 0) {
            char[][] deadUniverse = new char[1][1];
            deadUniverse[0][0] = GameOfLifeUtil.DEAD;
            return deadUniverse;
        }
        int rows = input.length;
        int columns = input[0].length;

        boolean hasAliveCellsOnLeft = false;
        boolean hasAliveCellsOnRight = false;
        boolean hasAliveCellsOnTop = false;
        boolean hasAliveCellsOnBottom = false;
        char[][] finalInputArray = input;

        for (int i = 0; i < rows; i++) {
            if (input[i][0] == GameOfLifeUtil.ALIVE && !hasAliveCellsOnLeft) {
                hasAliveCellsOnLeft = true;
            }
            if (input[i][columns - 1] == GameOfLifeUtil.ALIVE && !hasAliveCellsOnRight) {
                hasAliveCellsOnRight = true;
            }
            if (hasAliveCellsOnLeft && hasAliveCellsOnRight) {
                break;
            }
        }
        for (int i = 0; i < columns; i++) {
            if (input[0][i] == GameOfLifeUtil.ALIVE && !hasAliveCellsOnTop) {
                hasAliveCellsOnTop = true;
            }
            if (input[rows - 1][i] == GameOfLifeUtil.ALIVE && !hasAliveCellsOnBottom) {
                hasAliveCellsOnBottom = true;
            }
            if (hasAliveCellsOnTop && hasAliveCellsOnBottom) {
                break;
            }

        }
        if (!hasAliveCellsOnLeft) {
            finalInputArray = trimLeftColumn(finalInputArray);
        }
        if (!hasAliveCellsOnRight) {
            finalInputArray = trimRightColumn(finalInputArray);
        }
        if (!hasAliveCellsOnTop) {
            finalInputArray = trimTopRow(finalInputArray);
        }
        if (!hasAliveCellsOnBottom) {
            finalInputArray = trimBottomRow(finalInputArray);
        }
        if (!(hasAliveCellsOnLeft && hasAliveCellsOnRight && hasAliveCellsOnTop && hasAliveCellsOnBottom)) {
            finalInputArray = trimDeadBoundaries(finalInputArray);
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead row from bottom of the Universe
     * @param input
     * @return
     */
    private static char[][] trimBottomRow(final char[][] input) {
        int newRows = input.length - 1;
        int newColumns = input[0].length;
        char[][] finalInputArray =  new char[newRows][newColumns];
        for (int i = 0; i < input.length - 1; i++) { // skip the last array
            finalInputArray[i] = input[i];
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead row from top of the Universe
     * @param input
     * @return
     */
    private static char[][] trimTopRow(final char[][] input) {
        int newRows = input.length - 1;
        int newColumns = input[0].length;
        char[][] finalInputArray =  new char[newRows][newColumns];
        for (int i = 0; i < input.length - 1; i++) { // skip the first array
            finalInputArray[i] = input[i+1];
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead column from right side of the Universe
     * @param input
     * @return
     */
    private static char[][] trimRightColumn(final char[][] input) {
        int newRows = input.length;
        int newColumns = input[0].length - 1;
        char[][] finalInputArray =  new char[newRows][newColumns];
        for (int i = 0; i < input.length; i++) { // skip the last value from each nested array
            for (int j = 0; j < newColumns; j++) {
                finalInputArray[i][j] = input[i][j];
            }
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead column row from left of the Universe
     * @param input
     * @return
     */
    private static char[][] trimLeftColumn(final char[][] input) {
        int newRows = input.length;
        int newColumns = input[0].length - 1;
        char[][] finalInputArray =  new char[newRows][newColumns];
        for (int i = 0; i < input.length; i++) { // skip the last value from each nested array
            for (int j = 0; j < newColumns; j++) {
                finalInputArray[i][j] = input[i][j+1];
            }
        }
        return finalInputArray;
    }
}
