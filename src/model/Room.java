package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Room implements Serializable {
    private String name;
    private HashMap<LocalDate, ArrayList<LocalTime[]>> occupiedTimes;
    private ArrayList<Booking> bookings;

    public Room(String name) {
        this.name = name;
        this.occupiedTimes = new HashMap<>();
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        LocalDate date = booking.getDate();
        LocalTime[] times = booking.getTimes();
        addOccupiedTime(date, times);
    }

    public String getName() {
        return name;
    }

    public ArrayList<LocalTime[]> getOccupiedOfDay(LocalDate date) {
        return occupiedTimes.get(date);
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public ArrayList<LocalTime[]> getAvailableOfDay(LocalDate date) {
        ArrayList<LocalTime[]> availableTimeOfDay = new ArrayList<>();
        ArrayList<LocalTime[]> occupiedTimeOfDay = getOccupiedOfDay(date);
        LocalTime dayBegin = LocalTime.of(0,0,0);
        LocalTime dayEnd = LocalTime.of(23,59,59);

        if(occupiedTimeOfDay == null){
            availableTimeOfDay.add(new LocalTime[] {dayBegin, dayEnd});
            return availableTimeOfDay;
        }

        LocalTime time0 = dayBegin;
        for (LocalTime[] time: occupiedTimeOfDay) {
            if( time0.isBefore(time[0]) ) {
                availableTimeOfDay.add( new LocalTime[] {time0, time[0].minusSeconds(1)} );
            }
            time0 = time[1].plusSeconds(1);
        }

        if(!time0.equals(dayEnd)) {
            availableTimeOfDay.add( new LocalTime[] {time0, dayEnd} );
        }


        return availableTimeOfDay;
    }

    public boolean isAvailable(LocalDate date, LocalTime time) {
        ArrayList<LocalTime[]> occupiedTimeOfDay = getOccupiedOfDay(date);
        if(occupiedTimeOfDay == null) {
            return true;
        }
        for (LocalTime[] occupied: occupiedTimeOfDay) {
            if( time.isAfter(occupied[0]) && time.isBefore(occupied[1]) ) {
                return false;
            }
        }
        return true;
    }

    public boolean isAvailable(LocalDate date, LocalTime startTime, LocalTime endTime) {
        ArrayList<LocalTime[]> occupiedTimeOfDay = getOccupiedOfDay(date);
        if(occupiedTimeOfDay == null) {
            return true;
        }
        for (LocalTime[] occupied: occupiedTimeOfDay) {
            if( ( endTime.isAfter(occupied[1]) && startTime.isBefore(occupied[1]) )
                    || ( startTime.isBefore(occupied[0]) && endTime.isAfter(occupied[1]) )
                    || ( startTime.isBefore(occupied[0]) && endTime.isAfter(occupied[0]) ) ) {
                return false;
            }
        }
        return true;
    }

    private void addOccupiedTime(LocalDate date, LocalTime[] times) {
        ArrayList<LocalTime[]> occupiedTimeOfDay = occupiedTimes.getOrDefault(date, new ArrayList<>());

        if (occupiedTimeOfDay.size() == 0) {
            occupiedTimeOfDay.add(times);
            occupiedTimes.put(date, occupiedTimeOfDay);
            return;
        }

        for(int i = 0; i < occupiedTimeOfDay.size(); i++) {
            LocalTime[] occTime = occupiedTimeOfDay.get(i);
            if (times[0].isBefore(occTime[0])) {
                occupiedTimeOfDay.add(i, times);
            }
        }

        occupiedTimes.put(date,occupiedTimeOfDay);
    }

}
