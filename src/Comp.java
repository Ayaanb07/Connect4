public class Comp
{
    public int[] canWin(Board board, int n)
    {
        for (int i = 0; i < 7; i++)
        {
            int r = board.testDrop(i);

            if (r != -1 && board.win( n, r, i))
            {
                return new int[]{r, i};
            }
        }

        return new int[]{-1, -1};
    }
    public int [] compMove(Board board, int n, int r, int c)
    {
        //win
        int[] move = canWin(board, 2);
        if (move[0] != -1) return move;
        //block
        move = canWin(board, 1);
        if (move[0] != -1) return move;


        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 7; i++) {
            Board testboard = new Board(board);

            int row = testboard.testDrop(i);

            if (row == -1)
                continue;

            testboard.updatePiece(i, 2);

            int score = evaluateBoard(testboard);
            if (score > bestScore) {
                bestScore = score;
                bestRow = row;
                bestCol = i;
            }
        }
        return new int[]{bestRow, bestCol};
    }

    public int evaluateBoard(Board board)
    {
        int score = 0;

        for (int r = 0; r < 6; r++)
        {
            for (int c = 0; c < 7; c++)
            {
                score += evaluateHoriz(board, r, c);
                score += evaluateVert(board, r, c);
                score += evaluateDiagDownRight(board, r, c);
                score += evaluateDiagDownLeft(board, r, c);
            }
        }

        return score;
    }
    public int evaluateHoriz(Board board, int r, int c)
    {
        if (c + 3 >= 7)
            return 0;

        return evaluateWindow(
                board.getCell(r, c),
                board.getCell(r, c+1),
                board.getCell(r, c+2),
                board.getCell(r, c+3));
    }
    public int evaluateVert(Board board, int r, int c)
    {
        if (r + 3 >= 6)
            return 0;

        return evaluateWindow(
                board.getCell(r, c),
                board.getCell(r+1, c),
                board.getCell(r+2, c),
                board.getCell(r+3, c));
    }
    public int evaluateDiagDownRight(Board board, int r, int c)
    {
        if (r + 3 >= 6 || c + 3 >= 7)
            return 0;

        return evaluateWindow(
            board.getCell(r, c),
            board.getCell(r+1, c+1),
            board.getCell(r+2, c+2),
            board.getCell(r+3, c+3));

    }
    public int evaluateDiagDownLeft(Board board, int r, int c)
    {
        if (r + 3 >= 6 || c - 3 < 0)
            return 0;

        return evaluateWindow(
                board.getCell(r, c),
                board.getCell(r+1, c-1),
                board.getCell(r+2, c-2),
                board.getCell(r+3, c-3));
    }
    public int evaluateWindow(int a, int b, int c, int d)
    {
        int comp = 0;
        int player = 0;
        int empty = 0;

        int[] window = {a, b, c, d};

        for (int i = 0; i < 4; i++)
        {
            if (window[i] == 2)
                comp++;
            else if (window[i] == 1)
                player++;
            else
                empty++;
        }

        if (comp == 3 && empty == 1)
            return 5;
        else if (comp == 2 && empty == 2)
            return 2;
        else if (player == 3 && empty == 1)
            return -5;
        else if (player == 2 && empty == 2)
            return -2;

        return 0;
    }
}
