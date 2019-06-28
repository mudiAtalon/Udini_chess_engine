package players;

import board.*;

import java.util.List;

public interface Position {

    boolean isWhiteTurn();

    List<Ply> getAllLegalPlies();

    Board.Result getResult();

    boolean isPawn(Square square);

    boolean isEnPassantSquare(Square square);
}
