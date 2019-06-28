package board.pieces;

import board.Board;
import board.Ply;
import board.Square;

import java.util.*;

public class Knight extends Piece {

    public Knight(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? PieceType.WHITE_KNIGHT : PieceType.BLACK_KNIGHT);
    }

    public Knight(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Knight(Piece other){
        super(other);
    }

    @Override
    public List<Ply> moves(Board board) {
        List<Ply> ret = new ArrayList<>();
        addStep(ret, board, 2, 1);
        addStep(ret, board, -2, 1);
        addStep(ret, board, 2, -1);
        addStep(ret, board, -2, -1);
        addStep(ret, board, 1, 2);
        addStep(ret, board, -1, 2);
        addStep(ret, board, 1, -2);
        addStep(ret, board, -1, -2);
        return ret;
    }
}

