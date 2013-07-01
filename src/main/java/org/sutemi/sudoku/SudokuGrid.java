package org.sutemi.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 6/5/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SudokuGrid {
    List[][] grid;

    public SudokuGrid() {
        grid = new List[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new ArrayList<Integer>();
                for (int k = 1; k < 10; k++) {
                    grid[i][j].add(k);
                }
            }
        }
    }

    public void eliminatePossibility(CellPoint point, Integer possibility) {
        grid[point.row][point.col].remove(possibility);
    }

    public List<CellPoint> getRowPoints(CellPoint point) {
        List<CellPoint> rowPoints = new ArrayList<CellPoint>();
        for (int i = 0; i < 9; i++) {
            if (i != point.getCol()) {
                rowPoints.add(new CellPoint(point.getRow(), i));
            }
        }
        return rowPoints;
    }

    public List<CellPoint> getColPoints(CellPoint point) {
        List<CellPoint> colPoints = new ArrayList<CellPoint>();
        for (int i = 0; i < 9; i++) {
            if (i != point.getRow()) {
                colPoints.add(new CellPoint(i, point.getCol()));
            }
        }
        return colPoints;
    }

    public List<CellPoint> getPeerPoints(CellPoint point) {
        int rowOffset = point.getRow() / 3; //int division
        int colOffset = point.getCol() / 3; //int division
        List<CellPoint> peerPoints = new ArrayList<CellPoint>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int row = i + (rowOffset * 3);
                int col = j + (colOffset * 3);
                if (!(row == point.getRow() && col == point.getCol())) {
                    peerPoints.add(new CellPoint(row,col));
                }
            }
        }
        return peerPoints;
    }

    public List<Integer> getPossibilities(CellPoint cellPoint) {
        return grid[cellPoint.getRow()][cellPoint.getCol()];

    }
}
