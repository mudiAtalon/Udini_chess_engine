package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class Knight extends Piece {

    public Knight(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2658' : '\u265e');
    }

    public Knight(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Knight(Piece other){
        super(other);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
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

