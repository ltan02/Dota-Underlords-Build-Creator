package ui;

import exceptions.InvalidTileException;
import exceptions.TileOccupiedException;
import model.Board;
import model.Placeable;
import ui.actionhandler.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Graphical User Interface of Underlord Board
public class UnderlordBoardGUI extends JDialog implements MouseListener, MouseMotionListener, WindowListener {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int MENU_BAR_HEIGHT = 75;

    // Board Information
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = FRAME_HEIGHT - MENU_BAR_HEIGHT;
    private static final Color TILE_COLOR = new Color(0, 0, 0);
    private static final Color TILE_COLOR_ALT = new Color(255, 255, 255);

    private Board board;
    private JFrame parentFrame;
    private JPanel underlordBoard;
    private boolean unitSelected;
    private Placeable selectedUnit;
    private JMenuBar menuBar;

    // EFFECTS: constructs a new Underlord Board
    public UnderlordBoardGUI(Board b, JFrame parentFrame) {
        this.board = b;
        this.parentFrame = parentFrame;
        setupFrame();
        createMenuBar();
        createBoard();
        this.addWindowListener(this);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: creates a menu bar and adds save, add unit, remove unit, and view alliances buttons
    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, BOARD_WIDTH, MENU_BAR_HEIGHT);
        getContentPane().add(menuBar);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveBoardAction(this.board));
        JButton addUnitButton = new JButton("Add Unit");
        addUnitButton.addActionListener(new AddUnitAction(this.board, this));
        JButton removeUnitButton = new JButton("Remove Unit");
        removeUnitButton.addActionListener(new RemoveUnitAction(this.board, this));
        JButton viewAlliancesButton = new JButton("View Alliances");
        viewAlliancesButton.addActionListener(new ViewAllianceAction(this.board, this));
        menuBar.add(saveButton);
        menuBar.add(addUnitButton);
        menuBar.add(removeUnitButton);
        menuBar.add(viewAlliancesButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a new board
    private void createBoard() {
        JLayeredPane layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        this.underlordBoard = new JPanel();
        this.underlordBoard.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        layeredPane.add(this.underlordBoard, JLayeredPane.DEFAULT_LAYER);
        this.underlordBoard.setLayout(new GridLayout(Board.MAX_ROWS, Board.MAX_COLUMNS));
        this.underlordBoard.setBounds(0, MENU_BAR_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT);
        addTiles();
        addUnits();
    }

    // EFFECTS: returns the unit that is selected
    public Placeable getSelectedUnit() {
        return this.selectedUnit;
    }

    // EFFECTS: returns whether unit is selected
    public boolean getUnitSelected() {
        return this.unitSelected;
    }

    // MODIFIES: this
    // EFFECTS: sets the selectedUnit to given value
    public void setSelectedUnit(Placeable unit) {
        this.selectedUnit = unit;
    }

    // MODIFIES: this
    // EFFECTS: sets the unitSelected to given value
    public void setUnitSelected(boolean selected) {
        this.unitSelected = selected;
    }

    // MODIFIES: this
    // EFFECTS: adds the tiles to the board in alternating colors
    private void addTiles() {
        int counter = 0;
        for (int i = 0; i < Board.MAX_ROWS; i++) {
            for (int j = 0; j < Board.MAX_COLUMNS; j++) {
                JPanel tile = new JPanel(new BorderLayout());
                tile.setBackground((counter % 2 == 0) ? TILE_COLOR : TILE_COLOR_ALT);
                this.underlordBoard.add(tile);
                counter++;
            }
            counter++;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the units to the board
    private void addUnits() {
        Placeable[][] tiles = this.board.getTiles();
        int counter = 0;
        for (Placeable[] row : tiles) {
            for (Placeable unit : row) {
                if (unit != null) {
                    JLabel unitIcon = new JLabel(new ImageIcon("./data/portraits/" + unit.getName() + ".png"));
                    JPanel tile = (JPanel) this.underlordBoard.getComponent(counter);
                    tile.add(unitIcon);
                }
                counter++;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: setups the frame of the underlord board
    private void setupFrame() {
        this.parentFrame.setVisible(false);
        this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: re-renders board based on user input
    public void drawBoard() {
        this.underlordBoard.removeAll();
        this.underlordBoard.repaint();
        addTiles();
        addUnits();
        this.underlordBoard.revalidate();
    }

    // DOES NOTHING
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // MODIFIES: this
    // EFFECTS: moves unit to selected tile
    @Override
    public void mousePressed(MouseEvent e) {
        int column = e.getX() / (BOARD_WIDTH / Board.MAX_COLUMNS);
        int row = (e.getY() - MENU_BAR_HEIGHT) / (BOARD_HEIGHT / Board.MAX_ROWS);
        if (this.unitSelected) {
            try {
                this.board.moveUnit(this.selectedUnit, row, column);
            } catch (InvalidTileException | TileOccupiedException ex) {
                ex.printStackTrace();
            }
            this.unitSelected = false;
            this.selectedUnit = null;
        } else if (this.board.getTiles()[row][column] == null) {
            this.unitSelected = false;
            this.selectedUnit = null;
        } else {
            this.unitSelected = true;
            this.selectedUnit = this.board.getTiles()[row][column];
        }
        drawBoard();
    }

    // DOES NOTHING
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    // DOES NOTHING
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    // DOES NOTHING
    @Override
    public void mouseExited(MouseEvent e) {

    }

    // DOES NOTHING
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    // DOES NOTHING
    @Override
    public void mouseMoved(MouseEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowClosing(WindowEvent e) {
        new SaveBoardGUI(this.board);
    }

    // DOES NOTHING
    @Override
    public void windowClosed(WindowEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // DOES NOTHING
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
