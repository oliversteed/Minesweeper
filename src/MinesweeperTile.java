import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Class for Minesweeper tile inherits from JButton.
public class MinesweeperTile extends JButton {
    int rows;
    int columns;
    boolean isMine;
    boolean isMarked;
    boolean isRevealed;

    Minesweeper currentBoard;

    MinesweeperTile(int rows, int columns, Minesweeper currentBoard) {
        this.rows = rows;
        this.columns = columns;
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
                revealTile();
            }
        });
    }

    /*
    Makes the tile no longer clickable and sets text to correspond to whether it was a mine or not.
    If the tile was a mine, this will call gameOver() on the active game board.
     */
    public void revealTile(){

        setText("O");
        isRevealed = true;
        setEnabled(false);

        if(isMine) {
            setText("X");
            currentBoard.gameOver();
        }
    }

    public void setMine(){
        isMine = true;
    }

    public boolean isMine(){
        return isMine;
    }

}
