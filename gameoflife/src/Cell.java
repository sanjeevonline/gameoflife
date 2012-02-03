/**
 * Cell is the smallest building block of Universe.
 * @author Sanjeev Kumar
 */
public final class Cell {
    /**
     * State of the cell. One of the two possible values i.e. ALIVE or DEAD
     */
    private final State state;

    /**
     * Column index of the cell in the Universe.
     */
    private final int column;

    /**
     * Row index of the cell in the Universe.
     */
    private final int row;

    /**
     * Constructor.
     *
     * @param row1
     * @param column1
     * @param state1
     */
    public Cell(final int row1, final int column1, final State state1) {
        row         = row1;
        column      = column1;
        state       = state1;
    }

    /**
     * gets State of the Cell.
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * gets the column index of the cell.
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * gets row index of the cell.
     * @return row
     */
    public int getRow() {
        return row;
    }
}
