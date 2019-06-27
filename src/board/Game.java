package board;

public class Game {

    public static Board.Result playGame(Player white, Player black) {
        Game game = new Game(white, black);
        return game.play();
    }

    private Board board;
    private Player white, black;

    private Game(Player white, Player black){
        this.white = white;
        this.black = black;
        this.board = new Board();
    }

    private Board.Result play(){

        for(Player current = white, next = black, swaper;
                !board.isGameOver();
                swaper = current, current = next, next = swaper){
            Move move = current.getChosenMove(board);
            board.move(move);
            if(board.isGameOver()) {
                System.out.println(board);
                return board.getResult();
            }
        }
        throw new RuntimeException("oh, the places you'll go!");
    }

    public static void main(String[] args) {
        Player white = new FirstMovePlayer(),
                black = new FirstMovePlayer();
        Board.Result result = playGame(white ,black);
        System.out.println(result);
    }

}
