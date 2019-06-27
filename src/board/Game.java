package board;

public class Game {

    private static Game instance = null;

    public static Game getInstance() {
        if (instance == null)
            init();
        return instance;
    }

    private static void init() {
        instance = new Game();
    }

    private Board board;

}
