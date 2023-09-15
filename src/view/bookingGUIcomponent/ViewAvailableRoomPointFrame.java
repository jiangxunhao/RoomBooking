package view.bookingGUIcomponent;

import controller.BookingController;
import view.BookingGuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class ViewAvailableRoomPointFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 400;
    static final int DEFAULT_FRAME_HEIGHT = 300;


    private BookingController controller;

    private BookingGuiView parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel dateLabel, timeLabel;

    private JTextField dateInput, timeInput;

    private JButton searchButton, cancelButton;

    public ViewAvailableRoomPointFrame(BookingGuiView parent, BookingController controller) {
        super("View all available rooms");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        dateLabel = new JLabel("Date: ");
        timeLabel = new JLabel("Time: ");
        JLabel[] labels = {dateLabel, timeLabel};

        dateInput = new JTextField(15);
        timeInput = new JTextField(15);
        JComponent[] components = {dateInput, timeInput};

        searchButton = new JButton("search");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setViewAvailablePointFrame();

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
        controlPanel.setBounds(75, 80, 250, 150);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setViewAvailablePointFrame() {
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
                String dateText = dateInput.getText();
                String timeText = timeInput.getText();
                if (dateText.isEmpty() || timeText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String message = "The searched date is " + dateText + " and the time is " + timeText + ". ";
                        int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
                            HashMap<String, ArrayList<String>> rooms = controller.getAvailableRoom(dateText, timeText);
                            ArrayList<String[]> ArrayData = new ArrayList<>();
                            for (Entry<String, ArrayList<String>> entry : rooms.entrySet()) {
                                String buildingName = entry.getKey();
                                ArrayList<String> ar = entry.getValue();
                                for (int i = 0; i < ar.size(); i++) {
                                    String[] dataItem = new String[]{buildingName, ar.get(i)};
                                    ArrayData.add(dataItem);
                                }
                            }
                            String[][] data = ArrayData.toArray(new String[ArrayData.size()][2]);
                            String[] column = new String[]{"Building name", "Room name"};
                            JTable availableRooms = new JTable(data, column);
                            JScrollPane sp = new JScrollPane(availableRooms);

                            parent.setShowPanel(sp);

                            SwingUtilities.updateComponentTreeUI(parent);

                            this.dispose();
                        }
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "the valid format is (yyyy-mm-dd) and (hh:mm)", "Wrong format", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }

    }

}
