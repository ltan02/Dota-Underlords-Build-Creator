package ui;

import model.Board;
import persistence.JsonWriter;
import ui.actionhandler.SaveBoardAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

// Graphical User Interface for saving board before closing
public class SaveBoardGUI extends JFrame implements ActionListener {

    private Board board;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs a new Save Board GUI
    public SaveBoardGUI(Board board) {
        this.board = board;
        JLabel label = new JLabel("Do you wish to save your board before quitting?");
        label.setBounds(25, 50, 350, 50);

        JButton yesButton = new JButton("Yes");
        yesButton.setBounds(100, 150, 50, 25);
        yesButton.addActionListener(this);
        yesButton.setActionCommand("yes");

        JButton noButton = new JButton("No");
        noButton.setBounds(250, 150, 50, 25);
        noButton.addActionListener(this);
        noButton.setActionCommand("no");

        this.add(label);
        this.add(yesButton);
        this.add(noButton);
        this.setSize(400, 200);
        this.setLayout(null);
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: on activation, saves the board to board.json
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("yes")) {
            jsonWriter = new JsonWriter("./data/json/board.json");
            try {
                jsonWriter.open();
                jsonWriter.write(this.board);
                jsonWriter.close();
            } catch (FileNotFoundException error) {
                error.printStackTrace();
            }
            this.setVisible(false);
        } else if (e.getActionCommand().equals("no")) {
            this.setVisible(false);
        }
    }
}
