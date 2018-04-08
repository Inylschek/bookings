package com.simon.bookings.spaces;

import java.time.LocalTime;
import java.util.Calendar;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.simon.bookings.requests.BookingRequest;
import com.simon.bookings.requests.SimpleBookingRequest;
import com.simon.bookings.spaces.IOffice;
import com.simon.bookings.spaces.SimpleOffice;
import com.simon.bookings.spaces.SimpleRoom;
import com.simon.organization.Grunt;

public class SimpleRoomTest {

    private static int ROOM_ID = 1;
    private static String EMPLOYEE_ID = "EMP001";

    private static int DEFAULT_YEAR = 2000;
    private static int DEFAULT_MONTH = 0;
    private static int DEFAULT_DATE = 1;
    private static int DEFAULT_MILLISECOND = 0;

    @Test
    public void testRequestStartsAfterOpeningEndsBeforeClosing_isOpen() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(11, 0, 0, 1);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(true));
    }

    @Test
    public void testRequestBeginsAtOpeningEndsBeforeClosing_isOpen() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(9, 0, 0, 1);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(true));
    }

    @Test
    public void testRequestBeginsAfterOpeningEndsAtClosing_isOpen() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(16, 0, 0, 1);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(true));
    }

    @Test
    public void testRequestBeginsAtOpeningEndsAtClosing_isOpen() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(9, 0, 0, 8);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(true));
    }

    @Test
    public void testRequestBeginsBeforeOpeningEndsBeforeClosing_isClosed() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(7, 0, 0, 6);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(false));
    }

    @Test
    public void testRequestBeginsAfterOpeningEndsAfterClosing_isClosed() {
        LocalTime opensAt = localTime(9, 0, 0);
        LocalTime closesAt = localTime(17, 0, 0);
        IOffice office = new SimpleOffice(opensAt, closesAt);
        SimpleRoom room = new SimpleRoom(ROOM_ID, office);

        BookingRequest request = request(13, 0, 0, 7);

        Assert.assertThat(room.isOpenFor(request), CoreMatchers.is(false));
    }

    private BookingRequest request(int startsHour, int startsMinute, int startsSecond, int duration) {
        Calendar requests = calendar(startsHour - 1, startsMinute, startsSecond);
        Calendar starts = calendar(startsHour, startsMinute, startsSecond);

        return new SimpleBookingRequest(new Grunt(EMPLOYEE_ID), requests, starts, duration);
    }

    private LocalTime localTime(int hour, int minute, int second) {
        return LocalTime.of(hour, minute, second);
    }

    private Calendar calendar(int hour, int minute, int second) {
        return calendar(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_DATE, hour, minute, second);
    }

    private Calendar calendar(int year, int month, int date, int hour, int minute, int second) {
        Calendar.Builder cb = new Calendar.Builder();
        return cb.set(Calendar.YEAR, year) //
                .set(Calendar.MONTH, month) //
                .set(Calendar.DATE, date) //
                .set(Calendar.HOUR_OF_DAY, hour) //
                .set(Calendar.MINUTE, minute) //
                .set(Calendar.SECOND, second) //
                .set(Calendar.MILLISECOND, DEFAULT_MILLISECOND) //
                .build();
    }
}
