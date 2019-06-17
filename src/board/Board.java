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

    private static final Square WHITE_SHORT_ROOK_START_SQR = new Square(7, 7),
            WHITE_LONG_ROOK_START_SQR = new Square(7, 0),
            WHITE_KING_START_SQR = new Square(7, 4),
            BLACK_SHORT_ROOK_START_SQR = new Square(0, 7),
            BLACK_LONG_ROOK_START_SQR = new Square(0, 0),
            BLACK_KING_START_SQR = new Square(0, 4);

    private final Piece[][] board = new Piece[8][8];
    private final List<Piece>[][] whiteSqrControl = new List[8][8],
            blackSqrControl = new List[8][8];//I hate java it should have generic arrays
    private EmptyPiece EP;
    private boolean isWhiteTurn;
    private Square enPassant;
    private boolean whiteShortCastleRight,
            whiteLongCastleRight,
            blackShortCastleRight,
            blackLongCastleRight;
    private Piece whiteKing, blackKing;

    private List<Move> allMoves;

//    private Result result;

//    private enum Result{
//        WHITE_WINS,
//        BLACK_WINS,
//        DRAW,
//        PLAYING
//    }

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

        for (int rank = 0; rank < 8; rank++)
            for (int file = 0; file < 8; file++) {
                whiteSqrControl[rank][file] = new ArrayList<>();
                blackSqrControl[rank][file] = new ArrayList<>();
            }
        for (Piece[] file : board)
            for (Piece piece : file) {
                if (piece.isEmpty())
                    continue;

                if (piece.isWhite())
                    for (Square to : piece.sqrControl(this))
                        whiteSqrControl[to.rank][to.file].add(piece);
                else
                    for (Square to : piece.sqrControl(this))
                        blackSqrControl[to.rank][to.file].add(piece);
            }

//        result = Result.PLAYING;
    }

    public Square getEnPassant() {
        if (enPassant == null) return null;
        return enPassant.copy();
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

    public List<Move> castleMoves() {
        if (isWhiteTurn) {
            return castleMovesWhite();
        } else {
            return castleMovesBlack();
        }
    }

    private List<Move> castleMovesWhite() {
        List<Move> ret = new ArrayList<>();
        Square from = new Square(7, 4);
        if (whiteShortCastleRight && canCastle(true, true)) {
            Square to = new Square(7, 6);
            ret.add(new Move(from, to, false, false, true, false));
        }
        if (whiteLongCastleRight && canCastle(true, false)) {
            Square to = new Square(7, 2);
            ret.add(new Move(from, to, false, false, false, true));
        }
        return ret;
    }

    private List<Move> castleMovesBlack() {
        List<Move> ret = new ArrayList<>();
        Square from = new Square(0, 4);
        if (blackShortCastleRight && canCastle(false, true)) {
            Square to = new Square(0, 6);
            ret.add(new Move(from, to, false, false, true, false));
        }
        if (blackLongCastleRight && canCastle(false, false)) {
            Square to = new Square(0, 2);
            ret.add(new Move(from, to, false, false, false, true));
        }
        return ret;
    }

    public void move(int rankFrom, int fileFrom, int rankTo, int fileTo) {
        move(new Square(rankFrom, fileFrom), new Square(rankTo, fileTo));
    }

    public void move(Move m) {
//        if(result != Result.PLAYING)
//            throw new IllegalArgumentException("the game is over. go home!");
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
        throw new IllegalArgumentException("no move from " +
                m.getFrom() + " to " + m.getTo());
    }

    public void move(Square from, Square to) {
//        if(result != Result.PLAYING)
//            throw new IllegalArgumentException("the game is over. go home!");
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
        throw new IllegalArgumentException("no legal move from " + from + " to " + to);
    }

    private void _move(Move move) {
        Square from = move.getFrom(), to = move.getTo();

        List<Piece> changedControllingPieces = new ArrayList<>();
        changedControllingPieces.add(get(from));

        _rawMove(from, to);

        changedControllingPieces.addAll(getControllingPieces(from));
        changedControllingPieces.addAll(getControllingPieces(to));

        enPassant = null;
        if (move.isPawnJump) {
            enPassant = new Square(to.rank + (isWhiteTurn ? 1 : -1), to.file);
        }
        if (move.isEnPassant) {
            int jumpedPawnRank = to.rank + (isWhiteTurn ? 1 : -1);
            changedControllingPieces.add(get(jumpedPawnRank, to.file));
            board[jumpedPawnRank][to.file] = EP;
        }
        if (move.isCastleShort) {
            from = isWhiteTurn ? WHITE_SHORT_ROOK_START_SQR : BLACK_SHORT_ROOK_START_SQR;
            to = new Square(from.rank, from.file - 2);
            changedControllingPieces.add(get(from));
            _rawMove(from, to);
        }
        if (move.isCastleLong) {
            from = isWhiteTurn ? WHITE_LONG_ROOK_START_SQR : BLACK_LONG_ROOK_START_SQR;
            to = new Square(from.rank, from.file + 3);
            changedControllingPieces.add(get(from));
            _rawMove(from, to);
        }
        updateWhiteCastleRights(move);
        updateBlackCastleRights(move);

        if (get(move.getTo()) instanceof Pawn) {
            Pawn pawn = (Pawn) get(move.getTo());
            if (pawn.isPromotionRank(move.rankTo))
                switch (move.promotedTo) {
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

        for(Piece piece : changedControllingPieces)
            removePieceSqrControl(piece);
        for(Piece piece : changedControllingPieces)
            addPieceSqrControl(piece);

        isWhiteTurn = !isWhiteTurn;

//        setAllMoves(isWhiteTurn);
//        if(isInCheck(!isWhiteTurn)) throw new IllegalArgumentException("can't walk into check");

//        result = getUpdatedResult(!isWhiteTurn);
//
//        if(result != Result.PLAYING)
//            System.out.println(result);
    }

    private void updateWhiteCastleRights(Move move) {
        if (move.getFrom().equals(WHITE_KING_START_SQR)) {
            whiteShortCastleRight = false;
            whiteLongCastleRight = false;
        }
        if (move.getFrom().equals(WHITE_SHORT_ROOK_START_SQR))
            whiteShortCastleRight = false;
        if (move.getFrom().equals(WHITE_LONG_ROOK_START_SQR))
            whiteLongCastleRight = false;
    }

    private void updateBlackCastleRights(Move move) {
        if (move.getFrom().equals(BLACK_KING_START_SQR)) {
            blackShortCastleRight = false;
            blackLongCastleRight = false;
        }
        if (move.getFrom().equals(BLACK_SHORT_ROOK_START_SQR))
            blackShortCastleRight = false;
        if (move.getFrom().equals(BLACK_LONG_ROOK_START_SQR))
            blackLongCastleRight = false;
    }

    private boolean isInCheck(boolean isWhite) {
        Piece myKing = isWhite ? whiteKing : blackKing;
        for (Move nextMove : getAllMoves()) {
            if (nextMove.getTo().equals(myKing.getSquare()))
                return true;
        }
        return false;
    }

//    private Result getUpdatedResult(boolean whitePlayedLast){
//        if(getAllMoves().size() > 0)
//            return Result.PLAYING;
//        setAllMoves(whitePlayedLast);
//        if(!isInCheck(!whitePlayedLast))
//            return Result.DRAW;
//        if(whitePlayedLast)
//            return Result.WHITE_WINS;
//        else
//            return Result.BLACK_WINS;
//    }

    private List<Piece> getControllingPieces(Square sqr){
        List<Piece> ret = new ArrayList<>(whiteSqrControl[sqr.rank][sqr.file]);
        ret.addAll(blackSqrControl[sqr.rank][sqr.file]);
        return ret;
    }

    private void addPieceSqrControl(Piece piece){
        if (piece.isEmpty()) return;
        List<Square> newControlledSqrs = piece.sqrControl();
        List[][] squareControlBoard = piece.isWhite() ? whiteSqrControl : blackSqrControl;
        for(Square sqr : newControlledSqrs)
            squareControlBoard[sqr.rank][sqr.file].add(sqr);
    }

    private void removePieceSqrControl(Piece piece) {
        if (piece.isEmpty()) return;
        List<Square> oldControlledSqrs = piece.getControlledSquares();
        List[][] squareControlBoard = piece.isWhite() ? whiteSqrControl : blackSqrControl;
        for (Square sqr : oldControlledSqrs)
            squareControlBoard[sqr.rank][sqr.file].remove(sqr);
    }

    private void _rawMove(Square from, Square to) {
        get(from).setSquare(to);
        board[to.rank][to.file] = get(from);
        board[from.rank][from.file] = EP;
    }

    public Piece get(Square square) {
        return board[square.rank][square.file];
    }

    public Piece get(int rank, int file){
        return board[rank][file];
    }

    public void setAllMoves(boolean forWhite) {
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

//    public boolean isLegal(Move move){
//
//    }

//    public List<Move> getAllLegalMoves(){
//        List<Move> ret = new ArrayList<>();
//        for(Move move : allMoves){
//
//        }
//    }

    public static boolean isOnBoard(int rank, int file) {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }

    public static boolean isOnBoard(Square move) {
        return isOnBoard(move.rank, move.file);
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
        b.move(7, 0, 0, 0);
    }
}
