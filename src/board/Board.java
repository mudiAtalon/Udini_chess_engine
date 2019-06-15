package board;

import board.pieces.Piece;

import java.util.*;
import board.pieces.*;

public class Board {

    private static Board instance = null;

    public static Board getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    private static void init() {
        instance = new Board();
    }

    private static final int[] WHITE_SHORT_ROOK_START_SQR = new int[]{7, 7},
            WHITE_LONG_ROOK_START_SQR = new int[]{7, 0},
            WHITE_KING_START_SQR = new int[]{7, 4},
            BLACK_SHORT_ROOK_START_SQR = new int[]{0, 7},
            BLACK_LONG_ROOK_START_SQR = new int[]{0, 0},
            BLACK_KING_START_SQR = new int[]{0, 4};

    private final Piece[][] board = new Piece[8][8];
    private EmptyPiece EP;
    private boolean isWhiteTurn;
    private int[] enPassant;
    private boolean whiteShortCastleRight,
            whiteLongCastleRight,
            blackShortCastleRight,
            blackLongCastleRight;
    private Piece whiteKing, blackKing;

    private List<Move> allMoves;

    private Result result;

    private enum Result{
        WHITE_WINS,
        BLACK_WINS,
        DRAW,
        PLAYING
    }

    private Board() {
        EP = EmptyPiece.getInstance();

        enPassant = null;
        whiteShortCastleRight = true;
        whiteLongCastleRight = true;
        blackShortCastleRight = true;
        blackLongCastleRight = true;

        isWhiteTurn = true;

        whiteKing = new King(true, 7, 4);
        blackKing = new King(false, 0, 4);

        board[0][0] = new Rook(false, 0, 0);
        board[0][1] = new Knight(false, 0, 1);
        board[0][2] = new Bishop(false, 0, 2);
        board[0][3] = new Queen(false, 0, 3);
        board[0][4] = blackKing;
        board[0][5] = new Bishop(false, 0, 5);
        board[0][6] = new Knight(false, 0, 6);
        board[0][7] = new Rook(false, 0, 7);
        for (int file = 0; file < 8; file++) {
            board[1][file] = new Pawn(false, 1, file);
        }
        for (int rank = 2; rank < 6; rank++)
            for (int file = 0; file < 8; file++) {
                board[rank][file] = EP;
            }
        for (int file = 0; file < 8; file++) {
            board[6][file] = new Pawn(true, 6, file);
        }
        board[7][0] = new Rook(true, 7, 0);
        board[7][1] = new Knight(true, 7, 1);
        board[7][2] = new Bishop(true, 7, 2);
        board[7][3] = new Queen(true, 7, 3);
        board[7][4] = whiteKing;
        board[7][5] = new Bishop(true, 7, 5);
        board[7][6] = new Knight(true, 7, 6);
        board[7][7] = new Rook(true, 7, 7);

        result = Result.PLAYING;
    }

    public int[] getEnPassant(){
        if(enPassant == null) return null;
        return Arrays.copyOf(enPassant, 2);
    }

    private boolean canCastle(boolean isWhite, boolean isShort) {
        int[] scanSqr = Arrays.copyOf(isWhite ? WHITE_KING_START_SQR : BLACK_KING_START_SQR, 2);
        int step = isShort ? 1 : -1;
        for (scanSqr[1] += step; scanSqr[1] != (isShort ? 7 : 0); scanSqr[1] += step) {
            if (!get(scanSqr).isEmpty())
                return false;
        }
        return true;
    }

    public List<Move> castleMoves(){
        if (isWhiteTurn) {
            return castleMovesWhite();
        } else {
            return castleMovesBlack();
        }
    }

    private List<Move> castleMovesWhite(){
        List<Move> ret = new ArrayList<>();
        int[] from = new int[]{7, 4};
        if (whiteShortCastleRight && canCastle(true, true)) {
            int[] to = new int[]{7, 6};
            ret.add(new Move(from, to, false, false, true, false));
        }
        if (whiteLongCastleRight && canCastle(true, false)) {
            int[] to = new int[]{7, 2};
            ret.add(new Move(from, to, false, false, false, true));
        }
        return ret;
    }

    private List<Move> castleMovesBlack() {
        List<Move> ret = new ArrayList<>();
        int[] from = new int[]{0, 4};
        if (blackShortCastleRight && canCastle(false, true)) {
            int[] to = new int[]{0, 6};
            ret.add(new Move(from, to, false, false, true, false));
        }
        if (blackLongCastleRight && canCastle(false, false)) {
            int[] to = new int[]{0, 2};
            ret.add(new Move(from, to, false, false, false, true));
        }
        return ret;
    }

    public void move(int rankFrom, int fileFrom, int rankTo, int fileTo) {
        move(new int[]{rankFrom, fileFrom}, new int[]{rankTo, fileTo});
    }

    public void move(Move m){
        if(result != Result.PLAYING)
            throw new IllegalArgumentException("the game is over. go home!");
        if (!isOfColor(isWhiteTurn, get(m.getFrom())))
            throw new IllegalArgumentException("wait for your turn");
        System.out.println("start");
        List<Move> moves = get(m.getFrom()).moves();
        for (Move move : moves)
            System.out.println(move);
        for (Move move : moves) {
            if (move.isTo(m.getTo())) {
                _move(m);
                return;
            }
        }
        throw new IllegalArgumentException("no legal move from " +
                Arrays.toString(m.getFrom()) + " to " + Arrays.toString(m.getTo()));
    }

    public void move(int[] from, int[] to) {
        if(result != Result.PLAYING)
            throw new IllegalArgumentException("the game is over. go home!");
        if (!isOfColor(isWhiteTurn, get(from)))
            throw new IllegalArgumentException("wait for your turn");
        System.out.println("start");
        List<Move> moves = get(from).moves();
        for (Move move : moves)
            System.out.println(move);
        for (Move move : moves) {
            if (move.isTo(to)) {
                _move(move);
                return;
            }
        }
        throw new IllegalArgumentException("no legal move from " + Arrays.toString(from) + " to " + Arrays.toString(to));
    }

    private void _move(Move move) {
        int[] from = move.getFrom(), to = move.getTo();
        _rawMove(from, to);
        enPassant = null;
        if (move.isPawnJump) {
            enPassant = new int[]{to[0] + (isWhiteTurn ? 1 : -1), to[1]};
        }
        if (move.isEnPassant) {
            int jumpedPawnRank = to[0] + (isWhiteTurn ? 1 : -1);
            board[jumpedPawnRank][to[1]] = EP;
        }
        if (move.isCastleShort) {
            from = isWhiteTurn ? WHITE_SHORT_ROOK_START_SQR : BLACK_SHORT_ROOK_START_SQR;
            to = new int[]{from[0], from[1] - 2};
            _rawMove(from, to);
        }
        if (move.isCastleLong) {
            from = isWhiteTurn ? WHITE_LONG_ROOK_START_SQR : BLACK_LONG_ROOK_START_SQR;
            to = new int[]{from[0], from[1] + 3};
            _rawMove(from, to);
        }
        updateWhiteCastleRights(move);
        updateBlackCastleRights(move);

        if(get(move.getTo()) instanceof Pawn){
            Pawn pawn = (Pawn) get(move.getTo());
            if(pawn.isPromotionRank(move.rankTo))
                switch(move.promotedTo){
                    case QUEEN:
                        board[move.rankTo][move.fileTo] = new Queen(pawn.isWhite(), move.rankTo, move.fileTo);
                        break;
                    case ROOK:
                        board[move.rankTo][move.fileTo] = new Rook(pawn.isWhite(), move.rankTo, move.fileTo);
                        break;
                    case BISHOP:
                        board[move.rankTo][move.fileTo] = new Bishop(pawn.isWhite(), move.rankTo, move.fileTo);
                        break;
                    case KNIGHT:
                        board[move.rankTo][move.fileTo] = new Knight(pawn.isWhite(), move.rankTo, move.fileTo);
                        break;

                    case NO_PROMOTION:
                        throw new IllegalArgumentException("must promote to something");
                }
        }

        isWhiteTurn = !isWhiteTurn;

        setAllMoves(isWhiteTurn);
        if(isInCheck(!isWhiteTurn)) throw new IllegalArgumentException("can't walk into check");

        result = getUpdatedResult(!isWhiteTurn);

        if(result != Result.PLAYING)
            System.out.println(result);
    }

    private void updateWhiteCastleRights(Move move){
        if (Arrays.equals(move.getFrom(), WHITE_KING_START_SQR)) {
            whiteShortCastleRight = false;
            whiteLongCastleRight = false;
        }
        if (Arrays.equals(move.getFrom(), WHITE_SHORT_ROOK_START_SQR))
            whiteShortCastleRight = false;
        if (Arrays.equals(move.getFrom(), WHITE_LONG_ROOK_START_SQR))
            whiteLongCastleRight = false;
    }

    private void updateBlackCastleRights(Move move){
        if (Arrays.equals(move.getFrom(), BLACK_KING_START_SQR)) {
            blackShortCastleRight = false;
            blackLongCastleRight = false;
        }
        if (Arrays.equals(move.getFrom(), BLACK_SHORT_ROOK_START_SQR))
            blackShortCastleRight = false;
        if (Arrays.equals(move.getFrom(), BLACK_LONG_ROOK_START_SQR))
            blackLongCastleRight = false;
    }

    private boolean isInCheck(boolean isWhite){
        Piece myKing = isWhite ? whiteKing : blackKing;
        for(Move nextMove :getAllMoves()){
            if(Arrays.equals(nextMove.getTo(), myKing.getSquare()))
                return true;
        }
        return false;
    }

    private Result getUpdatedResult(boolean whitePlayedLast){
        if(getAllMoves().size() > 0)
            return Result.PLAYING;
        setAllMoves(whitePlayedLast);
        if(!isInCheck(!whitePlayedLast))
            return Result.DRAW;
        if(whitePlayedLast)
            return Result.WHITE_WINS;
        else
            return Result.BLACK_WINS;
    }

    private void _rawMove(int[] from, int[] to) {
        get(from).setSquare(to);
        board[to[0]][to[1]] = get(from);
        board[from[0]][from[1]] = EP;
    }

    public Piece get(int[] square) {
        return board[square[0]][square[1]];
    }

    public void setAllMoves(boolean forWhite){
        List<Move> moves = new ArrayList<>();
        for (Piece[] rank : board)
            for (Piece piece : rank)
                if (isOfColor(forWhite, piece))
                    moves.addAll(piece.moves());
        allMoves = moves;
    }

    public List<Move> getAllMoves() {
        return allMoves;
    }

    public static boolean isOnBoard(int rank, int file) {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    public static boolean isOnBoard(int[] move) {
        return isOnBoard(move[0], move[1]);
    }

    public static boolean isOfColor(boolean isWhite, Piece p) {
        return isWhite ? p.isWhite() : p.isBlack();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                ret.append(board[rank][file]);
            }
            ret.append(System.lineSeparator());
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        Board b = getInstance();
        System.out.println(b);
        b.move(6, 5, 4, 5);
        b.move(1, 4, 3, 4);
        b.move(6, 6, 4, 6);
        b.move(0, 3, 4, 7);
        System.out.println(b);
        b.move(7,0,0, 0);
    }
}
