package board.pieces;

import board.Move;

import java.util.*;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2657' : '\u265d');
    }

    public Bishop(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
        addRay(ret, 1, 1);
        addRay(ret, -1, 1);
        addRay(ret, 1, -1);
        addRay(ret, -1, -1);
        return ret;
    }
}

