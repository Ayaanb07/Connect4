public class Board
{
    int[][] grid;

    public Board() {
        grid = new int[6][7];
    }
    public int getCell(int r, int c)
    {
        return grid[r][c];
    }
    //creates copy of the current board
    public Board(Board original)
    {
        grid = new int[6][7];

        for (int r = 0; r < 6; r++)
        {
            for (int c = 0; c < 7; c++)
            {
                grid[r][c] = original.grid[r][c];
            }
        }
    }


    public static boolean fullBoard (int n)
    {
        return n==42;
    }
    public boolean win ( int n, int r, int c)
    {
        return winDia(n, r ,c) || winHoriz( n, r ,c) ||  winVert( n, r ,c);
    }
    // win check functions
    public boolean winVert( int n, int row, int col)
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
    public boolean winHoriz(int n, int row, int col)
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

    public boolean winDia(int n, int row, int col)
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
    public void displayBoard()
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
    public int testDrop( int col)
    {
        for (int i = 5; i >= 0; i--)
        {
            if (grid[i][col] == 0)
            {
                return i;
            }
        }

        return -1; // Column is full
    }

    public  int updatePiece( int col, int n)
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

}
