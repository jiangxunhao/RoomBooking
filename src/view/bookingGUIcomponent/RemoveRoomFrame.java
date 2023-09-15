package view.bookingGUIcomponent;

import controller.BookingController;
import model.BookingModel;

import model.exception.NoBuildingExistsException;
import model.exception.NoRoomExistsException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RemoveRoomFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 300;

    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel buildingNameLabel, roomNameLabel;
    private JComboBox<String> buildingNameInput;

    private JTextField roomNameInput;

    private JButton removeButton, cancelButton;

    public RemoveRoomFrame(JFrame parent, BookingController controller) {
        super("Remove a room");
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

        removeButton = new JButton("remove");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setRemoveRoomFrame();

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
        controlPanel.setBounds(75, 80, 300, 150);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setRemoveRoomFrame() {
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
                String buildingName = buildingNameInput.getItemAt(buildingNameInput.getSelectedIndex());
                String roomName = roomNameInput.getText();
                if ( buildingName == null || buildingName.isEmpty() || roomName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The building name is " + buildingName + " and the room name is " + roomName + ". ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.removeRoom(buildingName, roomName);
                        JOptionPane.showMessageDialog(this, "remove successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            }
            this.dispose();
        } catch (NoBuildingExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No building exists", JOptionPane.ERROR_MESSAGE);
        } catch (NoRoomExistsException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No room exists", JOptionPane.ERROR_MESSAGE);
        }
    }

}