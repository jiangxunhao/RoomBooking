package view;

import controller.BookingController;
import model.BookingModel;
import model.exception.NoBuildingExistsException;
import model.exception.NoPersonExistsException;
import view.bookingGUIcomponent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;


public class BookingGuiView extends JFrame implements PropertyChangeListener, ActionListener {

    private BookingModel model;
    private BookingController controller;

    private static int DEFAULT_FRAME_WIDTH = 800;
    private static int DEFAULT_FRAME_HEIGHT = 600;

    private JMenuBar mainMenuBar;
    private JMenu fileMenu, editMenu, viewMenu;
    private JMenu addMenu, removeMenu;
    private JMenuItem loadMenuItem, saveMenuItem,
            addPersonMenuItem, addBuildingMenuItem, addRoomMenuItem, addBookingMenuItem,
            removePersonMenuItem, removeBuildingMenuItem, removeRoomMenuItem, removeBookingMenuItem,
            viewTimePointMenuItem, viewTimeIntervalMenuItem, viewRoomBookingMenuItem, viewRoomFreeTimeMenuItem, viewPersonBookingMenuItem;

    private JPanel titlePanel, controlPanel, showPanel;
    private JLabel titleLabel;
    private JButton viewTimePointButton, viewTimeIntervalButton, viewRoomBookingButton, viewRoomFreeTimeButton, viewPersonBookingButton;

    public BookingGuiView(String title, BookingModel model, BookingController controller) {
        super(title);

        this.model = model;
        this.controller = controller;
        model.addListener(this);

        mainMenuBar = new JMenuBar();

        titlePanel = new JPanel();
        controlPanel = new JPanel();
        showPanel = new JPanel();


        setMainFrame();

    }

    public void setMainMenuBar() {
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        viewMenu = new JMenu("View");

        loadMenuItem = new JMenuItem("Load");
        saveMenuItem = new JMenuItem("Save");

        addMenu = new JMenu("Add");
        removeMenu = new JMenu("Remove");

        addPersonMenuItem = new JMenuItem("person");
        addBuildingMenuItem = new JMenuItem("building");
        addRoomMenuItem = new JMenuItem("room");
        addBookingMenuItem = new JMenuItem("booking");

        removePersonMenuItem = new JMenuItem("person");
        removeBuildingMenuItem = new JMenuItem("building");
        removeRoomMenuItem = new JMenuItem("room");
        removeBookingMenuItem = new JMenuItem("booking");

        viewTimePointMenuItem = new JMenuItem("room(point)");
        viewTimeIntervalMenuItem = new JMenuItem("room(interval)");
        viewRoomBookingMenuItem = new JMenuItem("booking(room)");
        viewRoomFreeTimeMenuItem = new JMenuItem("freePeriod(room)");
        viewPersonBookingMenuItem = new JMenuItem("booking(person)");

        setFileMenu();
        setEditMenu();
        setViewMenu();

        mainMenuBar.add(fileMenu);
        mainMenuBar.add(editMenu);
        mainMenuBar.add(viewMenu);

        addActionListenerForMainMenu(this);
    }
    private void setFileMenu() {
        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
    }

    private void setAddMenu() {
        addMenu.add(addPersonMenuItem);
        addMenu.add(addBuildingMenuItem);
        addMenu.add(addRoomMenuItem);
        addMenu.add(addBookingMenuItem);
    }

    private void setRemoveMenu() {
        removeMenu.add(removePersonMenuItem);
        removeMenu.add(removeBuildingMenuItem);
        removeMenu.add(removeRoomMenuItem);
        removeMenu.add(removeBookingMenuItem);
    }

    private void setEditMenu() {
        setAddMenu();
        setRemoveMenu();

        editMenu.add(addMenu);
        editMenu.add(removeMenu);
    }

    private void setViewMenu() {
        viewMenu.add(viewTimePointMenuItem);
        viewMenu.add(viewTimeIntervalMenuItem);
        viewMenu.add(viewRoomBookingMenuItem);
        viewMenu.add(viewRoomFreeTimeMenuItem);
        viewMenu.add(viewPersonBookingMenuItem);
    }

    private void addActionListenerForMainMenu(ActionListener al) {
        loadMenuItem.addActionListener(al);
        saveMenuItem.addActionListener(al);

        addPersonMenuItem.addActionListener(al);
        addBuildingMenuItem.addActionListener(al);
        addRoomMenuItem.addActionListener(al);
        addBookingMenuItem.addActionListener(al);

        removePersonMenuItem.addActionListener(al);
        removeBuildingMenuItem.addActionListener(al);
        removeRoomMenuItem.addActionListener(al);
        removeBookingMenuItem.addActionListener(al);

        viewTimePointMenuItem.addActionListener(al);
        viewTimeIntervalMenuItem.addActionListener(al);
        viewRoomBookingMenuItem.addActionListener(al);
        viewRoomFreeTimeMenuItem.addActionListener(al);
        viewPersonBookingMenuItem.addActionListener(al);

    }

    private void addActionListenerForButton(ActionListener al) {
        viewTimePointButton.addActionListener(al);
        viewTimeIntervalButton.addActionListener(al);
        viewRoomBookingButton.addActionListener(al);
        viewRoomFreeTimeButton.addActionListener(al);
        viewPersonBookingButton.addActionListener(al);
    }

    private void setTitleLabel() {
        titleLabel = new JLabel("Booking System", JLabel.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
    }

    private void setTitlePanel() {
        setTitleLabel();
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBounds(0,0,DEFAULT_FRAME_WIDTH,50);
    }

    private void setControlPanel() {
        viewTimePointButton = new JButton("room(point)");
        viewTimePointButton.setBounds(0,0,200,80);
        viewTimeIntervalButton = new JButton("room(interval)");
        viewTimeIntervalButton.setBounds(0,90,200,80);
        viewRoomBookingButton = new JButton("booking(room)");
        viewRoomBookingButton.setBounds(0,180,200,80);
        viewRoomFreeTimeButton = new JButton("freePeriod(room)");
        viewRoomFreeTimeButton.setBounds(0,270,200,80);
        viewPersonBookingButton = new JButton("booking(person)");
        viewPersonBookingButton.setBounds(0,360,200,80);

        controlPanel.setLayout(null);
        controlPanel.setBounds(0,50,200,DEFAULT_FRAME_HEIGHT);
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        controlPanel.add(viewTimePointButton);
        controlPanel.add(viewTimeIntervalButton);
        controlPanel.add(viewRoomBookingButton);
        controlPanel.add(viewRoomFreeTimeButton);
        controlPanel.add(viewPersonBookingButton);

        addActionListenerForButton(this);
    }

    private void setShowPanel() {
        JLabel welcomeLabel = new JLabel("Welcome to the university room booking system. ", JLabel.CENTER);
        showPanel.setLayout(new BorderLayout());
        showPanel.add(welcomeLabel, BorderLayout.CENTER);
        showPanel.setBounds(200,50,600,500);
        showPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void setShowPanel(JScrollPane table) {
        showPanel.removeAll();
        showPanel.setLayout(new BorderLayout());
        showPanel.add(table, BorderLayout.CENTER);
        showPanel.setBounds(200, 50, 600, 500);
        showPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void setMainFrame() {
        setMainMenuBar();
        this.setJMenuBar(mainMenuBar);


        this.setLayout(null);
        setTitlePanel();
        this.add(titlePanel);
        setControlPanel();
        this.add(controlPanel);
        setShowPanel();
        this.add(showPanel);

        this.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadMenuItem) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();
                    controller.load(file);
                    JOptionPane.showMessageDialog(this, "load successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "failed loading", JOptionPane.ERROR_MESSAGE);
                }
            }

        } else if (e.getSource() == saveMenuItem) {
            try {
                controller.save();
                JOptionPane.showMessageDialog(this, "save successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, ioe.getMessage(), "failed serializable", JOptionPane.ERROR_MESSAGE);

            }
        } else if (e.getSource() == addPersonMenuItem) {
            AddPersonFrame apf = new AddPersonFrame(this, controller);

        } else if (e.getSource() == addBuildingMenuItem) {
            AddBuildingFrame abf = new AddBuildingFrame(this, controller);

        } else if (e.getSource() == addRoomMenuItem) {
            AddRoomFrame arf = new AddRoomFrame(this, controller);

        } else if (e.getSource() == addBookingMenuItem) {
            AddBookingFrame abf = new AddBookingFrame(this, controller);

        } else if (e.getSource() == removePersonMenuItem) {
            String email = JOptionPane.showInputDialog(this, "Please input the email: ", "Remove person", JOptionPane.QUESTION_MESSAGE);
            try {
                controller.removePerson(email);
                JOptionPane.showMessageDialog(this, "remove successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NoPersonExistsException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No person exists", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == removeBuildingMenuItem) {
            String buildingName = JOptionPane.showInputDialog(this, "Please input the building name: ",
                    "Remove building", JOptionPane.QUESTION_MESSAGE);
            try {
                controller.removeBuilding(buildingName);
                JOptionPane.showMessageDialog(this, "remove successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NoBuildingExistsException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No building exists", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == removeRoomMenuItem) {
            RemoveRoomFrame rrf = new RemoveRoomFrame(this, controller);

        } else if (e.getSource() == removeBookingMenuItem) {
            RemoveBookingFrame rbf = new RemoveBookingFrame(this, controller);

        } else if (e.getSource() == viewTimePointMenuItem || e.getSource() == viewTimePointButton) {
            ViewAvailableRoomPointFrame varpf = new ViewAvailableRoomPointFrame(this, controller);

        } else if (e.getSource() == viewTimeIntervalMenuItem || e.getSource() == viewTimeIntervalButton) {
            ViewAvailableRoomIntervalFrame varif = new ViewAvailableRoomIntervalFrame(this, controller);

        } else if (e.getSource() == viewRoomBookingMenuItem || e.getSource() == viewRoomBookingButton) {
            ViewRoomBookingFrame vrbf = new ViewRoomBookingFrame(this, controller);

        } else if (e.getSource() == viewRoomFreeTimeMenuItem || e.getSource() == viewRoomFreeTimeButton) {
            ViewRoomFreeTimeFrame vrft = new ViewRoomFreeTimeFrame(this, controller);

        } else if (e.getSource() == viewPersonBookingMenuItem || e.getSource() == viewPersonBookingButton) {
            ViewPersonBookingFrame vpbf = new ViewPersonBookingFrame(this, controller);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(showPanel,evt.getPropertyName()+" has changed, new value: " + evt.getNewValue());
                    }
                });
    }
}
