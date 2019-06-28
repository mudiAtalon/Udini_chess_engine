package players;

import board.Move;

public interface Player {

    Move getChosenMove(Position pos);
}
