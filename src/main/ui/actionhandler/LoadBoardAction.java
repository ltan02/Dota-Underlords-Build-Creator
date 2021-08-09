package ui.actionhandler;

import model.Board;
import persistence.JsonReader;
import ui.UnderlordBoardGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Action Listener for Load Board Button
public class LoadBoardAction implements ActionListener {

    private static final String JSON_STORE = "./data/json/board.json";

    private JFrame parentFrame;
    private JsonReader jsonReader;

    // EFFECTS: constructs a load board action listener
    public LoadBoardAction(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // MODIFIES: this
    // EFFECTS: on activation, loads the board from board.json
    @Override
    public void actionPerformed(ActionEvent e) {
        this.parentFrame.setVisible(false);
        this.jsonReader = new JsonReader(JSON_STORE);
        Board savedBoard = null;
        try {
            savedBoard = jsonReader.read();
        } catch (IOException error) {
            System.err.println("Error occured when reading json file");
        }
        new UnderlordBoardGUI(savedBoard, this.parentFrame);
    }
}
