package othello;

import java.util.ArrayList;

public class Board {
    public static final int BoardSize = 8;
    
    public static Space GetReverse (Space rev) {
        if (rev == Space.None) { return Space.None; }
        return (rev == Space.White) ? Space.Black : Space.White;
    }

    private Space[][] board = new Space[BoardSize + 2][BoardSize + 2];
    
    public Board () {
        for (int i = 0; i < BoardSize + 2; i++) {
            for (int ii = 0; ii < BoardSize + 2; ii++) {
                board[i][ii] = Space.None;
            }
        }
    
        board[(BoardSize + 2) / 2 - 1][(BoardSize + 2) / 2 - 1] = Space.White;
        board[(BoardSize + 2) / 2][(BoardSize + 2) / 2] = Space.White;
        board[(BoardSize + 2) / 2 - 1][(BoardSize + 2) / 2] = Space.Black;
        board[(BoardSize + 2) / 2][(BoardSize + 2) / 2 -1] = Space.Black;
    }
    
    /***
     * This function is used to change a spot on the board to a specific type without following the rules, allows placing of pieces on x and y 1 to BoardSize
     * @param type the type of space to place
     * @param x X position
     * @param y Y position
     */
    public void ReplacePiece (Space type, int x, int y) {
        if (x < 1 || x > BoardSize || y < 1 || y > BoardSize) { return; }
        board[x][y] = type;
    }
    
    public void PlayMove (Move move) {
        board[move.Position.X()][move.Position.Y()] = move.Type();
        
        if (move.Flips != null) {
            for (Pos pos : move.Flips) {
                Space colour = board[pos.X()][pos.Y()];
                board[pos.X()][pos.Y()] = GetReverse(colour);
            }
        }
    }
    
    public ArrayList<Move> GetPossibleMoves (Space colour) {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (colour == Space.None) { return moves; }
        
        for (int i = 1; i <= BoardSize; i++) {
            for (int ii = 1; ii <= BoardSize; ii++) {
                if (board[i][ii] != Space.None) { continue; }
                
                ArrayList<Pos> flips = GetFlips(colour, i, ii);
                
                if (flips.size() > 0) {
                    // A move can only be made if it flips another piece
                    moves.add(new Move(new Pos(i, ii), colour, flips));
                }
            }
        }
        
        return moves;
    }
    
    private ArrayList<Pos> GetFlips (Space colour, int x, int y) {
        ArrayList<Pos> flips = new ArrayList<Pos>();
        if (colour == Space.None || x < 1 || x > BoardSize || y < 1 || y > BoardSize) { return flips; }
        
        Space look = GetReverse(colour);
        
        for (int i = -1; i <= 1; i++) {
            for (int ii = -1; ii <= 1; ii++) {
                LookInDirection(flips, colour, look, x, y, i, ii);
            }
        }
        
        return flips;
    }
    
    private void LookInDirection (ArrayList<Pos> flips, Space colour, Space look, int startx, int starty, int changex, int changey) {
        if (changex == 0 && changey == 0) { return; }
        ArrayList<Pos> temflips = new ArrayList<>();
        
        for (int i = 1; i < BoardSize; i++) {
            Space check = board[startx + changex * i][starty + changey * i];
            
            if (check == look) {
                temflips.add(new Pos(startx + changex * i, starty + changey * i));
            } else if (check == colour) {
                flips.addAll(temflips);
                break;
            } else {
                break;
            }
        }
    }
    
    @Override
    public String toString () {
        String build = "";
    
        String chars = ".wb";
    
        for (int i = 0; i < BoardSize + 2; i++) {
            for (int ii = 0; ii < BoardSize + 2; ii++) {
                build += chars.charAt(board[ii][i].ordinal()) + " ";
            }
            
            build += '\n';
        }
    
        return build;
    }
}
