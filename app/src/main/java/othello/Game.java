package othello;

import java.util.ArrayList;

public class Game {
    private Board board = new Board();
    private Space turn = Space.Black; // Black always moves first
    
    private ArrayList<Move> possible;
    private int passes = 0;
    
    private boolean GameOver = false;
    
    public boolean IsGameOver () {
        return GameOver;
    }
    
    private Space Winner = Space.None;
    
    public Space GetWinner () {
        return Winner;
    }
    
    public Game () {
        possible = board.GetPossibleMoves(turn);
    }
    
    public Space CurrentTurn () {
        return turn;
    }
    
    public ArrayList<Pos> GetPossiblePositions () {
        ArrayList<Pos> positions = new ArrayList<Pos>();
        for (Move move : possible) {
            positions.add(new Pos(move.Position));
        }
        
        return positions;
    }
    
    public String GetBoardString () {
        return board.toString();
    }
    
    public boolean PlacePiece (Pos position) {
        if (GameOver) { return false; }
    
        Move make = null;
    
        for (Move move : possible) {
            if (move.Position.X() == position.X() && move.Position.Y() == position.Y()) {
                make = move;
                break;
            }
        }
        
        if (make != null) {
            board.PlayMove(make);
            SwitchTurn();
            return true;
        }
        
        return false;
    }
    
    public void MakeAIMove () {
        Move chosen = AI.ChooseMove(possible);
        PlacePiece(chosen.Position);
    }
    
    private void SwitchTurn () {
        if (GameOver) { return; }
    
        if (passes == 2) {
            // we have passed twice the game is over
            GameOver = true;
            turn = Space.None;
            possible.clear();
            
            CalculateWinner();
            return;
        }
        
        turn = Board.GetReverse(turn);
        possible = board.GetPossibleMoves(turn);
        
        if (possible.size() == 0) { 
            passes++; 
            SwitchTurn(); 
        } else {
            passes = 0;
        }
    }
    
    private void CalculateWinner () {
        int white = board.CountSpaces(Space.White);
        int black = board.CountSpaces(Space.Black);
        
        if (white > black) {
            Winner = Space.White;
        } else if (black > white) {
            Winner = Space.Black;
        }
    }
}
