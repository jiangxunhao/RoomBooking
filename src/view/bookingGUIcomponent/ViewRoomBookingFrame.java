package view.bookingGUIcomponent;

import controller.BookingController;
import view.BookingGuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;


public class ViewRoomBookingFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 400;
    static final int DEFAULT_FRAME_HEIGHT = 300;


    private BookingController controller;

    private BookingGuiView parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel buildingNameLabel, roomNameLabel;

    private JTextField buildNameInput, roomNameInput;

    private JButton searchButton, cancelButton;

    public ViewRoomBookingFrame(BookingGuiView parent, BookingController controller) {
        super("View room all bookings");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        buildingNameLabel = new JLabel("Building name: ");
        roomNameLabel = new JLabel("Room name: ");

        JLabel[] labels = {buildingNameLabel, roomNameLabel};

        buildNameInput = new JTextField(15);
        roomNameInput = new JTextField(15);

        JComponent[] components = {buildNameInput, roomNameInput};

        searchButton = new JButton("search");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setViewRoomBookingFrame();

    }

    public void setButtonPanel() {
        buttonPanel.add(searchButton);
        buttonPanel.add(cancelButton);
    }

    public void addActionListenerForButtons(ActionListener al) {
        searchButton.addActionListener(al);
        cancelButton.addActionListener(al);
    }

    public void setControllerPanel() {
        controlPanel.setBounds(75, 80, 300, 150);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setViewRoomBookingFrame() {
        this.add(controlPanel);
        this.setFocusable(true);

        this.setLayout(null);
        this.setLocationRelativeTo(parent);
        this.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String buildingName = buildNameInput.getText();
            String roomName = roomNameInput.getText();

            if ( buildingName.isEmpty() || roomName.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
            } else {
                String message = "The searched building is " + buildingName + ",and the room is " + roomName + ". ";
                int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    ArrayList<String[]> bookings = controller.getRoomBooking(buildingName, roomName);
                    String[][] data = bookings.toArray(new String[bookings.size()][6]);
                    String[] column = new String[] {"Email", "Building", "Room", "Date", "StartTime", "EndTime"};
                    JTable table = new JTable(data, column);
                    JScrollPane sp = new JScrollPane(table);

                    parent.setShowPanel(sp);

                    SwingUtilities.updateComponentTreeUI(parent);

                    this.dispose();
                }
            }
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

}