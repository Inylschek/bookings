package com.simon.bookings.requests;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;

import com.simon.organization.IEmployee;

public abstract class BookingRequest {

    public static final Comparator<BookingRequest> REQUEST_TIME_COMPARATOR = new Comparator<BookingRequest>() {
        @Override
        public int compare(BookingRequest a, BookingRequest b) {
            return a.getRequestTime().compareTo(b.getRequestTime());
        }
    };

    public static final Comparator<BookingRequest> START_TIME_COMPARATOR = new Comparator<BookingRequest>() {
        @Override
        public int compare(BookingRequest a, BookingRequest b) {
            return a.getStartTime().compareTo(b.getStartTime());
        }
    };

    public abstract IEmployee getRequestor();

    public abstract Calendar getRequestTime();

    public abstract Calendar getStartTime();

    public abstract Calendar getEndTime();

    public abstract LocalDate getStartDate();

    public abstract boolean startsOnSameDay(BookingRequest that);

    protected abstract boolean clashes(BookingRequest booking);

    // TODO need to figure out when to check that the office is actually open

    public boolean clashes(Collection<BookingRequest> bookings) {
        for (BookingRequest booking : bookings) {
            if (this.clashes(booking)) {
                return true;
            }
        }

        return false;
    }
}
