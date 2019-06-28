package players;

import board.Move;

public class FirstMovePlayer implements Player {

    @Override
    public Move getChosenMove(Position pos) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pos.getAllLegalMoves().get(0);
    }
}
