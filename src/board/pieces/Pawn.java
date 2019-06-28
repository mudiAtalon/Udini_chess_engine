package board.pieces;

import board.*;

import java.util.*;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? PieceType.WHITE_PAWN : PieceType.BLACK_PAWN);
    }

    public Pawn(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Pawn(Piece other){
        super(other);
    }

    private boolean hasNotMoved() {
        if (isWhite)
            return square.rank == 1;
        else
            return square.rank == 6;
    }

    public boolean isPromotionRank(int rank) {
        return (isWhite && rank == 7) || (!isWhite && rank == 0);
    }

    private void addMoveToList(Ply ply, List<Ply> list) {
        if (isPromotionRank(ply.rankTo)) {
            Square from = ply.getFrom(), to = ply.getTo();
            list.add(new Ply(from, to, Ply.Promotion.QUEEN));
            list.add(new Ply(from, to, Ply.Promotion.ROOK));
            list.add(new Ply(from, to, Ply.Promotion.BISHOP));
            list.add(new Ply(from, to, Ply.Promotion.KNIGHT));
        } else
            list.add(ply);
    }

    @Override
    public List<Ply> moves(Board board) {
        List<Ply> ret = new ArrayList<>();
        int rankDiff = isWhite ? 1 : -1;

        int fileDiff = -1;
        Square to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to)) {
            if (isEnemy(board.get(to)))
                addMoveToList(new Ply(square, to), ret);
            if (to.equals(board.getEnPassant()))
                addMoveToList(new Ply(square, to, false, true), ret);
        }

        fileDiff = 0;
        to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to) && board.get(to).isEmpty()) {
            addMoveToList(new Ply(square, to), ret);

            if (hasNotMoved()) {
                to = new Square(to.rank + rankDiff, to.file);
                if (board.get(to).isEmpty())
                    addMoveToList(new Ply(square, to, true), ret);
            }

        }

        fileDiff = 1;
        to = new Square(square.rank + rankDiff, square.file + fileDiff);
        if (Board.isOnBoard(to)) {
            if (isEnemy(board.get(to)))
                addMoveToList(new Ply(square, to), ret);
            if (to.equals(board.getEnPassant()))
                addMoveToList(new Ply(square, to, false, true), ret);
        }

        return ret;
    }

}

