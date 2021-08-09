package ui.actionhandler;

import model.Board;
import ui.UnderlordBoardGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action Listener for New Board button
public class NewBoardAction implements ActionListener {

    private JFrame parentFrame;

    // EFFECTS: constructs a new board action listener
    public NewBoardAction(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // MODIFIES: this
    // EFFECTS: on activation, creates a new empty board
    @Override
    public void actionPerformed(ActionEvent e) {
        this.parentFrame.setVisible(false);
        String boardName = getBoardName();
        Board b = new Board(boardName);
        new UnderlordBoardGUI(b, this.parentFrame);
    }

    // EFFECTS: returns the name of the board that user gave
    private String getBoardName() {
        String boardName = JOptionPane.showInputDialog("What is the name of the board?");
        while (boardName.equals("")) {
            boardName = JOptionPane.showInputDialog("Please enter a valid board name");
        }
        return boardName;
    }
}
