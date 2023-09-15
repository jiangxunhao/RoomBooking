package view.bookingGUIcomponent;

import controller.BookingController;
import model.BookingModel;

import model.exception.NoBuildingExistsException;
import model.exception.RepeatedRoomNameException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddRoomFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 300;

    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel buildingNameLabel, roomNameLabel;
    private JComboBox<String> buildingNameInput;

    private JTextField roomNameInput;

    private JButton addButton, cancelButton;

    public AddRoomFrame(JFrame parent, BookingController controller) {
        super("Add a new room");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        buildingNameLabel = new JLabel("Building name: ");
        roomNameLabel = new JLabel("Room name: ");
        JLabel[] labels = {buildingNameLabel, roomNameLabel};

        buildingNameInput = new JComboBox<>(controller.getBuildingNames());
        roomNameInput = new JTextField(15);
        JComponent[] components = {buildingNameInput, roomNameInput};

        addButton = new JButton("add");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setAddRoomFrame();

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
        controlPanel.setBounds(75, 80, 300, 150);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setAddRoomFrame() {
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
                String buildingName = buildingNameInput.getItemAt(buildingNameInput.getSelectedIndex());
                String roomName = roomNameInput.getText();
                if ( buildingName == null || buildingName.isEmpty() || roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The building name is " + buildingName + " and the room name is " + roomName + ". ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.addRoom(buildingName, roomName);
                        JOptionPane.showMessageDialog(this, "Add successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }
            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }


        } catch (RepeatedRoomNameException repeatedRoomNameException) {
            JOptionPane.showMessageDialog(this, repeatedRoomNameException.getMessage(), "Repeated room name", JOptionPane.ERROR_MESSAGE);
        } catch (NoBuildingExistsException noBuildingExistsException) {
            JOptionPane.showMessageDialog(this, noBuildingExistsException.getMessage(), "No building exists", JOptionPane.ERROR_MESSAGE);
        }
    }

}