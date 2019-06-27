package board;

public interface Player {

    default Move getChosenMove(Position pos){
        System.out.println(pos);
        return _getChosenMove(pos);
    }

    Move _getChosenMove(Position pos);
}
