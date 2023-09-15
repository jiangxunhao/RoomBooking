package model.exception;

public class RepeatedBookingException extends Exception {
    public RepeatedBookingException(String text) {
        super(text);
    }
}
