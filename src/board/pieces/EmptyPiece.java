package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public class EmptyPiece extends Piece {

    private static EmptyPiece instance = new EmptyPiece();

    public static EmptyPiece getInstance(){
        return instance;
    }

    private EmptyPiece() {
        super(true, -1, -1, '\u2001');
    }

    @Override
    public List<Move> moves() {
        return new ArrayList<>();
    }

    @Override
    protected List<Square> _sqrControl(Board b) {
        return new ArrayList<>();
    }

    @Override
    public boolean isWhite() {
        return false;
    }

    @Override
    public boolean isBlack() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
