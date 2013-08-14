package org.sutemi.sudoku;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/13/13
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class SolverMain {
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("USAGE: SolverMain <file> ...");
            System.exit(1);
        }
        for (String arg: args) {
            File file = new File(arg);
            processPuzzleFile(file);
        }
    }
    
    public static void processPuzzleFile(File file) {
        int solvedCount = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String puzzle = reader.readLine();
            while (puzzle != null) {
                if (!puzzle.equals("")) {
                    SudokuGrid grid = new SudokuGrid(puzzle);
                    SudokuSolver solver = new SudokuSolver();
                    SudokuGrid solution = solver.solve(grid);
                    if (solution != null) {
                        solvedCount += 1;
                        System.out.println("Puzzle solved");
                    }
                }
                puzzle = reader.readLine();
            }
        } catch (FileNotFoundException fnfe) {
            
        } catch (IOException ioe) {

        }
        System.out.println("File : " + file.getName() + " solved puzzles: " + solvedCount);
    }
}
