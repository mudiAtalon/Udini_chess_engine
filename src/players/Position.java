package players;

import board.*;
import board.pieces.Piece;

import java.util.List;

public interface Position {

    boolean isWhiteTurn();

    List<Move> getAllLegalMoves();

    Board.Result getResult();

    boolean isPawn(Square square);

    boolean isEnPassantSquare(Square square);
}
