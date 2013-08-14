package org.sutemi.sudoku;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/12/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuSolver {
    public SudokuGrid solve(SudokuGrid puzzle) {
        if (puzzle == null) {
            return null;
        }

        if (puzzle.isSolution()) {
            return puzzle;
        }

        CellPoint point = puzzle.getMinimumPossibilityCell();
        for (int poss : puzzle.getPossibilities(point)) {
            SudokuGrid solution = solve(puzzle.placeConjecture(point, poss));
            if (solution != null && solution.isSolution()) {
                return solution;
            }
        }

        return null;
    }

}
