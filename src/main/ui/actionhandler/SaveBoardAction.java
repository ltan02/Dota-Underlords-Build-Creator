package ui.actionhandler;

import model.Board;
import persistence.JsonWriter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// Action Listener for Save Board Button
public class SaveBoardAction implements ActionListener {

    private static final String JSON_DIRECTORY = "./data/json/board.json";

    private Board board;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs a new save board action listener
    public SaveBoardAction(Board board) {
        this.board = board;
    }

    // EFFECTS: on activation, writes the board to board.json
    @Override
    public void actionPerformed(ActionEvent e) {
        jsonWriter = new JsonWriter(JSON_DIRECTORY);
        try {
            jsonWriter.open();
            jsonWriter.write(this.board);
            jsonWriter.close();
        } catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }
}
