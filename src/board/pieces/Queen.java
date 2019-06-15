package board.pieces;

import board.Move;
import java.util.*;

public class Queen extends Piece {

    public Queen(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2655' : '\u265b');
    }

    public Queen(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
        addRay(ret, 1, 1);
        addRay(ret, 1, 0);
        addRay(ret, 1, -1);
        addRay(ret, 0, 1);
        addRay(ret, 0, -1);
        addRay(ret, -1, 1);
        addRay(ret, -1, 0);
        addRay(ret, -1, -1);
        return ret;
    }
}

