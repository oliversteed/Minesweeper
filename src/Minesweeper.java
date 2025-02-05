import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class Minesweeper {

    //Class for Minesweeper tile inherits from JButton
    private class MinesweeperTile extends JButton {
        int rows;
        int columns;
        boolean isMine;

        MinesweeperTile(int rows, int columns){
            this.rows = rows;
            this.columns = columns;
        }

        public boolean isMine(){
            return isMine;
        }
    }

    //Class parameters. Need to create an options pane to allow users to specify these.
    int tileSize = 80;
    int numberOfRows = 6;
    int numberOfColumns = numberOfRows;
    int windowWidth = numberOfColumns * tileSize;
    int windowHeight = numberOfRows * tileSize;

    //Initialise objects for constructing the window.
    JFrame frame = new JFrame("Minesweeper");
    JLabel titleText = new JLabel();
    JPanel titlePanel = new JPanel();
    JPanel playingBoard = new JPanel();

    //Stores the tiles in a 2D array
    MinesweeperTile[][] playingBoardArray = new MinesweeperTile[numberOfRows][numberOfColumns];

    Minesweeper(){
        //Set window parameters
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Set title text parameters
        titleText.setFont(new Font("Arial", Font.ITALIC, 30));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("MINESWEEPER");
        titleText.setOpaque(true);

        //Set window layout
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleText);
        frame.add(titlePanel, BorderLayout.NORTH);

        //Set layout of playing board and add to the frame
        playingBoard.setLayout(new GridLayout(numberOfRows, numberOfColumns));
        frame.add(playingBoard);

        //Iterate through the grid and add the MinesweeperTile objects.
        for (int row = 0; row < numberOfRows; row++){
            for (int col = 0; col < numberOfColumns; col++){
                MinesweeperTile tile = new MinesweeperTile(row, col);
                playingBoardArray[row][col] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial", Font.PLAIN, 45));

                playingBoard.add(tile);
            }
        }

        frame.setVisible(true);
    }
}
