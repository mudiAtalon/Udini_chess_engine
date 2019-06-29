import board.Board;
import players.CommandLinePlayer;
import board.Ply;
import players.MoveListPlayer;
import players.Player;

import java.util.ArrayList;
import java.util.List;

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

            Ply ply = current.getChosenMove(board);
            board.move(ply);
            System.out.println(board);
            if (board.isGameOver()) {
                return board.getResult();
            }
        }
        throw new RuntimeException("oh, the places you'll go!");
    }

    public static void main(String[] args) {
//        List<Ply> whiteMoves = new ArrayList<>(), blackMoves = new ArrayList<>();
//        for(int iter = 0; iter<100; iter++){
//            whiteMoves.add(new Ply(0, 6, 2, 5));
//            blackMoves.add(new Ply(7, 1, 5, 2));
//            whiteMoves.add(new Ply(2, 5, 0, 6));
//            blackMoves.add(new Ply(5, 2, 7, 1));
//        }
        Player white = new CommandLinePlayer(),
                black = new CommandLinePlayer();
        Board.Result result = playGame(white, black);
        System.out.println(result);
    }

}
