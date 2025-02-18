import com.sun.net.httpserver.Authenticator;

//Static library to create key game objects.
public class GameMaster {

    //Creates the master window which remains constant throughout the game and returns the created master window
    //This allows it to be passed to the created minesweeper board instances.
    public static MasterWindow initialiseGame(){
        return new MasterWindow();
    }

    //Creates a minesweeper board. Takes the master window as a parameter.
    public static void createGame(MasterWindow masterWindow){
        Minesweeper minesweeper = new Minesweeper(masterWindow);
    }

    //Creates a retry window. Used for game overs.
    public static void createRetryWindow(MasterWindow masterWindow, Minesweeper minesweeper, String title){
        RetryWindow retryWindow = new RetryWindow(masterWindow,minesweeper,title);
    }
}
