

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Universe as collection of cells.
 *
 * @author Sanjeev Kumar
 *
 */
public final class Universe {
    /**
     * Boundary parameters of Universe. Represents rows in the Universe.
     */
    private final int rows;

    /**
     * Boundary parameters of Universe. Represents columns in the Universe.
     */
    private final int columns;

    /**
     * Cells that constitute the Universe.
     */
    private final List<Cell> cells;

    /**
     * Constructor.
     *
     * @param rows
     * @param columns
     * @param cells
     */
    public Universe(final int rows, final int columns, final List<Cell> cells) {
        this.rows = rows;
        this.columns = columns;
        this.cells = cells;
    }

    /**
     * Returns the count of rows in the Universe.
     *
     * @return rows in the Universe
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the count of columns in the Universe.
     *
     * @return int
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns unmodified collection of cells.
     *
     * @return int
     */
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Retrieves alive cells of the Universe.
     *
     * To simplify the logic of checking equality of two Universe Instances only
     * ALIVE cells shall be matched
     *
     * @return List<Cell>
     */
    public List<Cell> getAliveCells() {
        List<Cell> aliveCells = new ArrayList<Cell>();
        for (Cell cell : getCells()) {
            if (State.ALIVE.equals(cell.getState())) {
                aliveCells.add(cell);
            }
        }
        return aliveCells;
    }

    /**
     * typical hash code generation algorithm.
     * @return int
     */
    @Override
    public int hashCode() {
        return (rows * 31) ^ columns;
    }

    /**
     * Check if the two version of Universe have same set of alive fields.
     * @return boolean
     */
    @Override
    public boolean equals(final Object o) {

        if (o instanceof Universe) {
            Universe otherUniverse = (Universe) o;
            if (!(this.getAliveCells().size() == otherUniverse.getAliveCells().size())) {
                return false;
            }
            for (Cell cell : this.getAliveCells()) { // match only live cells
                boolean found = false;
                for (Cell _cell: otherUniverse.getAliveCells()) {
                    if (cell.getRow() == _cell.getRow() && cell.getColumn() == _cell.getColumn()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Creates the visual representation of the Universe by creating a two dimensional view displaying Alive cells as
     * 'X' and dead cells as '-'.
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder strBldr = new StringBuilder();
        int colIndex = 1;
        for (Cell cell : cells) {
            if (State.ALIVE.equals(cell.getState())) {
                strBldr.append(" X");
            } else {
                strBldr.append(" -");
            }
            if (colIndex == columns) {
                strBldr.append("\n");
                colIndex = 0;
            }
            colIndex++;
        }
        return strBldr.toString();
    }
}