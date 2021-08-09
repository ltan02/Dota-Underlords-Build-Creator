package ui.actionhandler;

import model.Board;
import ui.UnderlordBoardGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action Listener for View Alliance button
public class ViewAllianceAction implements ActionListener {

    private Board board;
    private UnderlordBoardGUI parentFrame;

    // EFFECTS: Constructs a new view alliance action listener
    public ViewAllianceAction(Board board, UnderlordBoardGUI parentFrame) {
        this.board = board;
        this.parentFrame = parentFrame;
    }

    // EFFECTS: on activation, shows the alliances of the board
    @Override
    public void actionPerformed(ActionEvent e) {
        String alliancesString = "Alliances: ";
        for (int i = 0; i < board.getAlliances().size() - 1; i++) {
            alliancesString += board.getAlliances().get(i) + ", ";
        }
        alliancesString += board.getAlliances().get(board.getAlliances().size() - 1);
        JOptionPane.showMessageDialog(parentFrame, alliancesString, "Alliance",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
