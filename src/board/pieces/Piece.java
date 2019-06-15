package board.pieces;

import board.Board;
import board.Move;

import java.util.List;

public abstract class Piece {
    protected final boolean isWhite;
    protected int[] square;
    private final char c;

    public Piece(boolean isWhite, int[] square, char c) {
        this.isWhite = isWhite;
        this.square = square;
        this.c = c;
    }

    public Piece(boolean isWhite, int rank, int file, char c) {
        this(isWhite, new int[]{rank, file}, c);
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isBlack() {
        return !isWhite;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isAlly(Piece p) {
        return isWhite ? p.isWhite() : p.isBlack();
    }

    public boolean isEnemy(Piece p) {
        return isWhite ? p.isBlack() : p.isWhite();
    }

    public int[] getSquare(){
        return square;
    }

    public void setSquare(int[] square){
        this.square = square;
    }

    public abstract List<Move> moves();

    protected void addRay(List<Move> moves, int rankStep, int fileStep) {
        for (int[] to = new int[]{square[0] + rankStep, square[1] + fileStep};
             Board.isOnBoard(to); to = new int[]{to[0] + rankStep, to[1] + fileStep}) {
            if (!isAlly(Board.getInstance().get(to)))
                moves.add(new Move(square, to));
            if (!Board.getInstance().get(to).isEmpty())
                return;
        }
    }

    protected void addStep(List<Move> moves, int rankStep, int fileStep) {
        int[] to = new int[]{square[0] + rankStep, square[1] + fileStep};
        if (Board.isOnBoard(to) && !isAlly(Board.getInstance().get(to)))
            moves.add(new Move(square, to));
    }

    @Override
    public String toString() {
        return c + " ";
    }

}
