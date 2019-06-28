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
        super(true, -1, -1, PieceType.EMPTY_PIECE);
    }

    @Override
    public List<Move> moves(Board board) {
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
