package model.exception;

public class NoBookingExistsException extends Exception {
    public NoBookingExistsException(String text) {
        super(text);
    }
}
