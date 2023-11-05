package othello;

public class Pos {
    private int x = 1, y = 1;
    
    public int X () {
        return x;
    }
    
    public int X (int val) {
        x = Math.min(Math.max(val, 1), Board.BoardSize);
        return x;
    }
    
    public int Y () {
        return y;
    }
    
    public int Y (int val) {
        y = Math.min(Math.max(val, 1), Board.BoardSize);
        return y;
    }
    
    public Pos (int x, int y) {
        X(x);
        Y(y);
    }
    
    public Pos (Pos dupe) {
        X(dupe.X());
        Y(dupe.Y());
    }
    
    @Override
    public String toString () {
        return String.format("(%d, %d)", x, y);
    }
}
