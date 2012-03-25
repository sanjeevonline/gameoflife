package com.javagyan.gameoflife.util;

import java.util.ArrayList;
import java.util.List;

import com.javagyan.gameoflife.model.Cell;
import com.javagyan.gameoflife.model.State;
import com.javagyan.gameoflife.model.Universe;

/**
 * Factory class for creating Universe.
 * @author Sanjeev Kumar
 */
public class UniverseUtil {

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

    /**
     * Convert a given Universe into a two dimensional array.
     * @param rows
     * @param columns
     * @param cells
     * @return char[][]
     */
    public static char[][] convertToArray(final int rows, final int columns, final List<Cell> cells) {
        final char[][] charArray = new char[rows][columns];
        for (final Cell cell : cells) {
            charArray[cell.getRow()][cell.getColumn()] = State.ALIVE.equals(cell.getState()) ? 'X' : '-';
        }
        return charArray;

    }

    /**
     * Constructs the Universe by copying cells from provided universe.
     * @param input
     */
    public static Universe createCopyOfUniverse(final Universe universe) {
        return createUniverse(convertUniverseToArray(universe));
    }

    /**
     * Constructs the Universe using the two dimensional input array.
     * @param input
     */
    public static Universe createUniverse(final char[][] input) {
        final char[][] trimmed = trimDeadBoundaries(input);
        final int rows = trimmed.length;
        final int columns = trimmed[0].length;

        final List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells.add(new Cell(i, j, (trimmed[i][j] == ALIVE) ? State.ALIVE : State.DEAD));
            }
        }
        return new Universe(rows, columns, cells);
    }

    /**
     * Constructs the Universe using the two dimensional input array.
     * @param input
     */
    public static Universe createUniverse(final int rows, final int columns, final List<Cell> cells) {
        return createUniverse(convertToArray(rows, columns, cells));
    }

    /**
     * Convert universe to two dimensional array.
     * @param universe
     * @return
     */
    private static char[][] convertUniverseToArray(final Universe universe) {
        return convertToArray(universe.getRows(), universe.getColumns(), universe.getCells());
    }

    /**
     * Helper function that trims a dead row from bottom of the Universe
     * @param input
     * @return
     */
    private static char[][] trimBottomRow(final char[][] input) {
        final int newRows = input.length - 1;
        final int newColumns = input[0].length;
        final char[][] finalInputArray = new char[newRows][newColumns];
        for (int i = 0; i < (input.length - 1); i++) { // skip the last array
            finalInputArray[i] = input[i];
        }
        return finalInputArray;
    }

    /**
     * Recursive function that trims dead boundary of the universe.
     * @param input
     * @return
     */
    private static char[][] trimDeadBoundaries(final char[][] input) {
        if ((input.length == 1) && (input[0].length == 1) && (input[0][0] == DEAD)) {
            return input;
        }
        if (input.length == 0) {
            final char[][] deadUniverse = new char[1][1];
            deadUniverse[0][0] = DEAD;
            return deadUniverse;
        }
        final int rows = input.length;
        final int columns = input[0].length;

        boolean hasAliveCellsOnLeft = false;
        boolean hasAliveCellsOnRight = false;
        boolean hasAliveCellsOnTop = false;
        boolean hasAliveCellsOnBottom = false;
        char[][] finalInputArray = input;

        for (int i = 0; i < rows; i++) {
            if ((input[i][0] == ALIVE) && !hasAliveCellsOnLeft) {
                hasAliveCellsOnLeft = true;
            }
            if ((input[i][columns - 1] == ALIVE) && !hasAliveCellsOnRight) {
                hasAliveCellsOnRight = true;
            }
            if (hasAliveCellsOnLeft && hasAliveCellsOnRight) {
                break;
            }
        }
        for (int i = 0; i < columns; i++) {
            if ((input[0][i] == ALIVE) && !hasAliveCellsOnTop) {
                hasAliveCellsOnTop = true;
            }
            if ((input[rows - 1][i] == ALIVE) && !hasAliveCellsOnBottom) {
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
     * Helper function that trims a dead column row from left of the Universe
     * @param input
     * @return
     */
    private static char[][] trimLeftColumn(final char[][] input) {
        final int newRows = input.length;
        final int newColumns = input[0].length - 1;
        final char[][] finalInputArray = new char[newRows][newColumns];
        for (int i = 0; i < input.length; i++) { // skip the last value from each nested array
            for (int j = 0; j < newColumns; j++) {
                finalInputArray[i][j] = input[i][j + 1];
            }
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead column from right side of the Universe
     * @param input
     * @return
     */
    private static char[][] trimRightColumn(final char[][] input) {
        final int newRows = input.length;
        final int newColumns = input[0].length - 1;
        final char[][] finalInputArray = new char[newRows][newColumns];
        for (int i = 0; i < input.length; i++) { // skip the last value from each nested array
            for (int j = 0; j < newColumns; j++) {
                finalInputArray[i][j] = input[i][j];
            }
        }
        return finalInputArray;
    }

    /**
     * Helper function that trims a dead row from top of the Universe
     * @param input
     * @return
     */
    private static char[][] trimTopRow(final char[][] input) {
        final int newRows = input.length - 1;
        final int newColumns = input[0].length;
        final char[][] finalInputArray = new char[newRows][newColumns];
        for (int i = 0; i < (input.length - 1); i++) { // skip the first array
            finalInputArray[i] = input[i + 1];
        }
        return finalInputArray;
    }

    /** Private constructor so that construction of instance is avoided from outside the class. */
    private UniverseUtil() {
        // TODO Auto-generated constructor stub
    }
}
