package othello;

public class App {
    public static void main(String[] args) {
        Board board = new Board();
        
        System.out.println(board);
        board.PlayMove(board.GetPossibleMoves(Space.Black).get(0));
        board.PlayMove(board.GetPossibleMoves(Space.White).get(2));
        board.PlayMove(board.GetPossibleMoves(Space.Black).get(3));
        board.PlayMove(board.GetPossibleMoves(Space.White).get(1));
        System.out.println("\n\n" + board);
        System.out.println("\n\n" + board.GetPossibleMoves(Space.White));
        
    }
}
