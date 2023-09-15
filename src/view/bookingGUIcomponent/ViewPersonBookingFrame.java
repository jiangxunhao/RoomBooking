package view.bookingGUIcomponent;

import controller.BookingController;
import view.BookingGuiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;


public class ViewPersonBookingFrame extends JFrame implements ActionListener {
    static final int DEFAULT_FRAME_WIDTH = 400;
    static final int DEFAULT_FRAME_HEIGHT = 300;


    private BookingController controller;

    private BookingGuiView parent;

    private JPanel controlPanel;
    private JPanel inputPanel, buttonPanel;

    private JLabel emailLabel;

    private JTextField emailInput;

    private JButton searchButton, cancelButton;

    public ViewPersonBookingFrame(BookingGuiView parent, BookingController controller) {
        super("View person all bookings");
        this.parent = parent;
        this.controller = controller;

        controlPanel = new JPanel();

        inputPanel = new JPanel();
        buttonPanel = new JPanel();

        emailLabel = new JLabel("Email: ");

        JLabel[] labels = {emailLabel};

        emailInput = new JTextField(15);

        JComponent[] components = {emailInput};

        searchButton = new JButton("search");
        cancelButton = new JButton("cancel");

        BookingPane.setGroup(inputPanel, labels, components);

        addActionListenerForButtons(this);

        setButtonPanel();
        setControllerPanel();
        setViewPersonBookingFrame();

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

    public void setViewPersonBookingFrame() {
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
            String email = emailInput.getText();

            if ( email.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Empty value!!!", "Empty value", JOptionPane.ERROR_MESSAGE);
            } else {
                String message = "The searched email is " + email + ". ";
                int confirmation = JOptionPane.showConfirmDialog(this, message, "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    ArrayList<String[]> bookings = controller.getPersonBooking(email);

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
