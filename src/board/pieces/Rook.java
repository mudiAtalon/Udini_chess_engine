package board.pieces;

import board.Move;

import java.util.*;

public class Rook extends Piece {

    public Rook(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2656' : '\u265c');
    }

    public Rook(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
        addRay(ret, 1, 0);
        addRay(ret, -1, 0);
        addRay(ret, 0, 1);
        addRay(ret, 0, -1);
        return ret;
    }
}

