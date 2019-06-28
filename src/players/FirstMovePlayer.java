package players;

import board.Ply;

public class FirstMovePlayer implements Player {

    @Override
    public Ply getChosenMove(Position pos) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pos.getAllLegalPlies().get(0);
    }
}
