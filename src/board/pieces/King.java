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

    @Override
    public List<Move> moves() {
        List<Move> ret = super.moves();
        ret.addAll(Board.getInstance().castleMoves());
        return ret;
    }

    @Override
    protected List<Square> _sqrControl(Board b) {
        List<Square> ret = new ArrayList<>();
        addStep(ret, 1, 1);
        addStep(ret, 1, 0);
        addStep(ret, 1, -1);
        addStep(ret, 0, 1);
        addStep(ret, 0, -1);
        addStep(ret, -1, 1);
        addStep(ret, -1, 0);
        addStep(ret, -1, -1);
        return ret;
    }
}

