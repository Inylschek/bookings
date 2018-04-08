package com.simon.bookings.spaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.simon.bookings.requests.BookingRequest;

public interface IRoom {

    public int getId();

    public List<BookingRequest> getBookings();

    public Map<LocalDate, List<BookingRequest>> getBookingsByDate();

    public boolean bookRequest(BookingRequest request);

    public LocalTime getOpeningTime();

    public LocalTime getClosingTime();

    public boolean isOpenFor(BookingRequest request);

    public void printBookings();
}
