package model.exception;

public class RepeatedRoomNameException extends Exception {
    public RepeatedRoomNameException(String text) {
        super(text);
    }
}
