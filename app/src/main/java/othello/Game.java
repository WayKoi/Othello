package othello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public Space getBoardPiece(int x, int y) {
        return board.getPiece(x, y);
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
    
    public boolean SaveToFile (String path) {
        // File is formatted like below
        /*
        turn player (b or w)
        ..........
        ...b.w....
        ..wb.bwwb.
        .wbwbwwbw.
        .bbw.b....
        ..........
         */
         
        try {
            File test = new File(path);
            if (!test.exists() && !test.isDirectory()) {
                test.createNewFile();
            }
            
            String types = ".bw";
            FileWriter write = new FileWriter(path);
            write.append(types.charAt(turn.ordinal()));
            write.append("\n" + board.toString());
            write.close();
            
        } catch (IOException e) {
            return false;
        }
        
        return true;
    }
    
    public boolean LoadBoard (String path) {
        try {
            File test = new File(path);
            if (test.exists() && !test.isDirectory()) {
                ArrayList<String> lines = new ArrayList<String>();
                
                Scanner scan = new Scanner(test);
                while (scan.hasNextLine()) {
                    lines.add(scan.nextLine());
                }
                scan.close();
                
                switch (lines.get(0).charAt(0)) {
                    case '.':
                        turn = Space.None;
                        break;
                    case 'b':
                        turn = Space.Black;
                        break;
                    case 'w':
                        turn = Space.White;
                        break;
                }
                
                lines.remove(0);

                board = new Board();
                for (int i = 1; i <= Board.BoardSize; i++) {
                    for (int ii = 1; ii <= Board.BoardSize; ii++) {
                        Space spot = Space.None;
                        switch (lines.get(i).charAt(ii)) {
                            case '.':
                                spot = Space.None;
                                break;
                            case 'b':
                                spot = Space.Black;
                                break;
                            case 'w':
                                spot = Space.White;
                                break;
                        }
                    
                        board.ReplacePiece(spot, ii, i);
                    }
                }
                
                possible = board.GetPossibleMoves(turn);
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        } catch (FileNotFoundException e) {
            return false;
        }
        
        return true;
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
