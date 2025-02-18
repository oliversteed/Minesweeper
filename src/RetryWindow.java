import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class RetryWindow {

    //Store the active game
    Minesweeper currentBoard;

    private String title = "default";

    //Initialise objects for constructing the window.
    JFrame frame = new JFrame(title);
    JLabel titleText = new JLabel();
    JPanel titlePanel = new JPanel();
    JPanel options = new JPanel();

    RetryWindow(Minesweeper currentBoard, String title){

        this.currentBoard = currentBoard;
        this.title = title;

        //Set window parameters
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Set title text parameters
        titleText.setFont(new Font("Arial", Font.ITALIC, 30));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText(title);
        titleText.setOpaque(true);

        //Set window layout
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleText);
        frame.add(titlePanel, BorderLayout.NORTH);

        //Set layout of playing board and add to the frame
        options.setLayout(new GridLayout(2, 1));
        frame.add(options);

        JButton retryButton = new JButton();

        //When button is clicked, close the current game and open a new one.
        retryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                currentBoard.closeGame();
                GameMaster.createGame();
                closeWindow();
            }
        });

        retryButton.setText("Retry");

        options.add(retryButton);

        frame.setVisible(true);
    }

    public void closeWindow(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
