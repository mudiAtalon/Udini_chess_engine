package bitBoard;

public class MovesGenerator {
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


}
