package othello;

import java.util.ArrayList;

public class Move {
    public Pos Position;
    private Space type = Space.Black;
    
    public Space Type () {
        return type;
    }
    
    public Space Type (Space change) {
        if (change == Space.None) { return type; }
        type = change;
        return type;
    }
    
    public ArrayList<Pos> Flips = new ArrayList<Pos>();
    
    public Move (Pos position, Space piece) {
        Position = position;
        Type(piece);
    }
    
    public Move (Pos position, Space piece, ArrayList<Pos> flips) {
        Position = position;
        Type(piece);
        Flips = flips;
    }
    
    @Override
    public String toString () {
        String build = "";
        
        build += (type == Space.Black ? 'b' : 'w');
        build += "@" + Position.toString();
        
        if (Flips != null) {
            int size = Flips.size();
            build += "f:";
            for (int i = 0; i < size; i++) {
                build += Flips.get(i).toString();
            }
        }
        
        return build;
    }
}
