package view.bookingGUIcomponent;

import controller.BookingController;
import model.BookingModel;

import model.exception.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;


public class RemoveBookingFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 400;

    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel emailLabel, buildingNameLabel, roomNameLabel, dateLabel, startTimeLabel;
    private JTextField emailInput, roomNameInput, dateInput, startTimeInput;
    private JComboBox<String> buildingNameInput;

    private JButton removeButton, cancelButton;

    public RemoveBookingFrame(JFrame parent, BookingController controller) {
        super("Remove a booking");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        emailLabel = new JLabel("Email: ");
        buildingNameLabel = new JLabel("Building name: ");
        roomNameLabel = new JLabel("Room name: ");
        dateLabel = new JLabel("Date: ");
        startTimeLabel = new JLabel("Start time: ");
        JLabel[] labels = {emailLabel, buildingNameLabel, roomNameLabel, dateLabel, startTimeLabel};

        emailInput = new JTextField(15);
        buildingNameInput = new JComboBox<>(controller.getBuildingNames());
        roomNameInput = new JTextField(15);
        dateInput = new JTextField(15);
        startTimeInput = new JTextField(15);
        JComponent[] components = {emailInput, buildingNameInput, roomNameInput, dateInput, startTimeInput};

        removeButton = new JButton("remove");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setRemoveBookingFrame();

    }

    public void setButtonPanel() {
        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);
    }

    public void addActionListenerForButtons(ActionListener al) {
        removeButton.addActionListener(al);
        cancelButton.addActionListener(al);
    }

    public void setControllerPanel() {
        controlPanel.setBounds(90, 50, 300, 300);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setRemoveBookingFrame() {
        this.add(controlPanel);

        this.setLayout(null);
        this.setLocationRelativeTo(parent);
        this.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == removeButton) {
                String email = emailInput.getText();
                String buildingName = buildingNameInput.getItemAt(buildingNameInput.getSelectedIndex());
                String roomName = roomNameInput.getText();
                String date = dateInput.getText();
                String startTime = startTimeInput.getText();

                if ( email.isEmpty() || buildingName.isEmpty() || roomName.isEmpty()
                        || date.isEmpty() || startTime.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The email is " + email + ", building name is " + buildingName + ", room name is " + roomName
                            + ", date is " + date + ", and start time is " + startTime + ". ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.removeBooking(email, buildingName, roomName, date, startTime);
                        JOptionPane.showMessageDialog(this, "remove successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }
            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }

        } catch (NoBookingExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No booking exists", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "the valid format is (yyyy-mm-dd) and (hh:mm)", "Wrong format", JOptionPane.ERROR_MESSAGE);
        }
    }

}