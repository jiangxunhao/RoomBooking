package main;

import controller.BookingController;
import model.BookingModel;
import view.BookingCommandView;
import view.BookingGuiView;

public class Main {
    public static void main(String[] args) {
        BookingModel model = new BookingModel();
        BookingController controller = new BookingController(model);
        BookingGuiView gui = new BookingGuiView("Booking System", model, controller);
        BookingCommandView comm = new BookingCommandView(model, controller);
        comm.run();
    }
}