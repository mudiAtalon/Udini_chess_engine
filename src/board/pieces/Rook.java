package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class Rook extends Piece {

    public Rook(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? PieceType.WHITE_ROOK : PieceType.BLACK_ROOK);
    }

    public Rook(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Rook(Piece other){
        super(other);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
        addRay(ret, board, 1, 0);
        addRay(ret, board, -1, 0);
        addRay(ret, board, 0, 1);
        addRay(ret, board, 0, -1);
        return ret;
    }
}

