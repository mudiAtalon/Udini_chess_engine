package board;

import java.util.List;

public interface Position {

    boolean isWhiteTurn();

    List<Move> getAllLegalMoves();

    Board.Result getResult();
}
