import board.Board;
import players.CommandLinePlayer;
import players.FirstMovePlayer;
import board.Move;
import players.Player;

public class Game {

    public static Board.Result playGame(Player white, Player black) {
        Game game = new Game(white, black);
        return game.play();
    }

    private Board board;
    private Player white, black;

    private Game(Player white, Player black) {
        this.white = white;
        this.black = black;
        this.board = new Board();
    }

    private Board.Result play() {
        System.out.println(board);

        for (Player current = white, next = black, swaper;
             !board.isGameOver();
             swaper = current, current = next, next = swaper) {

            Move move = current.getChosenMove(board);
            board.move(move);
            System.out.println(board);
            if (board.isGameOver()) {
                return board.getResult();
            }
        }
        throw new RuntimeException("oh, the places you'll go!");
    }

    public static void main(String[] args) {
        Player white = new CommandLinePlayer(),
                black = new CommandLinePlayer();
        Board.Result result = playGame(white, black);
        System.out.println(result);
    }

}
