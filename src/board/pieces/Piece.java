package board.pieces;

import board.Board;
import board.Ply;
import board.Square;

import java.util.*;

public abstract class Piece {
    protected final boolean isWhite;
    protected Square square;
    public final PieceType pieceType;

    public enum PieceType{
        WHITE_KING('\u2654', 0), BLACK_KING('\u265a', 0),
        WHITE_QUEEN('\u2655', 9), BLACK_QUEEN('\u265b', -9),
        WHITE_ROOK('\u2656', 5), BLACK_ROOK('\u265c', -5),
        WHITE_BISHOP('\u2657', 3.25), BLACK_BISHOP('\u265d', -3.25),
        WHITE_KNIGHT('\u2658', 3), BLACK_KNIGHT('\u265e', -3),
        WHITE_PAWN('\u2659', 1), BLACK_PAWN('\u265f', -1),
        EMPTY_PIECE('\u2001', 0);

        final char c;
        final double value;

        PieceType(char c, double value){
            this.c=c;
            this.value = value;
        }

        public double getValue(){
            return value;
        }

        @Override
        public String toString(){
            return c+"";
        }
    }

    public Piece(boolean isWhite, Square square, PieceType pieceType) {
        this.isWhite = isWhite;
        this.square = square;
        this.pieceType = pieceType;
    }

    public Piece(boolean isWhite, int rank, int file, PieceType pieceType) {
        this(isWhite, new Square(rank, file), pieceType);
    }

    public Piece(Piece other){
        this(other.isWhite, other.square, other.pieceType);
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

    public abstract List<Ply> moves(Board board);

    protected void addRay(List<Ply> plies, Board board, int rankStep, int fileStep) {
        for (Square to = new Square(square.rank + rankStep, square.file + fileStep);
             Board.isOnBoard(to); to = new Square(to.rank + rankStep, to.file + fileStep)) {
            if(!board.get(to).isAlly(this))
                plies.add(new Ply(square, to));
            if (!board.get(to).isEmpty())
                return;
        }
    }

    protected void addStep(List<Ply> plies, Board board, int rankStep, int fileStep) {
        Square to = new Square(square.rank + rankStep, square.file + fileStep);
        if (Board.isOnBoard(to) && !board.get(to).isAlly(this))
            plies.add(new Ply(square, to));
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Piece))
            return false;
        Piece other = (Piece) o;
        return this.pieceType == other.pieceType;
    }

    @Override
    public Piece clone(){
        switch (pieceType){
            case WHITE_KING:
            case BLACK_KING:
                return new King(this);
            case WHITE_QUEEN:
            case BLACK_QUEEN:
                return new Queen(this);
            case WHITE_ROOK:
            case BLACK_ROOK:
                return new Rook(this);
            case WHITE_BISHOP:
            case BLACK_BISHOP:
                return new Bishop(this);
            case WHITE_KNIGHT:
            case BLACK_KNIGHT:
                return new Knight(this);
            case WHITE_PAWN:
            case BLACK_PAWN:
                return new Pawn(this);
            case EMPTY_PIECE:
                return this;
            default:
                throw new IllegalArgumentException("wow, a new piece!");
        }
    }

    @Override
    public String toString() {
        return pieceType + " ";
    }

}
