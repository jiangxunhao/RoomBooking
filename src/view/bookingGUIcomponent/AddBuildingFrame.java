package view.bookingGUIcomponent;

import controller.BookingController;
import model.BookingModel;
import model.exception.RepeatedBuildingNameException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddBuildingFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 350;


    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel nameLabel, addressLine1Label, addressLine2Label;

    private JTextField nameInput, addressLine1Input, addressLine2Input;

    private JButton addButton, cancelButton;

    public AddBuildingFrame(JFrame parent, BookingController controller) {
        super("Add a new building");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        nameLabel = new JLabel("Name: ");
        addressLine1Label = new JLabel("Address(Line 1): ");
        addressLine2Label = new JLabel("Address(Line 2): ");
        JLabel[] labels = {nameLabel, addressLine1Label, addressLine2Label};

        nameInput = new JTextField(15);
        addressLine1Input = new JTextField(20);
        addressLine2Input = new JTextField(20);
        JComponent[] components = {nameInput, addressLine1Input, addressLine2Input};
        BookingPane.setGroup(inputPanel, labels, components);

        addButton = new JButton("add");
        cancelButton = new JButton("cancel");


        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setAddBuildingFrame();

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
        controlPanel.setBounds(75, 80, 400, 200);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setAddBuildingFrame() {
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
                String name = nameInput.getText();
                String address = addressLine1Input.getText() + addressLine2Input.getText();
                if ( name.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The added building name is " + name + " and the address is `" + address + "`. ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.addBuilding(name, address);
                        JOptionPane.showMessageDialog(this, "Add successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();

                    }
                }

            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }

        } catch (RepeatedBuildingNameException repeatedBuildingNameException) {
            JOptionPane.showMessageDialog(this, repeatedBuildingNameException.getMessage(), "Repeated building name", JOptionPane.ERROR_MESSAGE);
        }
    }

}
