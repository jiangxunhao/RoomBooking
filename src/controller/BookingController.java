package controller;

import model.*;
import model.exception.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

public class BookingController {
    static final int MIN_BLOCK_TIME = 5;

    private BookingModel model;

    public BookingController(BookingModel model) {
        this.model = model;
    }

    public void setModel(BookingModel model) {
        this.model = model;
    };

    public void addBuilding(String buildingName, String address) throws RepeatedBuildingNameException {
        if(hasBuildingName(buildingName)) {
            throw new RepeatedBuildingNameException("There has already been a building with name of `" + buildingName + "`! " );
        } else {
            Building building = new Building(buildingName,address);

            model.addBuilding(building);
        }

    }

    public void removeBuilding(String buildingName) throws NoBuildingExistsException {
        if (hasBuildingName(buildingName)) {
            model.removeBuilding(buildingName);
        } else {
            throw new NoBuildingExistsException("Cannot find the building named " + buildingName + "! Cannot remove! ");
        }

    }

    public void addRoom(String buildingName, String roomName) throws RepeatedRoomNameException, NoBuildingExistsException {
        if ( hasBuildingName(buildingName) ) {
            if(hasRoomName(buildingName, roomName)) {
                throw new RepeatedRoomNameException("There has already been a room with the name of `" + roomName + "` in the `" + buildingName + "`! ");
            } else {
                Room room = new Room(roomName);

                model.addRoom(buildingName, room);
            }
        } else {
            throw new NoBuildingExistsException("Cannot find the building named " + buildingName + "! Cannot add! ");
        }

    }

    public void removeRoom(String buildingName, String roomName) throws NoBuildingExistsException, NoRoomExistsException{
        if (hasBuildingName(buildingName)) {
            if (hasRoomName(buildingName, roomName)) {
                model.removeRoom(buildingName, roomName);
            } else {
                throw new NoRoomExistsException("Cannot find the room named `" + roomName + "` in the `" + buildingName + "`! ");
            }
        } else {
            throw new NoBuildingExistsException("Cannot find the building named " + buildingName + "! Cannot remove! ");
        }
    }

    public void addPerson(String email, String name) throws RepeatedEmailException {
        if (hasEmail(email)) {
            throw new RepeatedEmailException("This email " + email + " has already been registered! ");
        } else {
            Person person = new Person(email, name);
            model.addPerson(person);
        }
    }

    public void removePerson(String email) throws NoPersonExistsException {
        if (hasEmail(email)) {
            model.removePerson(email);
        } else {
            throw new NoPersonExistsException("This email `" + email + "` has not registered! Cannot remove! ");
        }
    }

    public void addBooking(String email, String buildingName, String roomName, String dateText, String startTimeText, String endTimeText)
            throws RepeatedBookingException, InvalidTimeException, NoPersonExistsException, NoBuildingExistsException, NoRoomExistsException {

        if (!hasEmail(email)) {
            throw new NoPersonExistsException("This email `" + email + "` has not registered! Cannot book! ");
        }
        Person person = model.getPerson(email);

        if (!hasBuildingName(buildingName)) {
            throw new NoBuildingExistsException("Cannot find the building named " + buildingName + "! Cannot book! ");
        }
        Building building = model.getBuilding(buildingName);

        if (!hasRoomName(buildingName, roomName)) {
            throw new NoRoomExistsException("Cannot find the room named `" + roomName + "` in the `" + buildingName + "`! Cannot book! ");
        }
        Room room = model.getRoom(buildingName, roomName);

        LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime startTime = LocalTime.parse(startTimeText, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime endTime = LocalTime.parse(endTimeText, DateTimeFormatter.ISO_LOCAL_TIME);
        LocalTime[] times = {startTime, endTime};
        if (!isValidTimes(date, startTime, endTime)) {
            throw new InvalidTimeException("Invalid Time! The minimum time block is "
                    + MIN_BLOCK_TIME + " or the booked time is before current time. Cannot book! ");
        }

        String primaryKey = email + buildingName + roomName + dateText + startTimeText;
        if (hasPrimaryKey(primaryKey)) {
            throw new RepeatedBookingException("Repeated booking! Cannot book!");
        }

        Booking booking = new Booking(primaryKey, person, building, room, date, times);
        model.addBooking(booking);


    }

    public void removeBooking(String email, String buildingName, String roomName, String dateText, String startTimeText) throws NoBookingExistsException {
        String primaryKey = email + buildingName + roomName + dateText + startTimeText;
        if (hasPrimaryKey(primaryKey)) {
            model.removeBooking(primaryKey);
        } else {
            throw new NoBookingExistsException("Cannot fine the booking! Cannot remove! ");
        }

    }

    public Vector<String> getBuildingNames() {
        return model.getBuildingNames();
    }

    public HashMap<String, ArrayList<String>> getAvailableRoom(String dateText, String timeText) {
        HashMap<String, ArrayList<String>> availableRooms = new HashMap<>();

        LocalDate date = LocalDate.parse(dateText);
        LocalTime time = LocalTime.parse(timeText);

        HashMap<String, Building> buildings = model.getBuildings();
        for( Entry<String, Building> buildingEntry: buildings.entrySet() ) {
            HashMap<String, Room> rooms = buildingEntry.getValue().getRooms();

            ArrayList<String> ar = new ArrayList<>();
            for( Entry<String, Room> roomEntry: rooms.entrySet()) {
                if (roomEntry.getValue().isAvailable(date, time)) {
                    ar.add(roomEntry.getKey());
                }
            }
            if(ar != null) {
                availableRooms.put(buildingEntry.getKey(), ar);
            }
        }

        return availableRooms;
    }

    public HashMap<String, ArrayList<String>> getAvailableRoom(String dateText, String startTimeText, String endTimeText) {
        HashMap<String, ArrayList<String>> availableRooms = new HashMap<>();

        LocalDate date = LocalDate.parse(dateText);
        LocalTime startTime = LocalTime.parse(startTimeText);
        LocalTime endTime = LocalTime.parse(endTimeText);

        HashMap<String, Building> buildings = model.getBuildings();
        for( Entry<String, Building> buildingEntry: buildings.entrySet() ) {
            HashMap<String, Room> rooms = buildingEntry.getValue().getRooms();

            ArrayList<String> ar = new ArrayList<>();
            for( Entry<String, Room> roomEntry: rooms.entrySet()) {
                if (roomEntry.getValue().isAvailable(date, startTime, endTime)) {
                    ar.add(roomEntry.getKey());
                }
            }
            if(ar != null) {
                availableRooms.put(buildingEntry.getKey(), ar);
            }
        }

        return availableRooms;
    }

    public ArrayList<String[]> getRoomBooking(String buildingName, String roomName) {
        ArrayList<String[]> res = new ArrayList<>();
        Room room = model.getRoom(buildingName, roomName);
        ArrayList<Booking> bookings = room.getBookings();
        for (Booking booking: bookings) {
            String[] bookingText = new String[] {booking.getEmail(), booking.getBuildingName(), booking.getRoomName(),
                    booking.getDate().toString(), booking.getStartTime().toString(), booking.getEndTime().toString()};
            res.add(bookingText);
        }
        return res;
    }

    public ArrayList<LocalTime[]> getRoomFreePeriod(String buildingName, String roomName, String dateText) {
        Room room = model.getRoom(buildingName, roomName);
        LocalDate date = LocalDate.parse(dateText);
        return room.getAvailableOfDay(date);
    }

    public ArrayList<String[]> getPersonBooking(String email){
        ArrayList<String[]> res = new ArrayList<>();
        Person person = model.getPerson(email);
        ArrayList<Booking> bookings = person.getBookings();
        for (Booking booking: bookings) {
            String[] bookingText = new String[] {booking.getEmail(), booking.getBuildingName(), booking.getRoomName(),
                    booking.getDate().toString(), booking.getStartTime().toString(), booking.getEndTime().toString()};
            res.add(bookingText);
        }
        return res;
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("model.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model.getPeople());
        oos.writeObject(model.getBuildings());
        oos.writeObject(model.getBookings());
        oos.close();
    }

    public void load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap<String, Person> people = (HashMap<String, Person>) ois.readObject();
        HashMap<String, Building> buildings = (HashMap<String, Building>) ois.readObject();
        HashMap<String, Booking> bookings = (HashMap<String, Booking>) ois.readObject();
        model.setPeople(people);
        model.setBuildings(buildings);
        model.setBookings(bookings);
        ois.close();
    }

    private boolean hasBuildingName(String buildingName) {
        HashMap<String, Building> buildings = model.getBuildings();
        if (buildings.containsKey(buildingName)) {
            return true;
        }
        return false;
    }

    private boolean hasRoomName(String buildingName, String roomName) {
        HashMap<String, Room> rooms = model.getRooms(buildingName);
        if (rooms.containsKey(roomName)) {
            return true;
        }
        return false;
    }

    private boolean hasEmail(String email) {
        HashMap<String, Person> people = model.getPeople();
        if (people.containsKey(email)) {
            return true;
        }
        return false;
    }

    private boolean hasPrimaryKey(String primaryKey) {
        HashMap<String, Booking> bookings = model.getBookings();
        if (bookings.containsKey(primaryKey)) {
            return true;
        }
        return false;
    }

    private boolean isValidTimes(LocalDate date, LocalTime startTime, LocalTime endTime) {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalTime endTime0 = endTime.minusMinutes(MIN_BLOCK_TIME);
        if(date.isBefore(nowDate)) {
            return false;
        }
        if ( date.equals(nowDate) && startTime.isBefore(nowTime) ) {
            return false;
        }
        if(startTime.isAfter(endTime0)) {
            return false;
        }

        return true;
    }

}
