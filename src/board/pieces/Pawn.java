package board.pieces;

import board.*;

import java.util.*;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2659' : '\u265f');
    }

    public Pawn(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    private boolean hasNotMoved() {
        if (isWhite)
            return square[0] == 6;
        else
            return square[0] == 1;
    }

    public boolean isPromotionRank(int rank){
        return (isWhite && rank == 0) || (!isWhite && rank == 7);
    }

    private void addMoveToList(Move move, List<Move> list){
        if(isPromotionRank(move.rankTo)){
            int[] from = move.getFrom(), to = move.getTo();
            list.add(new Move(from, to, Move.Promotion.QUEEN));
            list.add(new Move(from, to, Move.Promotion.ROOK));
            list.add(new Move(from, to, Move.Promotion.BISHOP));
            list.add(new Move(from, to, Move.Promotion.KNIGHT));
        }else
            list.add(move);
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
        int rankDiff = isWhite ? -1 : 1;
        int fileDiff = -1;
        int[] to = new int[]{square[0] + rankDiff, square[1] + fileDiff};
        if (Board.isOnBoard(to)) {
            if (isEnemy(Board.getInstance().get(to)))
                addMoveToList(new Move(square, to), ret);
            if (Arrays.equals(to, Board.getInstance().getEnPassant()))
                addMoveToList(new Move(square, to, false, true), ret);
        }
        fileDiff = 0;
        to = new int[]{square[0] + rankDiff, square[1] + fileDiff};
        if (Board.isOnBoard(to) && Board.getInstance().get(to).isEmpty()) {
            addMoveToList(new Move(square, to), ret);
            if (hasNotMoved()) {
                to = new int[]{to[0] + rankDiff, to[1]};
                if (Board.getInstance().get(to).isEmpty())
                    addMoveToList(new Move(square, to, true), ret);
            }
        }
        fileDiff = 1;
        to = new int[]{square[0] + rankDiff, square[1] + fileDiff};
        if (Board.isOnBoard(to)) {
            if (isEnemy(Board.getInstance().get(to)))
                addMoveToList(new Move(square, to), ret);
            if (Arrays.equals(to, Board.getInstance().getEnPassant()))
                addMoveToList(new Move(square, to, false, true), ret);
        }
        return ret;
    }
}

