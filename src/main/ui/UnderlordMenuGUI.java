package ui;

import ui.actionhandler.LoadBoardAction;
import ui.actionhandler.NewBoardAction;

import javax.swing.*;
import java.awt.*;

// Graphical User Interface of the Main Menu
public class UnderlordMenuGUI extends JFrame {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 800;
    private static final String JSON_STORE = "./data/json/board.json";

    // EFFECTS: Constructs a new Main Menu
    public UnderlordMenuGUI() {
        super("Main Menu");
        JPanel panel = createPanel();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    // EFFECTS: creates the panel for main menu
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 300, 100));
        JLabel label = new JLabel("<html><H1>Dota Underlord Build Creator</H1></html>");
        panel.setLayout(new GridLayout(0, 1, 0, 50));
        panel.add(label);
        label.setHorizontalAlignment(JLabel.CENTER);
        JButton newBoard = new JButton("Create a New Board");
        JButton loadBoard = new JButton("Load Board");
        newBoard.addActionListener(new NewBoardAction(this));
        loadBoard.addActionListener(new LoadBoardAction(this));
        panel.add(newBoard);
        panel.add(loadBoard);
        return panel;
    }

}
