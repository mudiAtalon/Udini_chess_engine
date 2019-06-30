package players.miniMax;

import board.Board;
import board.Ply;
import board.pieces.Piece;
import players.Player;
import players.Position;

public class MiniMaxPlayer implements Player {

    Ply bestMove;

    @Override
    public Ply getChosenMove(Position pos) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        evaluate(pos, 3, pos.isWhiteTurn());
        return bestMove;
    }

    private double evaluate(Position pos, int depth, boolean isMaximizing) {
        return evaluate(pos, depth, isMaximizing, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private double evaluate(Position pos, int depth, boolean isMaximizing, double alpha, double beta) {
        if (depth == 0)
            return evaluate(pos);
        Ply currentBestMove = null;
        for (Ply move : pos.getAllLegalPlies()) {
            Board copy = pos.clone();
            copy.move(move);
            double eval = evaluate(copy, depth - 1, !isMaximizing, alpha, beta);
            if (isMaximizing) {
                if(eval >= beta)
                    return eval;
                if (eval > alpha) {
                    alpha = eval;
                    currentBestMove = move;
                }
            } else {
                if(eval <= alpha)
                    return eval;
                if (eval < beta) {
                    beta = eval;
                    currentBestMove = move;
                }
            }
        }
        bestMove = currentBestMove;
        return isMaximizing ? alpha : beta;
    }

    private double evaluate(Position pos) {
        if (pos.getResult() != Board.Result.PLAYING) {
            switch (pos.getResult()) {
                case DRAW:
                    return 0;
                case WHITE_VICTORY:
                    return Double.POSITIVE_INFINITY;
                case BLACK_VICTORY:
                    return Double.NEGATIVE_INFINITY;

                default:
                    throw new RuntimeException("wow, a new result!");
            }
        }
        double ret = 0;
        for (int rank = 0; rank < 8; rank++)
            for (int file = 0; file < 8; file++)
                ret += pos.getPieceType(rank, file).getValue();
        return ret;
    }
}
