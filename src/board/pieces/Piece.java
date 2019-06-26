package board.pieces;

import board.Board;
import board.Move;
import board.Square;

import java.util.*;

public abstract class Piece {
    protected final boolean isWhite;
    protected Square square;
    private final char c;

    public Piece(boolean isWhite, Square square, char c) {
        this.isWhite = isWhite;
        this.square = square;
        this.c = c;
    }

    public Piece(boolean isWhite, int rank, int file, char c) {
        this(isWhite, new Square(rank, file), c);
    }

    public Piece(Piece other){
        this(other.isWhite, other.square, other.c);
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
        if(isEmpty()) return false;
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

    public abstract List<Move> moves(Board board);

    protected void addRay(List<Move> moves, Board board, int rankStep, int fileStep) {
        for (Square to = new Square(square.rank + rankStep, square.file + fileStep);
             Board.isOnBoard(to); to = new Square(to.rank + rankStep, to.file + fileStep)) {
            if(!board.get(to).isAlly(this))
                moves.add(new Move(square, to));
            if (!board.get(to).isEmpty())
                return;
        }
    }

    protected void addStep(List<Move> moves, Board board, int rankStep, int fileStep) {
        Square to = new Square(square.rank + rankStep, square.file + fileStep);
        if (Board.isOnBoard(to) && !board.get(to).isAlly(this))
            moves.add(new Move(square, to));
    }

    @Override
    public Piece clone(){
        switch (c){
            case '\u2654':
            case '\u265a':
                return new King(this);
            case '\u2655':
            case '\u265b':
                return new Queen(this);
            case '\u2656':
            case '\u265c':
                return new Rook(this);
            case '\u2657':
            case '\u265d':
                return new Bishop(this);
            case '\u2658':
            case '\u265e':
                return new Knight(this);
            case '\u2659':
            case '\u265f':
                return new Pawn(this);
            case '\u2001':
                return this;
            default:
                throw new IllegalArgumentException("wow, a new piece");
        }
    }

    @Override
    public String toString() {
        return c + " ";
    }

}
