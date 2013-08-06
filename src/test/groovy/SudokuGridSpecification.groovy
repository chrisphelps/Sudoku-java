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
        def oldPossibilities = grid.getPossibilities(point)
        grid.eliminatePossibility(point,5)
        def newPossibilities = grid.getPossibilities(point)

        then:
        oldPossibilities.contains(5)
        !newPossibilities.contains(5)
        [1,2,3,4,6,7,8,9].every() { newPossibilities.contains(it) }
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

}
