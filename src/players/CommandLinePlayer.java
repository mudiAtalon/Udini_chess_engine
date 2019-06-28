package players;

import board.Move;
import board.Square;
import board.pieces.Piece;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import java.util.Scanner;

public class CommandLinePlayer implements Player {

    Scanner input;

    public CommandLinePlayer() {
        input = new Scanner(System.in);
    }

    @Override
    public Move getChosenMove(Position pos) {
        if(isCastleFromUser("do you want to castle? (true/false)")){
            return getCastleMove(pos.isWhiteTurn(), isShortCastleFromUser("castle short? (true/false)"));
        }

        Square from = getSquareFromUser("enter origin square"),
                to = getSquareFromUser("enter destination square");

        if (isPromotion(pos, from, to)) {
            Move.Promotion promotion = getPromotionFromUser("promote to a:");
            return new Move(from, to, promotion);
        }

        if(isPawnJump(pos, from, to))
            return new Move(from, to, true);

        if(isEnPassant(pos, from, to))
            return new Move(from, to, false, true);

        return new Move(from, to);
    }

    private boolean isCastleFromUser(String message){
        System.out.println(message);
        if(!input.hasNextBoolean())
            throw new IllegalArgumentException("not a boolean");
        return input.nextBoolean();
    }

    private boolean isShortCastleFromUser(String message){
        System.out.println(message);
        if(!input.hasNextBoolean())
            throw new IllegalArgumentException("not a boolean");
        return input.nextBoolean();
    }

    private Square getSquareFromUser(String message) {
        System.out.println(message);
        String strSqr = input.next();
        if (strSqr.length() != 2)
            throw new IllegalArgumentException("not a square");
        char charFile = strSqr.charAt(0), charRank = strSqr.charAt(1);
        if ((charFile < 65 || charFile >= 73) && (charFile < 97 || charFile >= 105))
            throw new IllegalArgumentException("not a square");
        if (charRank < 49 || charRank >= 57)
            throw new IllegalArgumentException("not a square");

        int rank = charRank - '1', file = charFile -'a';
        return new Square(rank, file);
    }

    private Move.Promotion getPromotionFromUser(String message) {
        System.out.println(message);
        String strPieceType = input.next().toLowerCase();
        switch (strPieceType) {
            case "queen":
                return Move.Promotion.QUEEN;
            case "rook":
                return Move.Promotion.ROOK;
            case "bishop":
                return Move.Promotion.BISHOP;
            case "knight":
                return Move.Promotion.KNIGHT;
            default:
                throw new IllegalArgumentException("can't promote to that");
        }
    }

    private Move getCastleMove(boolean isWhite, boolean isShort){
        if(isShort) {
            if(isWhite)
                return Move.WHITE_SHORT_CASTLE;
            else
                return Move.BLACK_SHORT_CASTLE;
        }
        else{
            if(isWhite)
                return Move.WHITE_LONG_CASTLE;
            else
                return Move.BLACK_SHORT_CASTLE;
        }
    }

    private boolean isPromotion(Position pos, Square from, Square to) {
        if (pos.isWhiteTurn())
            return (pos.isPawn(from))
                    && to.rank == 7;
        else
            return (pos.isPawn(from))
                    && to.rank == 0;
    }

    private boolean isPawnJump(Position pos, Square from, Square to){
        if(pos.isWhiteTurn())
            return from.rank == 1 && to.rank == 3 && pos.isPawn(from);
        else
            return from.rank == 6 && to.rank == 4 && pos.isPawn(from);

    }

    private boolean isEnPassant(Position pos, Square from, Square to){
        return pos.isPawn(from) && pos.isEnPassantSquare(to);
    }
}
