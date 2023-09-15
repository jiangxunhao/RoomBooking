package model.exception;

public class RepeatedEmailException extends Exception {
    public RepeatedEmailException(String text) {
        super(text);
    }
}
