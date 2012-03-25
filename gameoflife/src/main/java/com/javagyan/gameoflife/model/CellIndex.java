package com.javagyan.gameoflife.model;
/**
 * Helper class. Instances of this class represents the row and column index for each neighbor. It helps in the flow
 * where neighbors are being set for each cell.
 *
 * @author Sanjeev Kumar
 */
@Deprecated
public class CellIndex {

    /** Row index with respect to the universe. */
    private final int rowIndex;

    /** Column index with respect to the universe. */
    private final int colIndex;

    /**
     * Constructor.
     * @param row
     * @param col
     */
    public CellIndex(final int row, final int col) {
        rowIndex = row;
        colIndex = col;
    }

    /**
     * Getter for row index.
     * @return rowIndex
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Getter for column index.
     * @return colIndex
     */
    public int getColIndex() {
        return colIndex;
    }
}
