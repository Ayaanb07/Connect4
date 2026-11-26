//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board board = new Board();
        Comp comp = new Comp();

        //player 1 = 1
        //player 2 = 2
        boolean win1 = true;
        int turnNum =0;
        //game loop
        while(win1)
        {
            int c= 8;
            int temp;
            // comp turn
            long start = System.nanoTime();

            int[] rowCol = comp.compMove(board, 2);

            long end = System.nanoTime();

            System.out.println("Search time: " + (end - start) / 1_000_000 + " ms");
            board.updatePiece(rowCol[1], 2);
            board.displayBoard();

            if(board.win( 2, rowCol[0], rowCol[1]))
            {
                System.out.println("Computer wins");
                win1 = false;
                break;
            }

            turnNum++;

            if(Board.fullBoard(turnNum))
            {
                System.out.println("draw!");
                win1 = false;
                break;
            }
            //players turn
                while (true)
                {
                    System.out.println("Pick a column (0-6):");
                    c = in.nextInt();

                    if (c < 0 || c >= 7)
                    {
                        System.out.println("Invalid column. Pick 0-6.");
                        continue;
                    }

                    temp = board.updatePiece(c, 1);

                    if (temp != -1) break;

                    System.out.println("That column is full. Try again.");
                }



            if(board.win(1 , temp ,c))
            {
                System.out.println("player wins");
                win1 = false;
                break;
            }

            turnNum++;
            if(Board.fullBoard(turnNum))
            {
                System.out.println("draw!");
                win1 = false;
                break;
            }






        }
    }




}
