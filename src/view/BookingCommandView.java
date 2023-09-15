package view;

import controller.BookingController;
import model.BookingModel;
import model.exception.*;
import view.bookingcommand.Configuration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class BookingCommandView implements PropertyChangeListener,Runnable {
    private BookingModel model;
    private BookingController controller;



    public BookingCommandView(BookingModel model, BookingController controller) {
        this.model = model;
        this.controller = controller;
        model.addListener(this);
        printInit();
        //receiveCommand();
    }

    public void receiveCommand() {
        while(true) {
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            switch (command) {
                case Configuration.SAVE_STRING -> handleSave();
                case Configuration.LOAD_STRING -> handleLoad();
                case Configuration.ADD_PERSON_STRING -> handleAddPerson();
                case Configuration.ADD_BUILDING_STRING -> handleAddBuilding();
                case Configuration.ADD_ROOM_STRING -> handleAddRoom();
                case Configuration.ADD_BOOKING_STRING -> handleAddBooking();
                case Configuration.REMOVE_PERSON_STRING -> handleRemovePerson();
                case Configuration.REMOVE_BUILDING_STRING -> handleRemoveBuilding();
                case Configuration.REMOVE_ROOM_STRING -> handleRemoveRoom();
                case Configuration.REMOVE_BOOKING_STRING -> handleRemoveBooking();
                case Configuration.VIEW_AVAILABLE_ROOM_POINT_STRING -> handleViewAvailableRoomPoint();
                case Configuration.VIEW_AVAILABLE_ROOM_INTERVAL_STRING -> handleViewAvailableRoomInterval();
                case Configuration.VIEW_BOOKING_OF_ROOM_STRING -> handleRoomBooking();
                case Configuration.VIEW_BOOKING_OF_PERSON_STRING -> handlePersonBooking();
                case Configuration.EXIT_STRING -> {
                    model.removeListener(this);
                    if(model.getListener().length == 0) {
                        System.exit(-1);
                    } else {
                        return;
                    }
                }

            }
        }
    }

    public void printInit() {
        System.out.println("Welcome to university booking system! ");
        System.out.println("1. save file: " + Configuration.SAVE_STRING);
        System.out.println("2. load file: " + Configuration.LOAD_STRING);
        System.out.println("3. add a new person: " + Configuration.ADD_PERSON_STRING);
        System.out.println("4. add a new building: " + Configuration.ADD_BUILDING_STRING);
        System.out.println("5. add a new room: " + Configuration.ADD_ROOM_STRING);
        System.out.println("6. add a new booking: " + Configuration.ADD_BOOKING_STRING);
        System.out.println("7. remove a new person: " + Configuration.REMOVE_PERSON_STRING);
        System.out.println("8. remove a new building: " + Configuration.REMOVE_BUILDING_STRING);
        System.out.println("9. remove a new room: " + Configuration.REMOVE_ROOM_STRING);
        System.out.println("10. remove a new booking: " + Configuration.REMOVE_BOOKING_STRING);
        System.out.println("11. view available rooms in a time point: " + Configuration.VIEW_AVAILABLE_ROOM_POINT_STRING);
        System.out.println("12. view available rooms in a time interval: " + Configuration.VIEW_AVAILABLE_ROOM_INTERVAL_STRING);
        System.out.println("13. view booking and available period of the room: " + Configuration.VIEW_BOOKING_OF_ROOM_STRING);
        System.out.println("14. view booking of the person: " + Configuration.VIEW_BOOKING_OF_PERSON_STRING);
        System.out.println("Exit: " + Configuration.EXIT_STRING);
    }

    public void handleSave() {
        try{
            controller.save();
            success();
        } catch (IOException ioe){
            unsuccess(ioe.getMessage());
        }
    }

    public void handleLoad() {
        System.out.println("Please enter a file of `*.ser` ");
        Scanner sc = new Scanner(System.in);
        String dir = sc.nextLine();
        File file = new File(dir);
        try {
            controller.load(file);
            success();
        } catch (IOException | ClassNotFoundException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleAddPerson() {
        System.out.println("Please enter a new email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        System.out.println("Please enter a name: ");
        String name = sc.nextLine();
        try {
            controller.addPerson(email, name);
            success();
        } catch (RepeatedEmailException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleAddBuilding() {
        System.out.println("Please enter a new building name: ");
        Scanner sc = new Scanner(System.in);
        String buildingName = sc.nextLine();
        System.out.println("Please enter the address: ");
        String address = sc.nextLine();
        try {
            controller.addBuilding(buildingName, address);
            success();
        } catch (RepeatedBuildingNameException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleAddRoom() {
        System.out.println("Please enter the building name: ");
        Scanner sc = new Scanner(System.in);
        String buildingName = sc.nextLine();
        System.out.println("Please enter a new room name: ");
        String roomName = sc.nextLine();
        try {
            controller.addRoom(buildingName, roomName);
            success();
        } catch (RepeatedRoomNameException | NoBuildingExistsException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleAddBooking() {
        System.out.println("Please enter the email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        System.out.println("Please enter the building name: ");
        String buildingName = sc.nextLine();
        System.out.println("Please enter the room name: ");
        String roomName = sc.nextLine();
        System.out.println("Please enter the date: (yyyy-mm-dd)");
        String dateText = sc.nextLine();
        System.out.println("Please enter the start time: (hh:mm:ss)");
        String startTimeText = sc.nextLine();
        System.out.println("Please enter the end time: (hh:mm:ss)");
        String endTimeText = sc.nextLine();
        try {
            controller.addBooking(email, buildingName, roomName, dateText, startTimeText, endTimeText);
            success();
        } catch (NoBuildingExistsException | InvalidTimeException | NoRoomExistsException | RepeatedBookingException |
                 NoPersonExistsException e) {
            unsuccess(e.getMessage());
        } catch (DateTimeParseException ex) {
            unsuccess("the valid format is (yyyy-mm-dd) and (hh:mm)");
        }
    }

    public void handleRemovePerson() {
        System.out.println("Please enter the email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        try {
            controller.removePerson(email);
            success();
        } catch (NoPersonExistsException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleRemoveBuilding() {
        System.out.println("Please enter the building name: ");
        Scanner sc = new Scanner(System.in);
        String buildingName = sc.nextLine();
        try {
            controller.removeBuilding(buildingName);
            success();
        } catch (NoBuildingExistsException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleRemoveRoom() {
        System.out.println("Please enter the building name: ");
        Scanner sc = new Scanner(System.in);
        String buildingName = sc.nextLine();
        System.out.println("Please enter the room name: ");
        String roomName = sc.nextLine();
        try {
            controller.removeRoom(buildingName, roomName);
            success();
        } catch (NoBuildingExistsException | NoRoomExistsException e) {
            unsuccess(e.getMessage());
        }
    }

    public void handleRemoveBooking() {
        System.out.println("Please enter the email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        System.out.println("Please enter the building name: ");
        String buildingName = sc.nextLine();
        System.out.println("Please enter the room name: ");
        String roomName = sc.nextLine();
        System.out.println("Please enter the date: (yyyy-mm-dd)");
        String dateText = sc.nextLine();
        System.out.println("Please enter the start time: (hh:mm:ss)");
        String startTimeText = sc.nextLine();

        try {
            controller.removeBooking(email, buildingName, roomName, dateText, startTimeText);
            success();
        } catch (NoBookingExistsException e) {
            unsuccess(e.getMessage());
        } catch (DateTimeParseException ex) {
            unsuccess("the valid format is (yyyy-mm-dd) and (hh:mm)");
        }
    }

    public void handleViewAvailableRoomPoint() {
        System.out.println("Please enter the date: (yyyy-mm-dd)");
        Scanner sc = new Scanner(System.in);
        String dateText = sc.nextLine();
        System.out.println("Please enter the time: (hh:mm:ss) ");
        String timeText = sc.nextLine();
        try {
            HashMap<String, ArrayList<String>> building_rooms = controller.getAvailableRoom(dateText, timeText);
            for (Entry<String, ArrayList<String>> entry : building_rooms.entrySet()) {
                String buildingName = entry.getKey();
                ArrayList<String> rooms = entry.getValue();
                for (String roomName : rooms) {
                    System.out.println(buildingName + "     " + roomName);
                }
            }
        } catch (DateTimeParseException ex) {
            unsuccess("the valid format is (yyyy-mm-dd) and (hh:mm)");
        }
    }

    public void handleViewAvailableRoomInterval() {
        System.out.println("Please enter the date: (yyyy-mm-dd)");
        Scanner sc = new Scanner(System.in);
        String dateText = sc.nextLine();
        System.out.println("Please enter the start time: (hh:mm:ss) ");
        String startTimeText = sc.nextLine();
        System.out.println("Please enter the end time: (hh:mm:ss) ");
        String endTimeText = sc.nextLine();
        try {
            HashMap<String, ArrayList<String>> building_rooms = controller.getAvailableRoom(dateText, startTimeText, endTimeText);
            for (Entry<String, ArrayList<String>> entry : building_rooms.entrySet()) {
                String buildingName = entry.getKey();
                ArrayList<String> rooms = entry.getValue();
                for (String roomName : rooms) {
                    System.out.println(buildingName + "     " + roomName);
                }
            }
        } catch (DateTimeParseException ex) {
            unsuccess("the valid format is (yyyy-mm-dd) and (hh:mm)");
        }
    }

    public void handleRoomBooking() {
        System.out.println("Please enter the building name: ");
        Scanner sc = new Scanner(System.in);
        String buildingName = sc.nextLine();
        System.out.println("Please enter the room name: ");
        String roomName = sc.nextLine();
        System.out.println("Please enter the date: (yyyy-mm-dd)");
        String dateText = sc.nextLine();
        try {
            ArrayList<String[]> bookings = controller.getRoomBooking(buildingName, roomName);
            ArrayList<LocalTime[]> freeTimes = controller.getRoomFreePeriod(buildingName, roomName, dateText);
            System.out.println("The all bookings of this room");
            System.out.println("Email Building Room Date StartTime EndTime");
            for (String[] booking : bookings) {
                System.out.println(booking[0] + " " + booking[1] + " " + booking[2] + " "
                        + booking[3] + " " + booking[4] + " " + booking[5]);
            }
            System.out.println();
            System.out.println("The free time of this room in the " + dateText);
            System.out.println("StartTime EndTime");
            for (LocalTime[] freeTime : freeTimes) {
                System.out.println(freeTime[0] + " " + freeTime[1]);
            }
        } catch (DateTimeParseException ex) {
            unsuccess("the valid format is (yyyy-mm-dd) and (hh:mm)");
        }
    }

    public void handlePersonBooking() {
        System.out.println("Please enter the email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.nextLine();
        ArrayList<String[]> bookings = controller.getPersonBooking(email);
        System.out.println("Email Building Room Date StartTime EndTime");
        for(String[] booking: bookings){
            System.out.println(booking[0] + " " + booking[1] + " " + booking[2] + " "
                    + booking[3] + " " + booking[4] + " " + booking[5]);
        }
    }


    public void success() {
        System.out.println("Successful! ");
    }
    public void unsuccess(String text) {
        System.out.println("Unsuccessful! " + text);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName()+" has changed, new value: " + evt.getNewValue());
    }

    @Override
    public void run() {
        receiveCommand();
    }
}
