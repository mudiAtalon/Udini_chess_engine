package bitBoard;

public class BitBoard {

    public static final long RANK_1, RANK_2, RANK_7, RANK_8, FILE_A, FILE_H;
    static {
        long query=0L;
        for(int ind=0; ind<8; ind++){
            query += 1L<<ind;
        }
        RANK_1 = query;
        query = 0L;
        for(int ind=8; ind<16; ind++){
            query += 1L<<ind;
        }
        RANK_2 = query;
        query = 0L;
        for(int ind=48; ind<56; ind++){
            query += 1L<<ind;
        }
        RANK_7 = query;
        query = 0L;
        for(int ind=56; ind<64; ind++){
            query += 1L<<ind;
        }
        RANK_8 = query;
        query = 0L;
        for(int ind=7; ind<64; ind+=8){
            query += 1L<<ind;
        }
        FILE_A = query;
        query = 0L;
        for(int ind=0; ind<64; ind+=8){
            query += 1L<<ind;
        }
        FILE_H = query;
    }

    private static final char[] DEFAULT_BOARD = new char[]{
            '\u265c', '\u265e', '\u265d', '\u265b', '\u265a', '\u265d', '\u265e', '\u265c',
            '\u265f', '\u265f', '\u265f', '\u265f', '\u265f', '\u265f', '\u265f', '\u265f',
            '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001',
            '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001',
            '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001',
            '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001', '\u2001',
            '\u2659', '\u2659', '\u2659', '\u2659', '\u2659', '\u2659', '\u2659', '\u2659',
            '\u2656', '\u2658', '\u2657', '\u2655', '\u2654', '\u2657', '\u2658', '\u2656'
    };

    private long WK=0L, WQ=0L, WR=0L, WB=0L, WN=0L, WP=0L, W=0L;
    private long BK=0L, BQ=0L, BR=0L, BB=0L, BN=0L, BP=0L, B=0L;
    private boolean isWhiteTurn;


    public BitBoard(){
        this(DEFAULT_BOARD);
    }

    public BitBoard(char[] board){
        isWhiteTurn = true;

        for(long ind=63, longVal=1L; ind>=0; ind--, longVal*=2L){
            switch(board[(int)ind]){
                case '\u2654':
                    WK+=longVal;
                    W+=longVal;
                    break;
                case '\u2655':
                    WQ+=longVal;
                    W+=longVal;
                    break;
                case '\u2656':
                    WR+=longVal;
                    W+=longVal;
                    break;
                case '\u2657':
                    WB+=longVal;
                    W+=longVal;
                    break;
                case '\u2658':
                    WN+=longVal;
                    W+=longVal;
                    break;
                case '\u2659':
                    WP+=longVal;
                    W+=longVal;
                    break;
                case '\u265a':
                    BK+=longVal;
                    B+=longVal;
                    break;
                case '\u265b':
                    BQ+=longVal;
                    B+=longVal;
                    break;
                case '\u265c':
                    BR+=longVal;
                    B+=longVal;
                    break;
                case '\u265d':
                    BB+=longVal;
                    B+=longVal;
                    break;
                case '\u265e':
                    BN+=longVal;
                    B+=longVal;
                    break;
                case '\u265f':
                    BP+=longVal;
                    B+=longVal;
                    break;
                default:
            }

        }
    }

    private char[] getCharArray(){
        char[] ret = new char[64];
        for(int ind=0; ind<64; ind++){
            ret[ind] = '\u2001';
            if((WK>>>(63-ind)&1L)==1)
                ret[ind]='\u2654';
            if((WQ>>>(63-ind)&1L)==1)
                ret[ind]='\u2655';
            if((WR>>>(63-ind)&1L)==1)
                ret[ind]='\u2656';
            if((WB>>>(63-ind)&1L)==1)
                ret[ind]='\u2657';
            if((WN>>>(63-ind)&1L)==1)
                ret[ind]='\u2658';
            if((WP>>>(63-ind)&1L)==1)
                ret[ind]='\u2659';

            if((BK>>>(63-ind)&1L)==1)
                ret[ind]='\u265a';
            if((BQ>>>(63-ind)&1L)==1)
                ret[ind]='\u265b';
            if((BR>>>(63-ind)&1L)==1)
                ret[ind]='\u265c';
            if((BB>>>(63-ind)&1L)==1)
                ret[ind]='\u265d';
            if((BN>>>(63-ind)&1L)==1)
                ret[ind]='\u265e';
            if((BP>>>(63-ind)&1L)==1)
                ret[ind]='\u265f';
        }
        return ret;
    }

    @Override
    public String toString(){
        char[] board = getCharArray();
        StringBuilder ret= new StringBuilder();
        for(int line=0; line<8; line++){
            for(int row=0; row<8; row++){
                ret.append(board[8 * line + row]).append('\u2000');
            }
            ret.append(System.lineSeparator());
        }
        ret.append(isWhiteTurn ? "white to move" : "black to move");
        return ret.toString();
    }

    public static String queryToString(long query){
        StringBuilder ret = new StringBuilder();
        for(int ind=63; ind>=0; ind--){
            ret.append((query>>>ind)&1L);
        }
        return ret.toString();
    }

    public static void main(String[] args){
        BitBoard b = new BitBoard();
        System.out.println(b);
        System.out.println(queryToString(FILE_H));
    }
}
