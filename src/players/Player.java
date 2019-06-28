package players;

import board.Ply;

public interface Player {

    Ply getChosenMove(Position pos);
}
