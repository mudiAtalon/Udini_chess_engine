package players;

import board.Ply;
import board.Square;

import java.util.Scanner;

public class CommandLinePlayer implements Player {

    private Scanner input;

    public CommandLinePlayer() {
        input = new Scanner(System.in);
    }

    @Override
    public Ply getChosenMove(Position pos) {
        String line = input.nextLine();
        if (line.equals("o-o"))
            if (pos.isWhiteTurn())
                return Ply.WHITE_SHORT_CASTLE;
            else
                return Ply.BLACK_SHORT_CASTLE;
        if (line.equals("o-o-o"))
            if (pos.isWhiteTurn())
                return Ply.WHITE_LONG_CASTLE;
            else
                return Ply.BLACK_LONG_CASTLE;

        Square from = getSquareFromString(line.substring(0, 2)),
                to = getSquareFromString(line.substring(3, 5));

        if (isPromotion(pos, from, to)) {
            Ply.Promotion promotion = getPromotionFromString(line.substring(6));
            return new Ply(from, to, promotion);
        }

        if (isPawnJump(pos, from, to))
            return new Ply(from, to, true);

        if (isEnPassant(pos, from, to))
            return new Ply(from, to, false, true);

        return new Ply(from, to);
    }

    private Square getSquareFromString(String strSqr) {
        char charFile = strSqr.charAt(0), charRank = strSqr.charAt(1);
        if ((charFile < 65 || charFile >= 73) && (charFile < 97 || charFile >= 105))
            throw new IllegalArgumentException(strSqr + "is not a square");
        if (charRank < 49 || charRank >= 57)
            throw new IllegalArgumentException(strSqr + "is not a square");

        int rank = charRank - '1', file = charFile - 'a';
        return new Square(rank, file);
    }

    private Ply.Promotion getPromotionFromString(String strPieceType) {
        switch (strPieceType) {
            case "queen":
                return Ply.Promotion.QUEEN;
            case "rook":
                return Ply.Promotion.ROOK;
            case "bishop":
                return Ply.Promotion.BISHOP;
            case "knight":
                return Ply.Promotion.KNIGHT;
            default:
                throw new IllegalArgumentException("can't promote to that");
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

    private boolean isPawnJump(Position pos, Square from, Square to) {
        if (pos.isWhiteTurn())
            return from.rank == 1 && to.rank == 3 && pos.isPawn(from);
        else
            return from.rank == 6 && to.rank == 4 && pos.isPawn(from);

    }

    private boolean isEnPassant(Position pos, Square from, Square to) {
        return pos.isPawn(from) && pos.isEnPassantSquare(to);
    }
}
