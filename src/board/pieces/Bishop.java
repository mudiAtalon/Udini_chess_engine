package board.pieces;

import board.Board;
import board.Square;

import java.util.*;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2657' : '\u265d');
    }

    public Bishop(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    @Override
    protected List<Square> _sqrControl(Board board) {
        List<Square> ret = new ArrayList<>();
        addRay(ret, board, 1, 1);
        addRay(ret, board, -1, 1);
        addRay(ret, board, 1, -1);
        addRay(ret, board, -1, -1);
        return ret;
    }
}

