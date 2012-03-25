package com.javagyan.gameoflife.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.javagyan.gameoflife.model.Cell;
import com.javagyan.gameoflife.model.CellIndex;
import com.javagyan.gameoflife.model.State;
import com.javagyan.gameoflife.model.Universe;
import com.javagyan.gameoflife.util.UniverseUtil;

/**
 * This class exposes static methods that provide the business logic to generate next generation of cells in the
 * Universe and expand the Universe by adding rows and columns if and when required.
 * @author Sanjeev Kumar
 */
public class GameOfLifeService {

    /**
     * Add a row on top if universe has the ability to expand on left.
     * @param finalInputArray
     * @return char[][]
     */
    private static char[][] addColumnOnLeft(char[][] finalInputArray) {
        final int colCount = finalInputArray[0].length;
        final int rowCount = finalInputArray.length;
        final char[][] output = new char[rowCount][colCount + 1];
        for (int i = 0; i < rowCount; i++) {
            output[i][0] = UniverseUtil.DEAD;
            for (int j = 0; j < colCount; j++) {
                output[i][j + 1] = finalInputArray[i][j];
            }
        }
        finalInputArray = output;
        return finalInputArray;
    }

    /**
     * Add a row on top if universe has the ability to expand on right.
     * @param finalInputArray
     * @return char[][]
     */
    private static char[][] addColumnOnRight(char[][] finalInputArray) {
        final int colCount = finalInputArray[0].length;
        final int rowCount = finalInputArray.length;
        final char[][] output = new char[rowCount][colCount + 1];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                output[i][j] = finalInputArray[i][j];
            }
            output[i][colCount] = UniverseUtil.DEAD;
        }
        finalInputArray = output;
        return finalInputArray;
    }

    /**
     * Add a row on top if universe has the ability to expand at bottom.
     * @param finalInputArray
     * @return char[][]
     */
    private static char[][] addRowAtbottom(char[][] finalInputArray) {
        final int colCount = finalInputArray[0].length;
        final int rowCount = finalInputArray.length;
        final char[][] output = new char[rowCount + 1][colCount];

        final char[] charArray = new char[colCount];
        for (int i = 0; i < colCount; i++) {
            charArray[i] = UniverseUtil.DEAD;
        }
        for (int i = 0; i < rowCount; i++) {
            output[i] = finalInputArray[i];
        }
        output[rowCount] = charArray;
        finalInputArray = output;
        return finalInputArray;
    }

    /**
     * Add a row on top if universe has the ability to expand on top.
     * @param finalInputArray
     * @return char[][]
     */
    private static char[][] addRowOnTop(char[][] finalInputArray) {
        final int colCount = finalInputArray[0].length;
        final int rowCount = finalInputArray.length;
        final char[][] output = new char[rowCount + 1][colCount];

        final char[] charArray = new char[colCount];
        for (int i = 0; i < colCount; i++) {
            charArray[i] = UniverseUtil.DEAD;
        }
        output[0] = charArray;
        for (int i = 0; i < rowCount; i++) {
            output[i + 1] = finalInputArray[i];
        }
        finalInputArray = output;
        return finalInputArray;
    }

    /**
     * The method looks up the boundary ( first row, last row, first column, last Column) of the universe to check if
     * the next generation would need more space. It is possible that the next generation occupies more rows and columns
     * in the Universe as is the case with input toad pattern where input has two rows but the next generation has four
     * rows. Example: Input For Toad Pattern is 2X4 - X X X X X X - Next Generation is 4X4 - - X - X - - X X - - X - X -
     * -
     * @param input
     * @return
     */
    private static char[][] expandForNextGeneration(final char[][] input) {
        final int rows = input.length;
        final int columns = input[0].length; // each sub array will be of same length
        char[][] finalInputArray = input;
        int countinousActiveCellsOnLeftColumn = 0;
        int countinousActiveCellsOnRightColumn = 0;
        int countinousActiveCellsOnTopRow = 0;
        int countinousActiveCellsOnLowsetRow = 0;
        for (int i = 0; i < rows; i++) {
            if ((input[i][0] == UniverseUtil.ALIVE)
                    && (countinousActiveCellsOnLeftColumn != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnLeftColumn++;
            }
            if ((input[i][0] == UniverseUtil.DEAD) && (countinousActiveCellsOnLeftColumn != 0)
                    && (countinousActiveCellsOnLeftColumn != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnLeftColumn--;
            }

            if ((input[i][columns - 1] == UniverseUtil.ALIVE)
                    && (countinousActiveCellsOnRightColumn != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnRightColumn++;
            }
            if ((input[i][columns - 1] == UniverseUtil.DEAD) && (countinousActiveCellsOnRightColumn != 0)
                    && (countinousActiveCellsOnRightColumn != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnRightColumn--;
            }

            if ((countinousActiveCellsOnLeftColumn == UniverseUtil.BRING_TO_LIFE_COUNT)
                    && (countinousActiveCellsOnRightColumn == UniverseUtil.BRING_TO_LIFE_COUNT)) {
                break;
            }
        }
        for (int i = 0; i < columns; i++) {
            if ((input[0][i] == UniverseUtil.ALIVE)
                    && (countinousActiveCellsOnTopRow != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnTopRow++;
            }
            if ((input[0][i] == UniverseUtil.DEAD) && (countinousActiveCellsOnTopRow != 0)
                    && (countinousActiveCellsOnTopRow != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnTopRow--;
            }

            if ((input[rows - 1][i] == UniverseUtil.ALIVE)
                    && (countinousActiveCellsOnLowsetRow != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnLowsetRow++;
            }
            if ((input[rows - 1][i] == UniverseUtil.DEAD) && (countinousActiveCellsOnLowsetRow != 0)
                    && (countinousActiveCellsOnLowsetRow != UniverseUtil.BRING_TO_LIFE_COUNT)) {
                countinousActiveCellsOnLowsetRow--;
            }

            if ((countinousActiveCellsOnLowsetRow == UniverseUtil.BRING_TO_LIFE_COUNT)
                    && (countinousActiveCellsOnTopRow == UniverseUtil.BRING_TO_LIFE_COUNT)) {
                break;
            }
        }

        if (countinousActiveCellsOnLowsetRow == UniverseUtil.BRING_TO_LIFE_COUNT) {
            finalInputArray = addRowAtbottom(finalInputArray);
        }
        if (countinousActiveCellsOnTopRow == UniverseUtil.BRING_TO_LIFE_COUNT) {
            finalInputArray = addRowOnTop(finalInputArray);
        }
        if (countinousActiveCellsOnRightColumn == UniverseUtil.BRING_TO_LIFE_COUNT) {
            finalInputArray = addColumnOnRight(finalInputArray);
        }
        if (countinousActiveCellsOnLeftColumn == UniverseUtil.BRING_TO_LIFE_COUNT) {
            finalInputArray = addColumnOnLeft(finalInputArray);
        }

        return finalInputArray;
    }

    /**
     * Helper method to simplify the logic for finding neighboring cells and to avoid multiple lookup into the
     * collections of cells in the Universe.
     * @param neighbourCellIndex
     * @return
     */
    @Deprecated
    private static int getAliveNeighbourCount(final Set<CellIndex> neighbourCellIndex, final Universe universe) {
        int count = 0;
        for (final Cell cell : universe.getCells()) {
            for (final CellIndex cellIndex : neighbourCellIndex) {
                if ((cell.getRow() == cellIndex.getRowIndex()) && (cell.getColumn() == cellIndex.getColIndex())
                        && State.ALIVE.equals(cell.getState())) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Business method that returns a collection of progressive versions of Universe. To avoid infinite loop scenario,
     * only first 10 generations of the universe are kept for now in case Universe keeps evolving.
     * @param seed
     * @return List<Universe> list
     */
    public final List<Universe> play(final Universe seed) {
        final List<Universe> list = new ArrayList<Universe>();
        list.add(seed);
        return play(list, seed);
    }

    /**
     * Core logic of the Game of life. - Before calculating for new generation check if the Universe needs to expand its
     * area - apply business rules 1 Any live cell with fewer than two live neighbours dies, as if by loneliness. 2 Any
     * live cell with more than three live neighbours dies, as if by overcrowding. 3 Any live cell with two or three
     * live neighbours lives, unchanged, to the next generation. 4 Any dead cell with exactly three live neighbours
     * comes to life.
     * @param universe
     * @return Universe
     */
    @Deprecated
    private final Universe generateNextGeneration(final Universe universe) {
        final List<Cell> nextGeneration = new ArrayList<Cell>();
        for (final Cell cell : universe.getCells()) {
            final Set<CellIndex> neighbourCellIndex = new HashSet<CellIndex>();
            if (cell.getColumn() > 0) { // has a neighbor on left side in the same row
                neighbourCellIndex.add(new CellIndex(cell.getRow(), cell.getColumn() - 1));
            }
            if (cell.getColumn() < (universe.getColumns() - 1)) {// has a neighbor on right side in the same row
                neighbourCellIndex.add(new CellIndex(cell.getRow(), cell.getColumn() + 1));
            }
            if (cell.getRow() > 0) { // has a neighbor above in the same column
                neighbourCellIndex.add(new CellIndex(cell.getRow() - 1, cell.getColumn()));
            }
            if (cell.getRow() < (universe.getRows() - 1)) {// has a neighbor below in the same column
                neighbourCellIndex.add(new CellIndex(cell.getRow() + 1, cell.getColumn()));
            }
            if ((cell.getRow() > 0) && (cell.getColumn() > 0)) { // has a neighbor at diagonal Left Above
                neighbourCellIndex.add(new CellIndex(cell.getRow() - 1, cell.getColumn() - 1));
            }
            if ((cell.getRow() > 0) && (cell.getColumn() < (universe.getColumns() - 1))) { // has a neighbor at diagonal
                                                                                           // Right above
                neighbourCellIndex.add(new CellIndex(cell.getRow() - 1, cell.getColumn() + 1));
            }
            if ((cell.getRow() < (universe.getRows() - 1)) && (cell.getColumn() > 0)) { // has a neighbor at diagonal
                                                                                        // Left Below
                neighbourCellIndex.add(new CellIndex(cell.getRow() + 1, cell.getColumn() - 1));
            }
            if ((cell.getRow() < (universe.getRows() - 1)) && (cell.getColumn() < (universe.getColumns() - 1))) {
                // has a neighbor at diagonal Right Below
                neighbourCellIndex.add(new CellIndex(cell.getRow() + 1, cell.getColumn() + 1));
            }
            final int aliveNeighbourCount = getAliveNeighbourCount(neighbourCellIndex, universe);
            if (State.ALIVE.equals(cell.getState())) {
                if ((aliveNeighbourCount < UniverseUtil.LONELILESS_LIMIT)
                        || (aliveNeighbourCount > UniverseUtil.OVER_CROWDING_LIMIT)) {
                    nextGeneration.add(new Cell(cell.getRow(), cell.getColumn(), State.DEAD));
                } else {
                    nextGeneration.add(new Cell(cell.getRow(), cell.getColumn(), State.ALIVE));
                }
            } else {
                if (aliveNeighbourCount == UniverseUtil.BRING_TO_LIFE_COUNT) {
                    nextGeneration.add(new Cell(cell.getRow(), cell.getColumn(), State.ALIVE));
                } else {
                    nextGeneration.add(new Cell(cell.getRow(), cell.getColumn(), State.DEAD));
                }
            }
        }
        return UniverseUtil.createUniverse(universe.getRows(), universe.getColumns(), nextGeneration);
    }

    /**
     * Faster version of the logic to generate next generation. It deals with Array. Core logic of the Game of life. -
     * Before calculating for new generation check if the Universe needs to expand its area - apply business rules 1 Any
     * live cell with fewer than two live neighbours dies, as if by loneliness. 2 Any live cell with more than three
     * live neighbours dies, as if by overcrowding. 3 Any live cell with two or three live neighbours lives, unchanged,
     * to the next generation. 4 Any dead cell with exactly three live neighbours comes to life.
     * @param universe
     * @return Universe
     */
    private final Universe generateNextGenerationFaster(final Universe universe) {
        final char[][] charArray = UniverseUtil.convertToArray(
                universe.getRows(), universe.getColumns(), universe.getCells());
        final char[][] expandedArray = expandForNextGeneration(charArray); // expand boundaries for new Generations

        final int rows = expandedArray.length;
        final int columns = expandedArray[0].length;
        final char[][] nextGeneration = new char[rows][columns];

        for (int rowId = 0; rowId < expandedArray.length; rowId++) {
            for (int columnId = 0; columnId < expandedArray[0].length; columnId++) {
                int aliveNeighbourCount = 0;
                final char currentState = expandedArray[rowId][columnId];
                if ((columnId > 0) && (expandedArray[rowId][columnId - 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor on left side in the same row
                }
                if ((columnId < (columns - 1)) && (expandedArray[rowId][columnId + 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor on right side in the same row
                }
                if ((rowId > 0) && (expandedArray[rowId - 1][columnId] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor above in the same column
                }
                if ((rowId < (rows - 1)) && (expandedArray[rowId + 1][columnId] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor below in the same column
                }
                if ((rowId > 0) && (columnId > 0) && (expandedArray[rowId - 1][columnId - 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor at diagonal Left Above
                }
                if ((rowId > 0) && (columnId < (columns - 1))
                        && (expandedArray[rowId - 1][columnId + 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor at diagonal Right above
                }
                if ((rowId < (rows - 1)) && (columnId > 0)
                        && (expandedArray[rowId + 1][columnId - 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor at diagonal Left Below
                }
                if ((rowId < (rows - 1)) && (columnId < (columns - 1))
                        && (expandedArray[rowId + 1][columnId + 1] == UniverseUtil.ALIVE)) {
                    aliveNeighbourCount++; // has a neighbor at diagonal Right Below
                }
                if (currentState == UniverseUtil.ALIVE) {
                    if ((aliveNeighbourCount < UniverseUtil.LONELILESS_LIMIT)
                            || (aliveNeighbourCount > UniverseUtil.OVER_CROWDING_LIMIT)) {
                        nextGeneration[rowId][columnId] = UniverseUtil.DEAD; // dead
                    } else {
                        nextGeneration[rowId][columnId] = UniverseUtil.ALIVE; // alive
                    }
                } else {
                    if (aliveNeighbourCount == UniverseUtil.BRING_TO_LIFE_COUNT) {
                        nextGeneration[rowId][columnId] = UniverseUtil.ALIVE; // alive
                    } else {
                        nextGeneration[rowId][columnId] = UniverseUtil.DEAD; // dead
                    }
                }
            }
        }
        return UniverseUtil.createUniverse(nextGeneration);
    }

    /**
     * Recursive function that stops playing if one of the following three conditions occur. 1. Universe is dead (There
     * are no more Cells in ALIVE state so no scope of revival) 2. 10 generations of the Universe has been produced.
     * (For memory and performance concerns on my Laptop) 3. Universe patterns start repeating.
     * @param generations
     * @param genNext
     * @return List<Universe>
     */
    private List<Universe> play(final List<Universe> generations, final Universe genNext) {
        final Universe uni = generateNextGenerationFaster(genNext);
        for (final Universe universe : generations) {
            if ((universe.getAliveCells().size() == 0) || (generations.size() == UniverseUtil.GENERATIONS_LIMIT)) {
                return generations;
            }
            if (universe.equals(uni)) {
                generations.add(uni);
                return generations;
            }
        }
        generations.add(uni);
        return play(generations, uni);
    }
}
