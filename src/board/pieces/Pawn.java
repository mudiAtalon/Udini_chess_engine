package board.pieces;

import board.*;

import java.util.*;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2659' : '\u265f');
    }

    public Pawn(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Pawn(Piece other){
        super(other);
    }

    private boolean hasNotMoved() {
        if (isWhite)
            return square.rank == 6;
        else
            return square.rank == 1;
    }

    public boolean isPromotionRank(int rank) {
        return (isWhite && rank == 0) || (!isWhite && rank == 7);
    }

    private void addMoveToList(Move move, List<Move> list) {
        if (isPromotionRank(move.rankTo)) {
            Square from = move.getFrom(), to = move.getTo();
            list.add(new Move(from, to, Move.Promotion.QUEEN));
            list.add(new Move(from, to, Move.Promotion.ROOK));
            list.add(new Move(from, to, Move.Promotion.BISHOP));
            list.add(new Move(from, to, Move.Promotion.KNIGHT));
        } else
            list.add(move);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
        int rankDiff = isWhite ? -1 : 1;
        int fileDiff = -1;
        Square to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to)) {
            if (isEnemy(board.get(to)))
                addMoveToList(new Move(square, to), ret);
            if (to.equals(board.getEnPassant()))
                addMoveToList(new Move(square, to, false, true), ret);
        }
        fileDiff = 0;
        to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to) && board.get(to).isEmpty()) {
            addMoveToList(new Move(square, to), ret);
            if (hasNotMoved()) {
                to = new Square(to.rank + rankDiff, to.file);
                if (board.get(to).isEmpty())
                    addMoveToList(new Move(square, to, true), ret);
            }
        }
        fileDiff = 1;
        to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to)) {
            if (isEnemy(board.get(to)))
                addMoveToList(new Move(square, to), ret);
            if (to.equals(board.getEnPassant()))
                addMoveToList(new Move(square, to, false, true), ret);
        }
        return ret;
    }

}

