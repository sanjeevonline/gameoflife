import java.util.List;



/**
 * Utility class that stores application constants and utility methods that are used throughout the application.
 *
 * @author Sanjeev Kumar
 *
 */
public final class GameOfLifeUtil {

    /** Maximum number of generations to keep in the program. */
    public static final int GENERATIONS_LIMIT = 10;

    /** Count of neighboring ALIVE cells below which live cell dies by loneliness. loneliness limit */
    public static final int LONELILESS_LIMIT = 2;

    /** Count of neighboring ALIVE cells after which live cell dies by overcrowding. Overcrowding limit. */
    public static final int OVER_CROWDING_LIMIT = 3;

    /** Count of neighboring ALIVE cells to bring a dead cell to ALIVE state. */
    public static final int BRING_TO_LIFE_COUNT = 3;

    /** ALIVE cell is represented as this character. */
    public static final char ALIVE = 'X';
    /** ALIVE cell is represented as this character. */
    public static final char DEAD = '-';

    /** Private constructor so that construction of instance is avoided from outside the class.  */
    private GameOfLifeUtil() {

    }

    /**
     * Convert a given Universe into a two dimensional array.
     *
     * @param rows
     * @param columns
     * @param cells
     * @return char[][]
     */
    public static char[][] convertToArray(final int rows, final int columns, final List<Cell> cells) {
        char[][] charArray = new char[rows][columns];
        for (Cell cell: cells) {
            charArray[cell.getRow()][cell.getColumn()] = State.ALIVE.equals(cell.getState()) ? 'X' : '-';
        }
        return charArray;

    }
}
