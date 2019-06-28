package players;

import board.Move;

import java.util.List;

public class MoveListPlayer implements Player {

    private final List<Move> moves;
    private int ind;

    public MoveListPlayer(List<Move> moves){
        this.moves = moves;
        ind = -1;
    }

    @Override
    public Move getChosenMove(Position pos) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ind++;
        if(ind<moves.size())
            return moves.get(ind);
        return pos.getAllLegalMoves().get(0);
    }
}
