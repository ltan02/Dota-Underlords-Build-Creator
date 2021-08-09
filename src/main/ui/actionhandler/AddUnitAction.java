package ui.actionhandler;

import model.Board;
import ui.UnderlordBoardGUI;
import ui.UnitsGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action Listener for Add Unit Button
public class AddUnitAction implements ActionListener {

    private Board board;
    private UnderlordBoardGUI underlordGui;

    // EFFECTS: constructs a new add unit action listener
    public AddUnitAction(Board board, UnderlordBoardGUI underlordGui) {
        this.board = board;
        this.underlordGui = underlordGui;
    }

    // MODIFIES: this
    // EFFECTS: on activation, adds a chosen unit
    @Override
    public void actionPerformed(ActionEvent e) {
        new UnitsGUI(board, underlordGui);
    }
}
