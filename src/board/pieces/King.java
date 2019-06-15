package board.pieces;

import board.Board;
import board.Move;

import java.util.*;

public class King extends Piece {

    public King(boolean isWhite, int[] square) {
        super(isWhite, square, isWhite ? '\u2654' : '\u265a');
    }

    public King(boolean isWhite, int rank, int file) {
        this(isWhite, new int[]{rank, file});
    }

    @Override
    public List<Move> moves() {
        List<Move> ret = new ArrayList<>();
        addStep(ret, 1, 1);
        addStep(ret, 1, 0);
        addStep(ret, 1, -1);
        addStep(ret, 0, 1);
        addStep(ret, 0, -1);
        addStep(ret, -1, 1);
        addStep(ret, -1, 0);
        addStep(ret, -1, -1);
        ret.addAll(Board.getInstance().castleMoves());
        return ret;
    }
}

