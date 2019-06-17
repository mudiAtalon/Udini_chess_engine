package board.pieces;

import board.Board;
import board.Square;

import java.util.*;

public class Rook extends Piece {

    public Rook(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2656' : '\u265c');
    }

    public Rook(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    @Override
    protected List<Square> _sqrControl(Board board) {
        List<Square> ret = new ArrayList<>();
        addRay(ret, board, 1, 0);
        addRay(ret, board, -1, 0);
        addRay(ret, board, 0, 1);
        addRay(ret, board, 0, -1);
        return ret;
    }
}

