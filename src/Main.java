//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] grid = new int[6][7];
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
                temp = updatePiece(grid, c, 1);
                if (temp != -1) break;
            }


            if(win(grid, 1 , temp ,c))
            {
                System.out.println("player wins");
                win1 = false;
                break;
            }

            turnNum++;
            if(fullBoard(turnNum))
            {
                System.out.println("draw!");
                win1 = false;
                break;
            }



            // comp turn
            int [] rowCol = compMove(grid,2);
            displayBoard(grid);

            if(win(grid, 2, rowCol[0], rowCol[1]))
            {
                System.out.println("Computer wins");
                win1 = false;
                break;
            }

            turnNum++;

            if(fullBoard(turnNum))
            {
                System.out.println("draw!");
                win1 = false;
                break;
            }

        }
    }
    public static boolean fullBoard ( int n)
    {
        if(n ==42)
        {
            return true;
        }
        else return false;
    }
    public static int[] compMove(int[][] grid, int n)
    {
        Random rand = new Random();
        int col;

        do
        {
            col = rand.nextInt(7);
        }
        while(grid[0][col] != 0);

        int row = updatePiece(grid, col, n);

        return new int[]{row, col};
    }


    public static boolean win (int [][] grid, int n, int r, int c)
    {
        return winDia(grid, n, r ,c) || winHoriz(grid, n, r ,c) ||  winVert(grid, n, r ,c);
    }



    public static int updatePiece(int[][] grid, int col, int n)
    {
        for(int i = 5; i >= 0; i--)
        {
            if(grid[i][col] == 0)
            {
                grid[i][col] = n;
                return i;
            }
        }

        return -1; // Column is full
    }

    public static void displayBoard(int[][] grid)
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("0 1 2 3 4 5 6");
    }




















    // win check functions
    public static boolean winVert(int[][] grid, int n, int row, int col)
    {
        int count = 1;

        // Check down
        for (int i = row + 1; i < grid.length; i++)
        {
            if (grid[i][col] == n)
            {
                count++;
            }
            else break;

        }

        // Check up
        for (int i = row - 1; i >= 0; i--)
        {
            if (grid[i][col] == n)
            {
                count++;
            }
            else break;

        }

        return count >= 4;
    }
    public static boolean winHoriz(int[][] grid, int n, int row, int col)
    {
        int count = 1; // newest piece

        // Check right
        for (int j = col + 1; j < grid[row].length; j++)
        {
            if (grid[row][j] == n)
            {
                count++;
            }
            else break;
        }

        // Check left
        for (int j = col - 1; j >= 0; j--)
        {
            if (grid[row][j] == n)
            {
                count++;
            }
            else break;
        }

        return count >= 4;
    }

    public static boolean winDia(int[][] grid, int n, int row, int col)
    {
        int count = 1;

        // Down-right
        for (int i = row + 1, j = col + 1;
             i < grid.length && j < grid[i].length && count < 4;
             i++, j++)
        {
            if (grid[i][j] == n)
                count++;
            else
                break;
        }

        // Up-left
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0 && count < 4;
             i--, j--)
        {
            if (grid[i][j] == n)
                count++;
            else
                break;
        }

        if (count >= 4)
            return true;

        count = 1;

        // Down-left
        for (int i = row + 1, j = col - 1;
             i < grid.length && j >= 0 && count < 4;
             i++, j--)
        {
            if (grid[i][j] == n)
                count++;
            else
                break;
        }

        // Up-right
        for (int i = row - 1, j = col + 1;
             i >= 0 && j < grid[i].length && count < 4;
             i--, j++)
        {
            if (grid[i][j] == n)
                count++;
            else
                break;
        }

        return count >= 4;
    }

}
