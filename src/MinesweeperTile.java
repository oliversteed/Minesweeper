import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Class for Minesweeper tile inherits from JButton.
public class MinesweeperTile extends JButton {
    //Sets the X and Y coords of the tile, relating to its position in the main game boards 2D array. Final as this cannot change once instantiated.
    private final int row;
    private final int column;
    private int surroundingMines;

    //Sets booleans for checking various statuses of the tile
    private boolean isMine;
    private boolean isMarked;
    private boolean isRevealed;
    private boolean adjacentMine = false;

    Minesweeper currentBoard;

    MinesweeperTile(int row, int column, Minesweeper currentBoard) {
        this.row = row;
        this.column = column;
        this.currentBoard = currentBoard;

        //Adds functionality to check for mouse clicks once the object is constructed.
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                //Don't allow interaction with a tile once it has been clicked and revealed.
                if(isRevealed) return;

                //If right-clicked, mark the tile instead of revealing and disabling.
                if(e.getButton() == MouseEvent.BUTTON3){
                    //Unmark if already marked.
                    if(isMarked){
                        setText("");
                        isMarked = false;
                        return;
                    }
                    setText("-");
                    isMarked = true;
                    return;
                }

                //Disabled button once clicked. Set mines to X and safe tiles to O. This is temporary. TODO: Tiles should display number of adjacent mines, not "O"
                revealTile(true);
            }
        });
    }

    /*
    Makes the tile no longer clickable and sets text to correspond to whether it was a mine or not.
    If the tile was a mine, this will call gameOver() on the active game board.
     */
    public void revealTile(boolean cascade){

        isRevealed = true;
        setEnabled(false);

        if(isMine) {
            setText("X");
            currentBoard.gameOver();
        }
        else{
            //Control when the revealing tiles should cascade.
            if(cascade) currentBoard.scanAdjacent(this, true);
        }
    }

    public void increaseSurroundingMines(){
        surroundingMines++;
    }

    public int getSurroundingMines(){
        return surroundingMines;
    }

    //setter for isMine
    public void setMine(){
        isMine = true;
    }

    //getter for isMine
    public boolean isMine(){
        return isMine;
    }

    //getter for row index
    public int getRow(){
        return row;
    }

    //getter for column index
    public int getColumn(){
        return column;
    }

    //getter for isRevealed
    public boolean getRevealed(){
        return isRevealed;
    }

    //Getter for adjacentMine
    public boolean isAdjacentMine(){
        return adjacentMine;
    }

    //setter for adjacentMine
    public void setAdjacentMine(){
        adjacentMine = true;
    }

    //Allows other objects to set the text of this object.
    public void setTileText(String tileText){
        setText(tileText);
    }

}
