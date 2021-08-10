package ui.actionhandler;

import exceptions.InvalidColumnException;
import exceptions.InvalidRowException;
import model.Board;
import model.Hero;
import model.Item;
import model.Placeable;
import ui.UnderlordBoardGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action Listener for Remove Unit Button
public class RemoveUnitAction implements ActionListener {

    private Board board;
    private UnderlordBoardGUI underlordGui;

    // EFFECTS: constructs a new remove unit action listener
    public RemoveUnitAction(Board board, UnderlordBoardGUI underlordGui) {
        this.board = board;
        this.underlordGui = underlordGui;
    }

    // MODIFIES: this
    // EFFECTS: on activation, removes the unit that was selected
    @Override
    public void actionPerformed(ActionEvent e) {
        Placeable unitSelected = underlordGui.getSelectedUnit();
        if (unitSelected != null) {
            if (unitSelected instanceof Hero) {
                Hero hero = (Hero) unitSelected;
                try {
                    board.removeHero(hero.getRow(), hero.getColumn());
                } catch (InvalidColumnException | InvalidRowException ex) {
                    ex.printStackTrace();
                }
            } else {
                Item item = (Item) unitSelected;
                try {
                    board.removeItem(item.getRow(), item.getColumn());
                } catch (InvalidColumnException | InvalidRowException ex) {
                    ex.printStackTrace();
                }
            }
        }
        underlordGui.setSelectedUnit(null);
        underlordGui.setUnitSelected(false);
        underlordGui.drawBoard();
    }
}
