package model;

import java.io.Serializable;
import java.util.HashMap;

public class Building implements Serializable {
    private String name;
    private String address;
    private HashMap<String, Room> rooms;

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
        this.rooms = new HashMap<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addRoom(Room room) {
        String roomName = room.getName();
        rooms.put(roomName, room);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public Room getRoom(String roomName) {
        return rooms.get(roomName);
    }


}
