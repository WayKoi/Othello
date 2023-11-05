package othello;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static String Place = "\033[0;32m", Key = "\033[0;33m", Reset = "\033[0m", Black = "\033[0;35m", White = "\033[0;37m";

    public static void main(String[] args) {
        Game game = new Game();
        
        Scanner inp = new Scanner(System.in);
        
        // this is used to denote who is an AI or not
        // True means that there is a player, false means AI
        boolean[] players = { true, true };
        
        boolean countChosen = false;
        while (!countChosen) {
            System.out.print("How many players? (0, 1 or 2)\n> ");
            int amt = inp.nextInt();
            countChosen = true;
            
            switch (amt) {
                case 0:
                    players[0] = false;
                    players[1] = false;
                    break;
                case 1:
                    System.out.print("Play black or white? (0 = black, 1 = white)\n> ");
                    int col = Math.min(Math.max(inp.nextInt(), 0), 1);
                    
                    players[(col + 1) % 2] = false;
                    break;
                case 2:
                    break;
                default:
                    countChosen = false;
                    break;
            }
        }
        
        Space player = game.CurrentTurn();
        while (!game.IsGameOver()) {
            String turn = GetTurnName(game.CurrentTurn());
        
            System.out.println("\n" + turn + "\'s turn\n");
            PrintBoard(game.GetBoardString(), game.GetPossiblePositions());

            if (players[player.ordinal() - 1]) {
                boolean correct = false;
            
                while (!correct) {
                    System.out.print("Enter Move to make (x y)\n> ");
                    int x = inp.nextInt();
                    int y = inp.nextInt();
                    
                    correct = game.PlacePiece(new Pos(x, y));
                    if (!correct) {
                        System.out.println("That move is not allowed, try again");
                    }
                }
                
                if (!game.IsGameOver()) {
                    // if the turn player is the same after playing a move then the opponents turn was skipped
                    if (player == game.CurrentTurn()) {
                        System.out.println("\n" + GetTurnName(Board.GetReverse(game.CurrentTurn())) + " skips their turn");
                    }
                }
            } else {
                game.MakeAIMove();
            }
            
            player = game.CurrentTurn();
        }
        
        System.out.println("\nGame is Over!");
        System.out.println("The Winner is " + GetTurnName(game.GetWinner()) + "\nThe Final Board was\n");
        PrintBoard(game.GetBoardString(), null);
        
        inp.close();
    }
    
    private static String GetTurnName (Space colour) {
        switch (colour) {
            case White:
               return White + "White" + Reset;
            case Black:
                return Black + "Black" + Reset;
            case None:
                return "No one";
        }
        
        return "";
    }
    
    private static void PrintBoard (String boardString, ArrayList<Pos> possible) {
        String[] lines = boardString.split("\n");
        // ◎◉◦
        
        // header of the board
        System.out.print(Key + " ");
        for (int i = 0; i < Board.BoardSize; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println(Reset);
        
        for (int i = 1; i <= Board.BoardSize; i++) {
            String build = Key + i + " " + Reset;
            
            for (int ii = 1; ii <= Board.BoardSize; ii++) {
                char space = lines[i].charAt(ii);
            
                boolean found = false;
                if (possible != null && space == '.') {
                    for (Pos pos : possible) {
                        if (pos.X() == ii && pos.Y() == i) {
                            build += Place + "X " + Reset;
                            found = true;
                            break;
                        }
                    }
                }
                
                if (!found) {
                    switch (space) {
                        case '.':
                            build += ". ";
                            break;
                        case 'b':
                            build += Black + "O " + Reset;
                            break;
                        case 'w':
                            build += White + "O " + Reset;
                            break;
                    }
                }
            }
            
            System.out.println(build);
        }
    }
}
