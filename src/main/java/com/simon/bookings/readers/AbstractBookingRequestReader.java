package com.simon.bookings.readers;

import java.io.IOException;
import java.util.List;

import com.simon.bookings.requests.BookingRequest;
import com.simon.bookings.spaces.IOffice;

public abstract class AbstractBookingRequestReader {

    abstract public void read() throws IOException;

    abstract public List<BookingRequest> getBookingRequests();

    abstract public IOffice getOffice();
}
