package view.bookingGUIcomponent;

import controller.BookingController;
import view.BookingGuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class ViewRoomFreeTimeFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 400;
    static final int DEFAULT_FRAME_HEIGHT = 300;


    private BookingController controller;

    private BookingGuiView parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel buildingNameLabel, roomNameLabel, dateLabel;

    private JTextField buildNameInput, roomNameInput, dateInput;

    private JButton searchButton, cancelButton;

    public ViewRoomFreeTimeFrame(BookingGuiView parent, BookingController controller) {
        super("View room all free periods");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        buildingNameLabel = new JLabel("Building name: ");
        roomNameLabel = new JLabel("Room name: ");
        dateLabel = new JLabel("Date: ");

        JLabel[] labels = {buildingNameLabel, roomNameLabel, dateLabel};

        buildNameInput = new JTextField(15);
        roomNameInput = new JTextField(15);
        dateInput = new JTextField(15);

        JComponent[] components = {buildNameInput, roomNameInput, dateInput};

        searchButton = new JButton("search");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setViewRoomFreeTimeFrame();

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

    public void setViewRoomFreeTimeFrame() {
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
            String dateText = dateInput.getText();

            if ( buildingName.isEmpty() || roomName.isEmpty() || dateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
            } else {

                String message = "The searched building is " + buildingName + ", the room is " + roomName + ", and the date is " + dateText + ". ";
                int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    try {
                        ArrayList<LocalTime[]> freePeriods = controller.getRoomFreePeriod(buildingName, roomName, dateText);
                        ArrayList<String[]> freePeriodsText = new ArrayList<>();
                        for (LocalTime[] localTimes : freePeriods) {
                            freePeriodsText.add(new String[]{localTimes[0].toString(), localTimes[1].toString()});
                        }

                        String[][] data = freePeriodsText.toArray(new String[freePeriodsText.size()][2]);
                        String[] column = new String[]{"StartTime", "EndTime"};
                        JTable table = new JTable(data, column);
                        JScrollPane sp = new JScrollPane(table);


                        parent.setShowPanel(sp);

                        SwingUtilities.updateComponentTreeUI(parent);

                        this.dispose();
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "the valid format is (yyyy-mm-dd) and (hh:mm)", "Wrong format", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

}
