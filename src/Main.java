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
            int c;
            int temp;
            //players turn
            while(true) {
                c = in.nextInt();
                temp = board.updatePiece(c, 1);
                if (temp != -1) break;
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



            // comp turn
            int [] rowCol = comp.compMove(board,2, temp, c);
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

        }
    }




}
