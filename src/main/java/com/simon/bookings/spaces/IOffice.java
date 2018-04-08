package com.simon.bookings.spaces;

import java.time.LocalTime;
import java.util.Collection;

public interface IOffice {
    public LocalTime getOpeningTime();

    public LocalTime getClosingTime();

    public Collection<IRoom> getRooms();

    public boolean hasRoom(int id);

    public void printAllBookings();
}
