package players;

import board.Ply;

import java.util.List;

public class MoveListPlayer implements Player {

    private final List<Ply> moves;
    private int ind;

    public MoveListPlayer(List<Ply> moves){
        this.moves = moves;
        ind = -1;
    }

    @Override
    public Ply getChosenMove(Position pos) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ind++;
        System.out.println("playing move " + (ind+1));
        if(ind<moves.size())
            return moves.get(ind);
        return pos.getAllLegalPlies().get(0);
    }
}
