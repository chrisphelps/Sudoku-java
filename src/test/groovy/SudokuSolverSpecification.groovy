import org.sutemi.sudoku.SudokuGrid
import org.sutemi.sudoku.SudokuSolver
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/12/13
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
class SudokuSolverSpecification extends Specification {
    def solver = new SudokuSolver()

    def "solve null puzzle"() {
        expect:
        solver.solve(null) == null
    }

    def "solve already-solved puzzle"() {
        given:
        def initgrid = "003020600900305001001806400008102900700000008006708200002609500800203009005010300"
        def expectedgrid =
            "4 8 3 | 9 2 1 | 6 5 7\n" +
                    "9 6 7 | 3 4 5 | 8 2 1\n" +
                    "2 5 1 | 8 7 6 | 4 9 3\n" +
                    "---------------------\n" +
                    "5 4 8 | 1 3 2 | 9 7 6\n" +
                    "7 2 9 | 5 6 4 | 1 3 8\n" +
                    "1 3 6 | 7 9 8 | 2 4 5\n" +
                    "---------------------\n" +
                    "3 7 2 | 6 8 9 | 5 1 4\n" +
                    "8 1 4 | 2 5 3 | 7 6 9\n" +
                    "6 9 5 | 4 1 7 | 3 8 2"
        def newgrid = new SudokuGrid(initgrid)
        def solution = solver.solve(newgrid)

        expect:
        solution.toString() == expectedgrid
        solution.isSolution()
    }

    def "solve search puzzle"() {
        given:
        def initgrid = "4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......"
        def expectedgrid =
            "4 1 7 | 3 6 9 | 8 2 5\n" +
            "6 3 2 | 1 5 8 | 9 4 7\n" +
            "9 5 8 | 7 2 4 | 3 1 6\n" +
            "---------------------\n" +
            "8 2 5 | 4 3 7 | 1 6 9\n" +
            "7 9 1 | 5 8 6 | 4 3 2\n" +
            "3 4 6 | 9 1 2 | 7 5 8\n" +
            "---------------------\n" +
            "2 8 9 | 6 4 3 | 5 7 1\n" +
            "5 7 3 | 2 9 1 | 6 8 4\n" +
            "1 6 4 | 8 7 5 | 2 9 3"
        def newgrid = new SudokuGrid(initgrid)
        def solution = solver.solve(newgrid)

        expect:
        solution.toString() == expectedgrid
        solution.isSolution()
    }
}
