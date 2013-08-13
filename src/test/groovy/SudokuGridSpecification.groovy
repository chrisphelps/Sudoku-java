/**
 * Created with IntelliJ IDEA.
 * User: chris
 * Date: 8/5/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */

import org.sutemi.sudoku.SudokuGrid
import org.sutemi.sudoku.CellPoint
import spock.lang.*

class SudokuGridSpecification extends Specification {
    def grid = new SudokuGrid()

    def "rowPoints"() {
        given:
        def rowPoints = grid.getRowPoints(new CellPoint(3,4))

        expect:
        rowPoints.size() == 8
        [0,1,2,3,5,6,7,8].every(){ rowPoints.contains(new CellPoint(3,it)) }
    }

    def "colPoints"() {
        given:
        def colPoints = grid.getColPoints(new CellPoint(3,4))

        expect:
        colPoints.size() == 8
        [0,1,2,4,5,6,7,8].every(){ colPoints.contains(new CellPoint(it,4)) }
    }

    def "peerPoints"() {
        given:
        def peerPoints = grid.getPeerPoints(new CellPoint(3,4))

        expect:
        peerPoints.size() == 8
        [[3,3],[3,5],[4,3],[4,4],[4,5],[5,3],[5,4],[5,5]].every() {
            peerPoints.contains(new CellPoint(it[0],it[1]))
        }
    }

    def "all possibilities"(int row, int col) {
        expect:
        [1,2,3,4,5,6,7,8,9].every() { grid.getPossibilities(new CellPoint(row,col)).contains(it) }

        where:
        row << [0,1,2,3,4,5,6,7,8]
        col << [0,1,2,3,4,5,6,7,8]
    }

    def "remove possibility without propagation"() {
        given:
        def point = new CellPoint(3,4)

        when:
        def newgrid = grid.eliminatePossibility(point,5)

        then:
        // original grid unchanged
        grid.getPossibilities(point).contains(5)
        // newgrid is updated
        !newgrid.getPossibilities(point).contains(5)
        [1,2,3,4,6,7,8,9].every() { newgrid.getPossibilities(point).contains(it) }
    }

    def "place given"() {
        when:
        grid.placeGiven(new CellPoint(3,4),5)

        then:
        grid.getPossibilities(new CellPoint(3,4)).size() == 1
        [0,1,2,4,5,6,7,8].every() { grid.getPossibilities(new CellPoint(it,4)).size() == 8 }
        [0,1,2,3,5,6,7,8].every() { grid.getPossibilities(new CellPoint(3,it)).size() == 8 }
    }

    def "place conjecture"() {
        when:
        def newgrid = grid.placeConjecture(new CellPoint(3,4),5)

        then:
        // original grid unchanged
        [0,1,2,3,4,5,6,7,8].every() { grid.getPossibilities(new CellPoint(it,4)).size() == 9 }
        [0,1,2,3,4,5,6,7,8].every() { grid.getPossibilities(new CellPoint(3,it)).size() == 9 }
        // newgrid is updated
        newgrid.getPossibilities(new CellPoint(3,4)).size() == 1
        [0,1,2,4,5,6,7,8].every() { newgrid.getPossibilities(new CellPoint(it,4)).size() == 8 }
        [0,1,2,3,5,6,7,8].every() { newgrid.getPossibilities(new CellPoint(3,it)).size() == 8 }
    }

    def "remove possibility and propagate"() {
        given:
        def point = new CellPoint(3,4)
        def newgrid = grid
        for(i in 1..7) {
            newgrid = newgrid.eliminatePossibility(point,i)
        }

        when:
        newgrid = newgrid.eliminatePossibility(point,8) // remove second to last

        then:
        // original grid unchanged
        grid.getPossibilities(point).size() == 9
        [0,1,2,3,4,5,6,7,8].every() { grid.getPossibilities(new CellPoint(it,4)).size() == 9 }
        [0,1,2,3,4,5,6,7,8].every() { grid.getPossibilities(new CellPoint(3,it)).size() == 9 }
        // newgrid is updated
        newgrid.getPossibilities(new CellPoint(3,4)).size() == 1
        [0,1,2,4,5,6,7,8].every() { newgrid.getPossibilities(new CellPoint(it,4)).size() == 8 }
        [0,1,2,3,5,6,7,8].every() { newgrid.getPossibilities(new CellPoint(3,it)).size() == 8 }
    }

    def "place conjecture fails when conjecture is not possible"() {
        given:
        def newgrid = grid.placeConjecture(new CellPoint(3,4),5)

        expect:
        newgrid.placeConjecture(new CellPoint(5,4),5) == null
    }

    def "new grid is not a solution"() {
        expect:
        grid.isSolution() == false
    }

    def "full grid is a solution"() {
        given:
        [
                [0,0,1],[0,1,4],[0,2,7],[0,3,2],[0,4,5],[0,5,8],[0,6,3],[0,7,6],[0,8,9],
                [1,0,2],[1,1,5],[1,2,8],[1,3,3],[1,4,6],[1,5,9],[1,6,1],[1,7,4],[1,8,7],
                [2,0,3],[2,1,6],[2,2,9],[2,3,1],[2,4,4],[2,5,7],[2,6,2],[2,7,5],[2,8,8],
                [3,0,4],[3,1,7],[3,2,1],[3,3,8],[3,4,2],[3,5,5],[3,6,9],[3,7,3],[3,8,6],
                [4,0,5],[4,1,8],[4,2,2],[4,3,9],[4,4,3],[4,5,6],[4,6,7],[4,7,1],[4,8,4],
                [5,0,6],[5,1,9],[5,2,3],[5,3,7],[5,4,1],[5,5,4],[5,6,8],[5,7,2],[5,8,5],
                [6,0,7],[6,1,1],[6,2,4],[6,3,5],[6,4,8],[6,5,2],[6,6,6],[6,7,9],[6,8,3],
                [7,0,8],[7,1,2],[7,2,5],[7,3,6],[7,4,9],[7,5,3],[7,6,4],[7,7,7],[7,8,1],
                [8,0,9],[8,1,3],[8,2,6],[8,3,4],[8,4,7],[8,5,1],[8,6,5],[8,7,8],[8,8,2],
        ].each() { grid.placeGiven(new CellPoint(it[0],it[1]),it[2])}

        expect:
        grid.isSolution() == true
    }

    def "toString for empty grid"() {
        given:
        def expectedgrid =
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . .\n" +
                "---------------------\n" +
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . .\n" +
                "---------------------\n" +
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . .\n" +
                ". . . | . . . | . . ."

        expect:
        grid.toString() == expectedgrid
    }

    def "toString for partially filled grid"() {
        given:
        grid.placeGiven(new CellPoint(0,0),1)
        grid.placeGiven(new CellPoint(3,4),5)
        def expectedgrid =
            "1 . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . 5 . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . ."

        expect:
        grid.toString() == expectedgrid
    }


    def "initialize with List<List<Integer>>"() {
        given:
            def initgrid = [
            [1,0,0,2,0,0,3,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,5,0,0,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0],
            [0,0,0,0,0,0,0,0,0]
            ]
        def expectedgrid =
            "1 . . | 2 . . | 3 . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . 5 . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . ."

        expect:
         new SudokuGrid(initgrid).toString() == expectedgrid
    }

    def "initialize with too few rows"() {
        given:
        def initgrid = [
                [1,0,0,2,0,0,3,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
        ]
        when:
        new SudokuGrid(initgrid)

        then:
        thrown(IllegalArgumentException)
    }

    def "initialize with too few cols"() {
        given:
        def initgrid = [
                [1,0,0,2,0,0,3,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0]
        ]
        when:
        new SudokuGrid(initgrid)

        then:
        thrown(IllegalArgumentException)
    }

    def "minimum cell of empty grid is (0,0)"() {
        expect:
        grid.getMinimumPossibilityCell().getRow() == 0
        grid.getMinimumPossibilityCell().getCol() == 0
    }

    def "minimum cell of nonempty grid is minimum"() {
        given:
        def initgrid = [
                [1,0,0,2,0,0,3,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,5],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0],
                [0,0,0,0,0,0,0,0,0]
        ]
        def newgrid = new SudokuGrid(initgrid)

        expect:
        newgrid.getMinimumPossibilityCell().getRow() == 0
        newgrid.getMinimumPossibilityCell().getCol() == 8
    }

    def "init with null string"() {
        when:
        def newgrid = new SudokuGrid((String)null)

        then:
        thrown(IllegalArgumentException)
    }

    def "init with too many givens in string"() {
        when:
        def newgrid = new SudokuGrid("4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......9")

        then:
        thrown(IllegalArgumentException)
    }

    def "init with too few givens in string"() {
        when:
        def newgrid = new SudokuGrid("4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4....")

        then:
        thrown(IllegalArgumentException)
    }

    def "initialize with string with zeroes"() {
        given:
        def initgrid = "100200300000000000000000000000050000000000000000000000000000000000000000000000000"
        def expectedgrid =
            "1 . . | 2 . . | 3 . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . 5 . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            "---------------------\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . .\n" +
            ". . . | . . . | . . ."

        expect:
        new SudokuGrid(initgrid).toString() == expectedgrid
    }

    def "initialize with string with dots"() {
        given:
        def initgrid = "1..2..3........................5................................................."
        def expectedgrid =
            "1 . . | 2 . . | 3 . .\n" +
                    ". . . | . . . | . . .\n" +
                    ". . . | . . . | . . .\n" +
                    "---------------------\n" +
                    ". . . | . 5 . | . . .\n" +
                    ". . . | . . . | . . .\n" +
                    ". . . | . . . | . . .\n" +
                    "---------------------\n" +
                    ". . . | . . . | . . .\n" +
                    ". . . | . . . | . . .\n" +
                    ". . . | . . . | . . ."

        expect:
        new SudokuGrid(initgrid).toString() == expectedgrid
    }

    def "solve by constraint propagation"() {
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

        expect:
        newgrid.toString() == expectedgrid
        newgrid.isSolution()
    }
}
