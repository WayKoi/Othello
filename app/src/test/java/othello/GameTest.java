

package othello;

import java.io.File;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class GameTest {
    private static Game game = new Game();

    @Test
    @Order(1)
    void Setup() {
        System.out.println("Enter Setup");
        Pos pos = new Pos(3, 4);
        System.out.println("Position to be placed is " + pos.toString());
        game.PlacePiece(pos);
        System.out.println("Exit Setup");
    }

    @Test
    @Order(2)
    void SaveToFileTest() {
        assertTrue(game.SaveToFile("test.sav"));
    }

    @Test
    @Order(3)
    void PlayFirstPossibleMoveTest() {
        System.out.println("\nPlay a move");
        Space turn = game.CurrentTurn();
        Pos pos = game.GetPossiblePositions().get(0);

        System.out.println("Position to be placed is " + pos.toString());
        game.PlacePiece(pos);

        assertEquals(game.getBoardPiece(pos.X(), pos.Y()), turn);
        System.out.println("Exit");
    }

    @Test
    @Order(4)
    void LoadBoardTest() {
        game.LoadBoard("test.sav");
        assertEquals(game.CurrentTurn(), Space.White);
    }

    
    @ParameterizedTest
    @Order(5)
    @MethodSource("providePerameters")
    void LoadedBoardCorrectlyTest(int x, int y, Space space) {
        assertEquals(game.getBoardPiece(x, y), space);
    }

    private static Stream<Arguments> providePerameters() {
        return Stream.of(
            Arguments.of(3, 4, Space.Black),
            Arguments.of(4, 4, Space.Black),
            Arguments.of(5, 4, Space.Black),
            Arguments.of(4, 5, Space.Black),
            Arguments.of(5, 5, Space.White)
        );
    }

    @Test
    @Order(6)
    void cleanup() {
        File file = new File("test.sav");
        boolean success = file.delete();
        if (success) {
            System.out.println("\nDeleted the save file");
        } else {
            System.out.println("\nCouldn't Delete the save file");
        }
        assertTrue(success);
    }

}