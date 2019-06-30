package players.miniMax;

import board.Ply;

public class PositionEvaluationResult {
    public final double evaluation;
    public final Ply bestMove;
    public int depth;

    public PositionEvaluationResult(double evaluation, Ply bestMove) {
        this.evaluation = evaluation;
        this.bestMove = bestMove;
        this.depth = 0;
    }
}
