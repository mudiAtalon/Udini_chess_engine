package board;

public class Move {

    public static final Move WHITE_SHORT_CASTLE = new Move(7, 4, 7, 6, Promotion.NO_PROMOTION, false, false, true, false),
            WHITE_LONG_CASTLE = new Move(7, 4, 7, 2, Promotion.NO_PROMOTION, false, false, true, false),
            BLACK_SHORT_CASTLE = new Move(0, 4, 0, 6, Promotion.NO_PROMOTION, false, false, true, false),
            BLACK_LONG_CASTLE = new Move(0, 4, 0, 2, Promotion.NO_PROMOTION, false, false, true, false);

    public final int rankFrom, fileFrom, rankTo, fileTo;
    public final boolean isPawnJump, isEnPassant;
    public final boolean isCastleShort, isCastleLong;
    public final Promotion promotedTo;

    public Move(Square from, Square to) {
        this(from.rank, from.file, to.rank, to.file);
    }

    public Move(Square from, Square to, boolean isPawnJump) {
        this(from.rank, from.file, to.rank, to.file, Promotion.NO_PROMOTION, isPawnJump, false, false, false);
    }

    public Move(Square from, Square to, boolean isPawnJump, boolean isEnPassant) {
        this(from.rank, from.file, to.rank, to.file, Promotion.NO_PROMOTION, isPawnJump, isEnPassant, false, false);
    }

    public Move(Square from, Square to, boolean isPawnJump, boolean isEnPassant, boolean isCastleShort, boolean isCastleLong) {
        this(from.rank, from.file, to.rank, to.file, Promotion.NO_PROMOTION, isPawnJump, isEnPassant, isCastleShort, isCastleLong);
    }

    public Move(Square from, Square to, Promotion promotedTo) {
        this(from.rank, from.file, to.rank, to.file, promotedTo, false, false, false, false);
    }

    public Move(int rankFrom, int fileFrom, int rankTo, int fileTo) {
        this(rankFrom, fileFrom, rankTo, fileTo, Promotion.NO_PROMOTION, false, false, false, false);
    }

    public Move(int rankFrom, int fileFrom, int rankTo, int fileTo, Promotion promotedTo) {
        this(rankFrom, fileFrom, rankTo, fileTo, promotedTo, false, false, false, false);
    }

    public Move(int rankFrom, int fileFrom, int rankTo, int fileTo, Promotion promotedTo, boolean isPawnJump, boolean isEnPassant, boolean isCastleShort, boolean isCastleLong) {
        this.rankFrom = rankFrom;
        this.fileFrom = fileFrom;
        this.rankTo = rankTo;
        this.fileTo = fileTo;
        this.isPawnJump = isPawnJump;
        this.isEnPassant = isEnPassant;
        this.isCastleShort = isCastleShort;
        this.isCastleLong = isCastleLong;
        this.promotedTo = promotedTo;
    }

    public Square getFrom() {
        return new Square(rankFrom, fileFrom);
    }

    public Square getTo() {
        return new Square(rankTo, fileTo);
    }

    public boolean isTo(Square to) {
        return to.rank == rankTo && to.file == fileTo;
    }

    public enum Promotion {
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        NO_PROMOTION
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move))
            return false;
        Move other = (Move) o;
        return rankFrom == other.rankFrom &&
                fileFrom == other.fileFrom &&
                rankTo == other.rankTo &&
                fileTo == other.fileTo &&
                isPawnJump == other.isPawnJump &&
                isEnPassant == other.isEnPassant &&
                isCastleShort == other.isCastleShort &&
                isCastleLong == other.isCastleLong &&
                promotedTo == other.promotedTo;

    }

    public boolean isSameFromTo(Move other) {
        return rankFrom == other.rankFrom &&
                fileFrom == other.fileFrom &&
                rankTo == other.rankTo &&
                fileTo == other.fileTo;
    }

    @Override
    public String toString() {
        return "move from (" +
                rankFrom + ", " + fileFrom +
                ") to (" +
                rankTo + ", " + fileTo +
                ")";
    }
}
