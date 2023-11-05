package othello;

import java.util.ArrayList;
import java.util.Random;

public class AI {
    public static Move ChooseMove (ArrayList<Move> moves) {
        ArrayList<Move> poss = new ArrayList<Move>();
        int max = -100;
        
        for (Move move : moves) {
            int check = Eval(move);
            if (check > max) {
                max = check;
                poss.clear();
                poss.add(move);
            } else if (check == max) {
                poss.add(move);
            }
        }
        
        Random rand = new Random();
        
        int chosen = rand.nextInt(poss.size());
        return poss.get(chosen);
    }
    
    private static int Eval (Move move) {
        int score = 0;
        
        if (move.Position.X() == 1 || move.Position.X() == Board.BoardSize) {
            score += 5;
        } else if (move.Position.X() == 2 || move.Position.X() == Board.BoardSize - 1) {
            score -= 3;
        }
        
        if (move.Position.Y() == 1 || move.Position.Y() == Board.BoardSize) {
            score += 5;
        } else if (move.Position.Y() == 2 || move.Position.Y() == Board.BoardSize - 1) {
            score -= 3;
        }
        
        score += move.Flips.size();
        
        return score;
    }
}
