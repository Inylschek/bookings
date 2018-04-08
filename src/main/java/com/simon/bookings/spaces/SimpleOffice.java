package com.simon.bookings.spaces;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

public class SimpleOffice implements IOffice {

    private final LocalTime openingTime;
    private final LocalTime closingTime;

    private final Collection<IRoom> rooms;

    public SimpleOffice(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.rooms = Collections.singleton(new SimpleRoom(1, this));
    }

    @Override
    public LocalTime getOpeningTime() {
        return openingTime;
    }

    @Override
    public LocalTime getClosingTime() {
        return closingTime;
    }

    @Override
    public Collection<IRoom> getRooms() {
        return rooms;
    }

    @Override
    public void printAllBookings() {
        rooms.forEach(room -> room.printBookings());
    }

    @Override
    public boolean hasRoom(int id) {
        return rooms.stream() //
                .filter(room -> room.getId() == id) //
                .findFirst() //
                .isPresent();
    }
}
