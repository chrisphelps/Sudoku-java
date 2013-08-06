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
        rowPoints.contains(new CellPoint(3,0))
        rowPoints.contains(new CellPoint(3,1))
        rowPoints.contains(new CellPoint(3,2))
        rowPoints.contains(new CellPoint(3,3))
        rowPoints.contains(new CellPoint(3,5))
        rowPoints.contains(new CellPoint(3,6))
        rowPoints.contains(new CellPoint(3,7))
        rowPoints.contains(new CellPoint(3,8))
    }

    def "colPoints"() {
        given:
        def colPoints = grid.getColPoints(new CellPoint(3,4))

        expect:
        colPoints.size() == 8
        colPoints.contains(new CellPoint(0,4))
        colPoints.contains(new CellPoint(1,4))
        colPoints.contains(new CellPoint(2,4))
        colPoints.contains(new CellPoint(4,4))
        colPoints.contains(new CellPoint(5,4))
        colPoints.contains(new CellPoint(6,4))
        colPoints.contains(new CellPoint(7,4))
        colPoints.contains(new CellPoint(8,4))
    }

    def "peerPoints"() {
        given:
        def peerPoints = grid.getPeerPoints(new CellPoint(3,4))

        expect:
        peerPoints.size() == 8
        peerPoints.contains(new CellPoint(3,3))
        peerPoints.contains(new CellPoint(3,5))
        peerPoints.contains(new CellPoint(4,3))
        peerPoints.contains(new CellPoint(4,4))
        peerPoints.contains(new CellPoint(4,5))
        peerPoints.contains(new CellPoint(5,3))
        peerPoints.contains(new CellPoint(5,4))
        peerPoints.contains(new CellPoint(5,5))
    }

}
