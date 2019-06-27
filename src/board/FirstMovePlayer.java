package board;

public class FirstMovePlayer implements Player {

    @Override
    public Move _getChosenMove(Position pos) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Move ret;
        long time = System.nanoTime();
        ret = pos.getAllLegalMoves().get(0);
        time = System.nanoTime() - time;
        System.out.println(time / 1000000000.0);
        return ret;
    }
}
