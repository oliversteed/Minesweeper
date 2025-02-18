import javax.swing.*;
import java.awt.*;

public class MasterWindow {
    //Initialise objects for constructing the window.
    JFrame frame = new JFrame("Minesweeper");
    JLabel titleText = new JLabel();
    JPanel titlePanel = new JPanel();
    JLabel scoreText = new JLabel();
    JPanel score = new JPanel();

    private int scoreCount;

    MasterWindow(){

        scoreCount = 0; //Initially set to 0 upon launch.

        //Exiting the master window will terminate the program. This window will be the only constant window across all games.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocation(20,20);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        //Set title text parameters
        titleText.setFont(new Font("Arial", Font.BOLD, 40));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("Minesweeper");
        titleText.setOpaque(true);

        //Set score parameters
        score.setFont(new Font("Arial", Font.BOLD, 30));
        scoreText.setHorizontalAlignment(JLabel.CENTER);
        scoreText.setText("SCORE: " + scoreCount);
        scoreText.setOpaque(true);

        //Set window layout
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleText);

        score.setLayout(new BorderLayout());
        score.add(scoreText);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(score, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void increaseScore(){
        scoreCount ++;
        scoreText.setText("SCORE: " + scoreCount);
    }

    public void resetScore(){
        scoreCount = 0;
        scoreText.setText("SCORE: " + scoreCount);
    }

}
