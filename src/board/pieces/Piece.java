package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public abstract class Piece {
    protected final boolean isWhite;
    protected Square square;
    private final char c;

    protected List<Square> controlledSquares;

    public Piece(boolean isWhite, Square square, char c) {
        this.isWhite = isWhite;
        this.square = square;
        this.c = c;
        this.controlledSquares = new ArrayList<>();
    }

    public Piece(boolean isWhite, int rank, int file, char c) {
        this(isWhite, new Square(rank, file), c);
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

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public List<Move> moves() {
        List<Square> controledSqrs = sqrControl();
        List<Move> ret = new ArrayList<>();
        for (Square to : controledSqrs)
            if (!isAlly(Board.getInstance().get(to)))
                ret.add(new Move(square, to));
        return ret;
    }

    public List<Square> sqrControl(){
        return sqrControl(Board.getInstance());
    }

    public List<Square> sqrControl(Board board){
        controlledSquares = _sqrControl(board);
        return new ArrayList<>(controlledSquares);
    }

    protected abstract List<Square> _sqrControl(Board board);

    public List<Square> getControlledSquares(){
        return controlledSquares;
    }

    protected void addRay(List<Square> moves, Board board, int rankStep, int fileStep) {
        for (Square to = new Square(square.rank + rankStep, square.file + fileStep);
             Board.isOnBoard(to); to = new Square(to.rank + rankStep, to.file + fileStep)) {
            moves.add(to);
            if (!board.get(to).isEmpty())
                return;
        }
    }

    protected void addStep(List<Square> moves, int rankStep, int fileStep) {
        Square to = new Square(square.rank + rankStep, square.file + fileStep);
        if (Board.isOnBoard(to))
            moves.add(to);
    }

    @Override
    public String toString() {
        return c + " ";
    }

}
