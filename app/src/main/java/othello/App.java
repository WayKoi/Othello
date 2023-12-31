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
            System.out.print("How many players? (0, 1 or 2) or load a file with \"l (filename)\"\n> ");
            String[] read = inp.nextLine().split(" ");
            
            if (read.length > 1 && read[0].charAt(0) == 'l') {
                countChosen = game.LoadBoard(read[1]);
                if (!countChosen) {
                    System.out.println("File could not be read");
                }
            } else {
                try {
                    int amt = Integer.parseInt(read[0]);
                    countChosen = true;
                    
                    switch (amt) {
                        case 0:
                            players[0] = false;
                            players[1] = false;
                            break;
                        case 1:
                            boolean correct = false;
                        
                            while (!correct) {
                                System.out.print("Play black or white? (0 = black, 1 = white)\n> ");
                                String input = inp.nextLine();
                                
                                try {
                                    int col = Math.min(Math.max(Integer.parseInt(input), 0), 1);
                                    
                                    players[(col + 1) % 2] = false;
                                    correct = true;
                                } catch (NumberFormatException e) {
                                    System.out.println("That is not a valid input");
                                }
                            }
                            
                            break;
                        case 2:
                            break;
                        default:
                            countChosen = false;
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Please enter a number");
                }
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
                    System.out.print("\nEnter Move to make (x y)\n> ");
                    String input = inp.nextLine();
                    if (input.length() <= 0)  { continue; }
                    String[] split = input.split(" ");
                    
                    try {
                        if (split.length > 1) {
                            if (split[0].equals("s")) {
                                correct = game.SaveToFile(split[1]);
                                if (!correct) {
                                    System.out.println("Unable to save to file");
                                }
                            } else {
                                int x = Integer.parseInt(split[0]);
                                int y = Integer.parseInt(split[1]);
                                
                                correct = game.PlacePiece(new Pos(x, y));
                                if (!correct) {
                                    System.out.println("That move is not allowed, try again");
                                }
                            }
                        } else {
                            if (split[0].charAt(0) == 'q') {
                                System.out.println("Thanks for playing!");
                                System.exit(0);
                            }
                            System.out.println("That move is not allowed, try again");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("That move is not allowed, try again");
                    }
                }
            } else {
                game.MakeAIMove();
            }
            
            if (!game.IsGameOver()) {
                // if the turn player is the same after playing a move then the opponents turn was skipped
                if (player == game.CurrentTurn()) {
                    System.out.println("\n" + GetTurnName(Board.GetReverse(game.CurrentTurn())) + " skips their turn");
                }
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
