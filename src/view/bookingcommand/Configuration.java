package view.bookingcommand;

public abstract class Configuration {
    public static final String SAVE_STRING = "SAVE", LOAD_STRING = "LOAD",
            ADD_PERSON_STRING = "ADD person", ADD_BUILDING_STRING = "ADD building",
            ADD_ROOM_STRING = "ADD room", ADD_BOOKING_STRING = "ADD booking",
            REMOVE_PERSON_STRING = "REMOVE person", REMOVE_BUILDING_STRING = "REMOVE building",
            REMOVE_ROOM_STRING = "REMOVE room", REMOVE_BOOKING_STRING = "REMOVE booking",
            VIEW_AVAILABLE_ROOM_POINT_STRING = "VIEW availableRoomPoint", VIEW_AVAILABLE_ROOM_INTERVAL_STRING = "VIEW availableRoomInterval",
            VIEW_BOOKING_OF_ROOM_STRING = "VIEW roomBooking", VIEW_BOOKING_OF_PERSON_STRING = "VIEW personBooking",
            EXIT_STRING = "EXIT";
}
