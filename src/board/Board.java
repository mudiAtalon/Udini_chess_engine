package board;

import board.pieces.Piece;

import java.util.*;

import board.pieces.*;
import players.Position;

public class Board implements Position {

    private static final Square WHITE_SHORT_ROOK_START_SQR = new Square(0, 7),
            WHITE_LONG_ROOK_START_SQR = new Square(0, 0),
            WHITE_KING_START_SQR = new Square(0, 4),
            BLACK_SHORT_ROOK_START_SQR = new Square(7, 7),
            BLACK_LONG_ROOK_START_SQR = new Square(7, 0),
            BLACK_KING_START_SQR = new Square(7, 4);

    private final boolean isRealBoard;

    private final Piece[][] board = new Piece[8][8];
    private EmptyPiece EP;
    private boolean isWhiteTurn;
    private Square enPassant;
    private boolean whiteShortCastleRight,
            whiteLongCastleRight,
            blackShortCastleRight,
            blackLongCastleRight;
    private Piece whiteKing, blackKing;

    private List<Ply> allLegalPlies;

    private int fiftyMovePlyCounter;
    private Map<Board, Integer> positionRepetitionCounter;

    private Result result;

    public enum Result {
        WHITE_VICTORY,
        BLACK_VICTORY,
        DRAW,
        PLAYING
    }

    public Board() {
        EP = EmptyPiece.getInstance();

        enPassant = null;
        whiteShortCastleRight = true;
        whiteLongCastleRight = true;
        blackShortCastleRight = true;
        blackLongCastleRight = true;

        isWhiteTurn = true;

        whiteKing = new King(true, 0, 4);
        blackKing = new King(false, 7, 4);

        board[0][0] = new Rook(true, 0, 0);
        board[0][1] = new Knight(true, 0, 1);
        board[0][2] = new Bishop(true, 0, 2);
        board[0][3] = new Queen(true, 0, 3);
        board[0][4] = whiteKing;
        board[0][5] = new Bishop(true, 0, 5);
        board[0][6] = new Knight(true, 0, 6);
        board[0][7] = new Rook(true, 0, 7);
        for (int file = 0; file < 8; file++) {
            board[1][file] = new Pawn(true, 1, file);
        }
        for (int rank = 2; rank < 6; rank++)
            for (int file = 0; file < 8; file++) {
                board[rank][file] = EP;
            }
        for (int file = 0; file < 8; file++) {
            board[6][file] = new Pawn(false, 6, file);
        }
        board[7][0] = new Rook(false, 7, 0);
        board[7][1] = new Knight(false, 7, 1);
        board[7][2] = new Bishop(false, 7, 2);
        board[7][3] = new Queen(false, 7, 3);
        board[7][4] = blackKing;
        board[7][5] = new Bishop(false, 7, 5);
        board[7][6] = new Knight(false, 7, 6);
        board[7][7] = new Rook(false, 7, 7);

        result = Result.PLAYING;

        fiftyMovePlyCounter = 0;
        positionRepetitionCounter = new HashMap<>();
        positionRepetitionCounter.put(this, 1);

        isRealBoard = true;

        updateAllLegalMoves();
    }

    private Board(Board other) {
        this.EP = other.EP;

        this.enPassant = null;
        this.whiteShortCastleRight = other.whiteShortCastleRight;
        this.whiteLongCastleRight = other.whiteLongCastleRight;
        this.blackShortCastleRight = other.blackShortCastleRight;
        this.blackLongCastleRight = other.blackLongCastleRight;

        this.isWhiteTurn = other.isWhiteTurn;

        this.whiteKing = other.whiteKing.clone();
        this.blackKing = other.blackKing.clone();

        copyPieceArray(other, this);

        Square wks = whiteKing.getSquare(), bks = blackKing.getSquare();
        board[wks.rank][wks.file] = whiteKing;
        board[bks.rank][bks.file] = blackKing;

        this.result = other.result;

        this.allLegalPlies = other.allLegalPlies;

        this.fiftyMovePlyCounter = Integer.MIN_VALUE;
        positionRepetitionCounter = null;

        isRealBoard = false;
    }

    private static void copyPieceArray(Board from, Board to) {
        for (int rank = 0; rank < 8; rank++)
            for (int file = 0; file < 8; file++)
                to.board[rank][file] = from.board[rank][file].clone();
    }

    @Override
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public Square getEnPassant() {
        if (enPassant == null) return null;
        return enPassant.copy();
    }

    @Override
    public boolean isEnPassantSquare(Square square) {
        return square.equals(enPassant);
    }

    private boolean canCastle(boolean isWhite, boolean isShort) {
        Square scanSqr = new Square(isWhite ? WHITE_KING_START_SQR : BLACK_KING_START_SQR);
        int step = isShort ? 1 : -1;
        for (scanSqr = new Square(scanSqr.rank, scanSqr.file + step);
             scanSqr.file != (isShort ? 7 : 0);
             scanSqr = new Square(scanSqr.rank, scanSqr.file + step)) {
            if (!get(scanSqr).isEmpty())
                return false;
        }
        return true;
    }

    public List<Ply> castleMoves() {
        if (isWhiteTurn) {
            return castleMovesWhite();
        } else {
            return castleMovesBlack();
        }
    }

    private List<Ply> castleMovesWhite() {
        List<Ply> ret = new ArrayList<>();
        Square from = new Square(0, 4);
        if (whiteShortCastleRight && canCastle(true, true)) {
            Square to = new Square(0, 6);
            ret.add(new Ply(from, to, false, false, true, false));
        }
        if (whiteLongCastleRight && canCastle(true, false)) {
            Square to = new Square(0, 2);
            ret.add(new Ply(from, to, false, false, false, true));
        }
        return ret;
    }

    private List<Ply> castleMovesBlack() {
        List<Ply> ret = new ArrayList<>();
        Square from = new Square(7, 4);
        if (blackShortCastleRight && canCastle(false, true)) {
            Square to = new Square(7, 6);
            ret.add(new Ply(from, to, false, false, true, false));
        }
        if (blackLongCastleRight && canCastle(false, false)) {
            Square to = new Square(7, 2);
            ret.add(new Ply(from, to, false, false, false, true));
        }
        return ret;
    }

    public void move(int rankFrom, int fileFrom, int rankTo, int fileTo) {
        move(new Square(rankFrom, fileFrom), new Square(rankTo, fileTo));
    }

    public void move(Ply ply) {
        if (result != Result.PLAYING)
            throw new IllegalArgumentException("the game is over. go home!");
        if (getAllLegalPlies().contains(ply)) {
            _move(ply);
            return;
        }
        throw new IllegalArgumentException("no ply from " +
                ply.getFrom() + " to " + ply.getTo());
    }

    public void move(Square from, Square to) {
//        if(result != Result.PLAYING)
//            throw new IllegalArgumentException("the game is over. go home!");
        Ply m = new Ply(from, to);
        for (Ply ply : getAllLegalPlies()) {
            if (ply.isSameFromTo(m)) {
                _move(ply);
                return;
            }
        }
        throw new IllegalArgumentException("no legal move from " + from + " to " + to);
    }

    private void _move(Ply ply) {
        boolean isFiftyMoveBreaker = false;

        Square from = ply.getFrom(), to = ply.getTo();

        if (isPawn(from) || !get(to).isEmpty())
            isFiftyMoveBreaker = true;

        _rawMove(from, to);

        enPassant = null;
        if (ply.isPawnJump) {
            enPassant = new Square(to.rank + (isWhiteTurn ? -1 : 1), to.file);
        }
        if (ply.isEnPassant) {
            int jumpedPawnRank = to.rank + (isWhiteTurn ? -1 : 1);
            board[jumpedPawnRank][to.file] = EP;
        }
        if (ply.isCastleShort) {
            from = isWhiteTurn ? WHITE_SHORT_ROOK_START_SQR : BLACK_SHORT_ROOK_START_SQR;
            to = new Square(from.rank, from.file - 2);
            _rawMove(from, to);
        }
        if (ply.isCastleLong) {
            from = isWhiteTurn ? WHITE_LONG_ROOK_START_SQR : BLACK_LONG_ROOK_START_SQR;
            to = new Square(from.rank, from.file + 3);
            _rawMove(from, to);
        }
        updateWhiteCastleRights(ply);
        updateBlackCastleRights(ply);

        if (get(ply.getTo()) instanceof Pawn) {
            Pawn pawn = (Pawn) get(ply.getTo());
            if (pawn.isPromotionRank(ply.rankTo))
                switch (ply.promotedTo) {
                    case QUEEN:
                        board[ply.rankTo][ply.fileTo] = new Queen(pawn.isWhite(), ply.rankTo, ply.fileTo);
                        break;
                    case ROOK:
                        board[ply.rankTo][ply.fileTo] = new Rook(pawn.isWhite(), ply.rankTo, ply.fileTo);
                        break;
                    case BISHOP:
                        board[ply.rankTo][ply.fileTo] = new Bishop(pawn.isWhite(), ply.rankTo, ply.fileTo);
                        break;
                    case KNIGHT:
                        board[ply.rankTo][ply.fileTo] = new Knight(pawn.isWhite(), ply.rankTo, ply.fileTo);
                        break;

                    case NO_PROMOTION:
                        throw new IllegalArgumentException("must promote to something");
                }
        }

        isWhiteTurn = !isWhiteTurn;

        if (isRealBoard) {
            updateAllLegalMoves();

            if (isFiftyMoveBreaker)
                fiftyMovePlyCounter = 0;
            else
                fiftyMovePlyCounter++;

            positionRepetitionCounter.put(this, getPositionRepetitions() + 1);
        }
    }

    private void updateWhiteCastleRights(Ply ply) {
        if (ply.getFrom().equals(WHITE_KING_START_SQR)) {
            whiteShortCastleRight = false;
            whiteLongCastleRight = false;
        }
        if (ply.getFrom().equals(WHITE_SHORT_ROOK_START_SQR))
            whiteShortCastleRight = false;
        if (ply.getFrom().equals(WHITE_LONG_ROOK_START_SQR))
            whiteLongCastleRight = false;
    }

    private void updateBlackCastleRights(Ply ply) {
        if (ply.getFrom().equals(BLACK_KING_START_SQR)) {
            blackShortCastleRight = false;
            blackLongCastleRight = false;
        }
        if (ply.getFrom().equals(BLACK_SHORT_ROOK_START_SQR))
            blackShortCastleRight = false;
        if (ply.getFrom().equals(BLACK_LONG_ROOK_START_SQR))
            blackLongCastleRight = false;
    }

    public boolean isInCheck(boolean isWhite) {
        Piece myKing = isWhite ? whiteKing : blackKing;
        for (Ply nextMove : getAllMoves(!isWhite)) {
            if (nextMove.getTo().equals(myKing.getSquare()))
                return true;
        }
        return false;
    }

    private void _rawMove(Square from, Square to) {
        get(from).setSquare(to);
        board[to.rank][to.file] = get(from);
        board[from.rank][from.file] = EP;
    }

    private int getPositionRepetitions() {
        if (positionRepetitionCounter.containsKey(this))
            return positionRepetitionCounter.get(this);
        return 0;
    }

    @Override
    public boolean isPawn(Square square) {
        return get(square) instanceof Pawn;
    }

    public Piece get(Square square) {
        return board[square.rank][square.file];
    }

    public Piece get(int rank, int file) {
        return board[rank][file];
    }

    public List<Ply> getAllMoves(boolean isWhite) {
        List<Ply> moves = new ArrayList<>();
        for (Piece[] rank : board)
            for (Piece piece : rank)
                if (isOfColor(isWhite, piece))
                    moves.addAll(piece.moves(this));
        return moves;
    }

    private boolean _isLegal(Ply ply) {
        _move(ply);
        return !isInCheck(!isWhiteTurn);
    }

    public boolean isLegal(Ply ply) {
        Board copy = new Board(this);
        return copy._isLegal(ply);
    }

    private void updateAllLegalMoves() {
        List<Ply> moves = new ArrayList<>();
        for (Ply ply : getAllMoves(isWhiteTurn)) {
            if (isLegal(ply))
                moves.add(ply);
        }
        allLegalPlies = moves;
    }

    @Override
    public List<Ply> getAllLegalPlies() {
        return allLegalPlies;
    }

    public boolean isGameOver() {
        return allLegalPlies.size() == 0 || fiftyMovePlyCounter >= 100;
    }

    @Override
    public Result getResult() {
        if (!isGameOver()) {
            return Result.PLAYING;
        }
        if (!isInCheck(isWhiteTurn()) || fiftyMovePlyCounter >= 100)
            return Result.DRAW;
        if (isWhiteTurn())
            return Result.BLACK_VICTORY;
        else
            return Result.WHITE_VICTORY;
    }

    public static boolean isOnBoard(int rank, int file) {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    public static boolean isOnBoard(Square move) {
        return isOnBoard(move.rank, move.file);
    }

    public static boolean isOfColor(boolean isWhite, Piece p) {
        return isWhite ? p.isWhite() : p.isBlack();
    }

    private boolean arePieceEqual(Board other) {
        for (int rank = 0; rank < 8; rank++)
            for (int file = 0; file < 8; file++)
                if (!this.board[rank][file].equals(other.board[rank][file]))
                    return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Board))
            return false;
        Board other = (Board) o;
        return this.arePieceEqual(other) &&
                this.isWhiteTurn == other.isWhiteTurn &&
                this.enPassant.equals(other.enPassant) &&
                this.whiteShortCastleRight == other.whiteShortCastleRight &&
                this.whiteLongCastleRight == other.whiteLongCastleRight &&
                this.blackShortCastleRight == other.blackShortCastleRight &&
                this.blackLongCastleRight == other.blackLongCastleRight;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                ret.append(board[rank][file]);
            }
            ret.append(System.lineSeparator());
        }
        ret.append((isWhiteTurn ? "white" : "black") + " to play" + System.lineSeparator());
        return ret.toString();
    }
}
