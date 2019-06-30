package board;

public class Square {

    public final int rank, file;

    public Square(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

    public Square(Square other){
        this.rank = other.rank;
        this.file = other.file;
    }

    public Square copy(){
        return new Square(this);
    }

    public static Square copy(Square square){
        if(square == null) return null;
        return square.copy();
    }

    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(!(o instanceof Square))
            return false;
        Square other = (Square)o;
        return rank == other.rank &&
                file == other.file;
    }

    @Override
    public String toString(){
        return "[" + rank + ", " + file + "]";
    }
}
