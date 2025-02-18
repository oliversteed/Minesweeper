import com.sun.net.httpserver.Authenticator;

public class GameMaster {

    public static void createGame(){
        Minesweeper minesweeper = new Minesweeper();
    }

    public static void createRetryWindow(Minesweeper minesweeper, String title){
        RetryWindow retryWindow = new RetryWindow(minesweeper, title);
    }
}
