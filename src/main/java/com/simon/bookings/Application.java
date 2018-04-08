package com.simon.bookings;

import java.io.IOException;
import java.util.List;

import com.simon.bookings.readers.SimpleBookingRequestFileReader;
import com.simon.bookings.requests.BookingRequest;
import com.simon.bookings.spaces.IOffice;

public class Application {

    public static void main(String[] args) throws IOException {
        SimpleBookingRequestFileReader reader = new SimpleBookingRequestFileReader(args[0]);
        reader.read();

        IOffice office = reader.getOffice();
        List<BookingRequest> requests = reader.getBookingRequests();
        requests.sort(BookingRequest.REQUEST_TIME_COMPARATOR);

        requests.forEach(request -> {
            office.getRooms() //
                    .stream() //
                    .filter(room -> room.isOpenFor(request)) //
                    .filter(room -> !request.clashes(room.getBookings())) //
                    .findFirst() //
                    .ifPresent(room -> room.bookRequest(request));
        });

        office.printAllBookings();
    }

}
