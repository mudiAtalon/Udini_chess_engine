package board;

public class Move {
    public final int rankFrom, fileFrom, rankTo, fileTo;
    public final boolean isPawnJump, isEnPassant;
    public final boolean isCastleShort, isCastleLong;
    public final Promotion promotedTo;

    public Move(int[] from, int[] to){
        this(from[0], from[1], to[0], to[1]);
    }

    public Move(int[] from, int[] to, boolean isPawnJump){
        this(from[0], from[1], to[0], to[1], Promotion.NO_PROMOTION, isPawnJump, false, false, false);
    }

    public Move(int[] from, int[] to, boolean isPawnJump, boolean isEnPassant){
        this(from[0], from[1], to[0], to[1], Promotion.NO_PROMOTION, isPawnJump, isEnPassant, false, false);
    }

    public Move(int[] from, int[] to, boolean isPawnJump, boolean isEnPassant, boolean isCastleShort, boolean isCastleLong){
        this(from[0], from[1], to[0], to[1], Promotion.NO_PROMOTION, isPawnJump, isEnPassant, isCastleShort, isCastleLong);
    }

    public Move(int[] from, int[] to, Promotion promotedTo){
        this(from[0], from[1], to[0], to[1], promotedTo, false, false, false, false);
    }

    public Move(int rankFrom, int fileFrom, int rankTo, int fileTo) {
        this(rankFrom, fileFrom, rankTo, fileTo, Promotion.NO_PROMOTION, false, false, false, false);
    }

    public Move(int rankFrom, int fileFrom, int rankTo, int fileTo, Promotion promotedTo){
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

    public int[] getFrom(){
        return new int[]{rankFrom, fileFrom};
    }

    public int[] getTo(){
        return new int[]{rankTo, fileTo};
    }

    public boolean isTo(int[] to){
        return to[0] == rankTo && to[1] == fileTo;
    }

    public enum Promotion {
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        NO_PROMOTION
    }

    @Override
    public boolean equals(Object o){
        if(! (o instanceof Move))
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

    @Override
    public String toString(){
        return "move from (" +
                rankFrom + ", " + fileFrom +
                ") to (" +
                rankTo + ", " + fileTo +
                ")";
    }
}
