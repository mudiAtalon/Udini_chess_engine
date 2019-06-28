package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? PieceType.WHITE_BISHOP : PieceType.BLACK_BISHOP);
    }

    public Bishop(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Bishop(Piece other){
        super(other);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
        addRay(ret, board, 1, 1);
        addRay(ret, board, -1, 1);
        addRay(ret, board, 1, -1);
        addRay(ret, board, -1, -1);
        return ret;
    }
}

