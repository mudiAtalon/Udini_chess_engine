package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class King extends Piece {

    public King(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2654' : '\u265a');
    }

    public King(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public King(Piece other){
        super(other);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
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

