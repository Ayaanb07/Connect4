    public class Comp
    {
        public int[] canWin(Board board, int n) {
            for (int i = 0; i < 7; i++) {
                int r = board.testDrop(i);

                if (r != -1 && board.win(n, r, i)) {
                    return new int[]{r, i};
                }
            }

            return new int[]{-1, -1};
        }

        public int[] compMove(Board board) {
            int depth = 0;
            if(board.isFull())
            {
                return new int[]{-1, -1};
            }
            // Immediate win
            int[] move = canWin(board, 2);
            if (move[0] != -1) return move;

            // Immediate block
            move = canWin(board, 1);
            if (move[0] != -1) return move;

            int bestScore = Integer.MIN_VALUE;
            int bestRow = -1;
            int bestCol = -1;


            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            int [] moveOrder = {3,2,4,1,5,0,6};
            for (int i = 0; i < 7; i++) {

                Board testBoard = new Board(board);
                int row = testBoard.testDrop(moveOrder[i]);

                if (row == -1) continue;

                testBoard.updatePiece(moveOrder[i], 2);

                int score = bestMovePlayer(testBoard, 1, 10, alpha, beta);

                if (bestCol == -1 || score > bestScore) {
                    bestScore = score;
                    bestRow = row;
                    bestCol = moveOrder[i];
                }
                alpha = Math.max(alpha, score);
            }

            return new int[]{bestRow, bestCol};
        }

        public int bestMovePlayer(Board board, int depth, int maxDepth, int alpha, int beta)
        {
            if(board.isFull())
            {
                return 0;
            }


            int bestScore = Integer.MAX_VALUE;

            int [] moveOrder = {3,2,4,1,5,0,6};
            for (int i = 0; i < 7; i++)
            {
                Board testBoard = new Board(board);

                int row = testBoard.testDrop(moveOrder[i]);

                if (row == -1)
                    continue;

                testBoard.updatePiece(moveOrder[i], 1);

                if (testBoard.win(1, row, moveOrder[i]))
                    return Integer.MIN_VALUE;


                int score;

                if (depth == maxDepth)
                    score = evaluateBoard(testBoard);
                else
                    score = bestMoveComp(testBoard, depth + 1, maxDepth, alpha, beta);

                if (score < bestScore)
                    bestScore = score;


                beta = Math.min(beta, score);

                if (alpha >= beta)
                    break;
            }

            return bestScore;
        }

        public int bestMoveComp(Board board, int depth, int maxDepth, int alpha, int beta)
        {
            if(board.isFull())
            {
                return 0;
            }

            int bestScore = Integer.MIN_VALUE;
            int [] moveOrder = {3,2,4,1,5,0,6};
            for (int i = 0; i < 7; i++)
            {
                Board testBoard = new Board(board);

                int row = testBoard.testDrop(moveOrder[i]);

                if (row == -1)
                    continue;

                testBoard.updatePiece(moveOrder[i], 2);

                if (testBoard.win(2, row, moveOrder[i]))
                    return Integer.MAX_VALUE;

                int score;

                if (depth == maxDepth)
                    score = evaluateBoard(testBoard);
                else
                    score = bestMovePlayer(testBoard, depth + 1, maxDepth, alpha, beta);

                if (score > bestScore)
                    bestScore = score;

                alpha = Math.max(alpha, score);

                if (alpha >= beta)
                    break;
            }

            return bestScore;
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

            int comp = 0;
            int player = 0;
            int emptyPlayable = 0;
            int emptyNotPlayable = 0;

            for (int i = 0; i < 4; i++)
            {
                int cell = board.getCell(r, c + i);

                if (cell == 2)
                    comp++;
                else if (cell == 1)
                    player++;
                else if (cell == 0)
                {
                    if (isPlayable(board, r, c + i))
                        emptyPlayable++;
                    else
                        emptyNotPlayable++;
                }
            }

            return evaluateWindow(comp, player, emptyPlayable, emptyNotPlayable);
        }

        public int evaluateVert(Board board, int r, int c)
        {
            if (r + 3 >= 6)
                return 0;

            int comp = 0;
            int player = 0;
            int emptyPlayable = 0;
            int emptyNotPlayable = 0;


            for (int i = 0; i < 4; i++)
            {
                int cell = board.getCell(r + i, c);

                if (cell == 2)
                    comp++;
                else if (cell == 1)
                    player++;
                else if (cell == 0)
                {
                    if (isPlayable(board, r + i, c))
                        emptyPlayable++;
                    else
                        emptyNotPlayable++;
                }
            }

            return evaluateWindow(comp, player, emptyPlayable, emptyNotPlayable);
        }

        public int evaluateDiagDownRight(Board board, int r, int c)
        {
            if (r + 3 >= 6 || c + 3 >= 7)
                return 0;

            int comp = 0;
            int player = 0;
            int emptyPlayable = 0;
            int emptyNotPlayable = 0;

            for (int i = 0; i < 4; i++)
            {
                int cell = board.getCell(r + i, c + i);

                if (cell == 2)
                    comp++;
                else if (cell == 1)
                    player++;
                else if (cell == 0)
                {
                    if (isPlayable(board, r + i, c + i))
                        emptyPlayable++;
                    else
                        emptyNotPlayable++;
                }
            }

            return evaluateWindow(comp, player, emptyPlayable, emptyNotPlayable);
        }

        public int evaluateDiagDownLeft(Board board, int r, int c)
        {
            if (r + 3 >= 6 || c - 3 < 0)
                return 0;

            int comp = 0;
            int player = 0;
            int emptyPlayable = 0;
            int emptyNotPlayable = 0;

            for (int i = 0; i < 4; i++)
            {
                int cell = board.getCell(r + i, c - i);

                if (cell == 2)
                    comp++;
                else if (cell == 1)
                    player++;
                else if (cell == 0)
                {
                    if (isPlayable(board, r + i, c - i))
                        emptyPlayable++;
                    else
                        emptyNotPlayable++;
                }
            }

            return evaluateWindow(comp, player, emptyPlayable, emptyNotPlayable);
        }

        public int evaluateWindow(int comp, int player, int emptyPlayable, int emptyNotPlayable)
        {
            if (player > 0 && comp > 0) return 0; // no value for either player
            //comp scoring
            if(comp==1 && emptyNotPlayable ==3) return 1;
            if(comp==2 && emptyNotPlayable ==2) return 4;
            if(comp==3 && emptyNotPlayable ==1) return 10;
            if(comp==1 && emptyPlayable ==3) return 3;
            if(comp==2 && emptyPlayable ==2) return 8;
            if(comp==3 && emptyPlayable ==1) return 300;

            //player scoring
            if(player==1 && emptyNotPlayable ==3) return -1;
            if(player==2 && emptyNotPlayable ==2) return -4;
            if(player==3 && emptyNotPlayable ==1) return -10;
            if(player==1 && emptyPlayable ==3) return -3;
            if(player==2 && emptyPlayable ==2) return -8;
            if(player==3 && emptyPlayable ==1) return -300;


            return 0;
        }

        public boolean isPlayable(Board board, int r, int c)
        {
            if(r < 0 || r >= 6) return false;
            if(c < 0 || c >= 7) return false;
            if(board.getCell(r,c) !=0) return false;

            if(r==5) return true;
            return board.getCell(r + 1, c) != 0;

        }
    }
