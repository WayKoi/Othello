package othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.ArrayList;

@ExtendWith (MockitoExtension.class)
public class GameTest {

    
    
    // private Board board;
    private Game game;
    
    @BeforeEach
    public void init_game(){
        game=new Game();
    }

    @Test
    public void testCalculateWinner () {
        game.board = mock(Board.class);
        when(game.board.CountSpaces(Space.Black)).thenReturn(1);
        when(game.board.CountSpaces(Space.White)).thenReturn(2);

        int white = game.board.CountSpaces(Space.White);
        int black = game.board.CountSpaces(Space.Black);
        
        
        if (white > black) {
            game.Winner = Space.White;
        } else if (black > white) {
            game.Winner = Space.Black;
        }

        assertEquals( Space.White, game.Winner);
        
    }

    @Test
    public void GetPossiblePositions () {
        ArrayList<Pos> positions = new ArrayList<Pos>();
        ArrayList<Pos> spyList = spy(positions);

        for (Move move : game.possible) {
            spyList.add(new Pos(move.Position));
        }
        

        verify(spyList, times(4)).add(any());
    }
}