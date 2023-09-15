package view.bookingGUIcomponent;

import controller.BookingController;
import model.exception.RepeatedEmailException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddPersonFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 400;
    static final int DEFAULT_FRAME_HEIGHT = 300;


    private BookingController controller;

    private JFrame parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel emailLabel, nameLabel;

    private JTextField emailInput, nameInput;

    private JButton addButton, cancelButton;

    public AddPersonFrame(JFrame parent, BookingController controller) {
        super("Add a new person");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        emailLabel = new JLabel("Email: ");
        nameLabel = new JLabel("Name: ");
        JLabel[] labels = {emailLabel, nameLabel};

        emailInput = new JTextField(15);
        nameInput = new JTextField(15);
        JComponent[] components = {emailInput, nameInput};

        addButton = new JButton("add");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setAddPersonFrame();

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
        controlPanel.setBounds(75, 80, 250, 150);
        controlPanel.add(inputPanel);
        controlPanel.add(buttonPanel);
    }

    public void setAddPersonFrame() {
        this.add(controlPanel);
        this.setFocusable(true);

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
                String name = nameInput.getText();
                if ( email.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
                } else {
                    String message = "The added email is " + email + " and the name is " + name + ". ";
                    int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        controller.addPerson(email, name);
                        JOptionPane.showMessageDialog(this, "Add successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }
            } else if (e.getSource() == cancelButton) {
                this.dispose();
            }

        } catch (RepeatedEmailException repeatedEmailException) {
            JOptionPane.showMessageDialog(this, repeatedEmailException.getMessage(), "Repeated Email", JOptionPane.ERROR_MESSAGE);
        }
    }

}
