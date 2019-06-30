package players;

import board.*;
import board.pieces.Piece;

import java.util.List;

public interface Position {

    boolean isWhiteTurn();

    List<Ply> getAllLegalPlies();

    Board.Result getResult();

    Piece.PieceType getPieceType(int rank, int file);

    boolean isPawn(Square square);

    boolean isEnPassantSquare(Square square);

    Board clone();
}
