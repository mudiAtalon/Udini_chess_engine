package board.pieces;

import board.Board;
import board.Ply;
import board.Square;

import java.util.*;

public class King extends Piece {

    public King(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ?  PieceType.WHITE_KING: PieceType.BLACK_KING);
    }

    public King(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public King(Piece other){
        super(other);
    }

    @Override
    public List<Ply> moves(Board board) {
        List<Ply> ret = new ArrayList<>();
        addStep(ret, board, 1, 1);
        addStep(ret, board, 1, 0);
        addStep(ret, board, 1, -1);
        addStep(ret, board, 0, 1);
        addStep(ret, board, 0, -1);
        addStep(ret, board, -1, 1);
        addStep(ret, board, -1, 0);
        addStep(ret, board, -1, -1);
        ret.addAll(board.castleMoves());
        return ret;
    }
}

