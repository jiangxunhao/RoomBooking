package view.bookingGUIcomponent;

import controller.BookingController;

import model.exception.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeParseException;


public class AddBookingFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 400;


    //private BookingModel model;
    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel emailLabel, buildingNameLabel, roomNameLabel, dateLabel, startTimeLabel, endTimeLabel;
    private JTextField emailInput, roomNameInput, dateInput, startTimeInput, endTimeInput;
    private JComboBox<String> buildingNameInput;

    private JButton addButton, cancelButton;

    public AddBookingFrame(JFrame parent, BookingController controller) {
        super("Add a new booking");
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
        endTimeLabel = new JLabel("End time: ");
        JLabel[] labels = {emailLabel, buildingNameLabel, roomNameLabel, dateLabel, startTimeLabel, endTimeLabel};

        emailInput = new JTextField(15);
        buildingNameInput = new JComboBox<>(controller.getBuildingNames());
        roomNameInput = new JTextField(15);
        dateInput = new JTextField(15);
        startTimeInput = new JTextField(15);
        endTimeInput = new JTextField(15);
        JComponent[] components = {emailInput, buildingNameInput, roomNameInput, dateInput, startTimeInput, endTimeInput};
        BookingPane.setGroup(inputPanel, labels, components);

        addButton = new JButton("add");
        cancelButton = new JButton("cancel");


        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setAddBookingFrame();

    }

    public void setButtonPanel() {
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
    }

    public void addActionListenerForButtons(ActionListener al) {
        addButton.addActionListener(al);
        cancelButton.addActionListener(al);
    }

    public void setControllerPanel() {
        controlPanel.setBounds(90, 50, 300, 300);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setAddBookingFrame() {
        this.add(controlPanel);

        this.setLayout(null);
        this.setLocationRelativeTo(parent);
        this.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {
                String email = emailInput.getText();
                String buildingName = buildingNameInput.getItemAt(buildingNameInput.getSelectedIndex());
                String roomName = roomNameInput.getText();
                String date = dateInput.getText();
                String startTime = startTimeInput.getText();
                String endTime = endTimeInput.getText();
                if ( email.isEmpty() || buildingName.isEmpty() || roomName.isEmpty()
                        || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The email is " + email + ", building name is " + buildingName + ", room name is " + roomName
                            + ", date is " + date + ", start time is " + startTime + ", and end time is " + endTime + ". ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.addBooking(email, buildingName, roomName, date, startTime, endTime);
                        JOptionPane.showMessageDialog(this, "Add successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }
            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }

        } catch (NoBuildingExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No building exists", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidTimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid time", JOptionPane.ERROR_MESSAGE);
        } catch (NoRoomExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No room exists", JOptionPane.ERROR_MESSAGE);
        } catch (RepeatedBookingException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Repeated bookings", JOptionPane.ERROR_MESSAGE);
        } catch (NoPersonExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No person exists", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "the valid format is (yyyy-mm-dd) and (hh:mm)", "Wrong format", JOptionPane.ERROR_MESSAGE);
        }
    }

}