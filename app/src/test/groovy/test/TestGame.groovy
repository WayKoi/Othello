package othello

import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Shared

@Stepwise
class TestGame extends Specification {
    @Shared def game = new Game()
    
    def setupSpec() {
        println "Enter Setup"
        def pos = new Pos(3, 4)
        println "Positon Placed is " + pos.toString()
        game.PlacePiece(pos)
        println "Exit Setup"
    }
    
    def "Save to file"() {
        expect:
        game.SaveToFile("test.sav")
    }
    
    def "Play a move"() {
        setup:
        println "\nPlay a move"
        def turn = game.CurrentTurn()
        def pos = game.GetPossiblePositions()[0]
        
        println "Positon Placed is " + pos.toString()
        game.PlacePiece(pos)
        println "Exit"
        
        expect:
        game.board.board[pos.x][pos.y] == turn
    }
    
    def "Load a board"() {
        expect:
        game.LoadBoard("test.sav")
        game.CurrentTurn() == Space.White
    }
    
    def "Board loaded Correctly"() {
        expect:
        game.board.board[x][y] == space
        
        where:
        x << [3, 4, 5, 4, 5]
        y << [4, 4, 4, 5, 5]
        space << [Space.Black, Space.Black, Space.Black, Space.Black, Space.White]
    }
    
    def cleanupSpec () {
        def file = new File("test.sav")
        if (file.delete()) {
            println "\nDeleted the save file"    
        } else {
            println "\nCouldn't Delete the save file"
        }
    }
}