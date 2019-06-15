package board.pieces;

import board.Move;

import java.util.*;

public class Knight extends Piece {

    public Knight(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2658' : '\u265e');
    }

    public Knight(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
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

