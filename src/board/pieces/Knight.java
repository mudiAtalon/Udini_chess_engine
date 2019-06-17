package board.pieces;

import board.Board;
import board.Square;

import java.util.*;

public class Knight extends Piece {

    public Knight(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2658' : '\u265e');
    }

    public Knight(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    @Override
    protected List<Square> _sqrControl(Board b) {
        List<Square> ret = new ArrayList<>();
        addStep(ret, 2, 1);
        addStep(ret, -2, 1);
        addStep(ret, 2, -1);
        addStep(ret, -2, -1);
        addStep(ret, 1, 2);
        addStep(ret, -1, 2);
        addStep(ret, 1, -2);
        addStep(ret, -1, -2);
        return ret;
    }
}

