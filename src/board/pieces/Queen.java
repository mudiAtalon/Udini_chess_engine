package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class Queen extends Piece {

    public Queen(boolean isWhite, Square square) {
        super(isWhite, square, isWhite ? '\u2655' : '\u265b');
    }

    public Queen(boolean isWhite, int rank, int file) {
        this(isWhite, new Square(rank, file));
    }

    public Queen(Piece other){
        super(other);
    }

    @Override
    public List<Move> moves(Board board) {
        List<Move> ret = new ArrayList<>();
        addRay(ret, board, 1, 1);
        addRay(ret, board, 1, 0);
        addRay(ret, board, 1, -1);
        addRay(ret, board, 0, 1);
        addRay(ret, board, 0, -1);
        addRay(ret, board, -1, 1);
        addRay(ret, board, -1, 0);
        addRay(ret, board, -1, -1);
        return ret;
    }
}

