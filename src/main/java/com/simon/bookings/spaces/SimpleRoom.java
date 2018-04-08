package com.simon.bookings.spaces;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.simon.bookings.requests.BookingRequest;

public class SimpleRoom implements IRoom {

    private final int id;
    private final LocalTime openingTime;
    private final LocalTime closingTime;

    private final Map<LocalDate, List<BookingRequest>> bookings = new HashMap<>();

//    private final List<BookingRequest> bookings = new LinkedList<>();

    public SimpleRoom(int id, IOffice office) {
        this.id = id;
        this.openingTime = office.getOpeningTime();
        this.closingTime = office.getClosingTime();
    }

    public SimpleRoom(IOffice office) {
        this(newRandomId(office), office);
    }

    @Override
    public int getId() {
        return id;
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
    public List<BookingRequest> getBookings() {
        return bookings.keySet() //
                .stream() //
                .flatMap(localDate -> bookings.get(localDate).stream()) //
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDate, List<BookingRequest>> getBookingsByDate() {
        return bookings;
    }

    @Override
    public boolean bookRequest(BookingRequest request) {
        LocalDate date = request.getStartDate();

        if (bookings.containsKey(date)) {
            List<BookingRequest> requests = bookings.get(date);
            return requests.add(request);
        } else {
            List<BookingRequest> requests = Lists.newArrayList(request);
            bookings.put(date, requests);
            return true;
        }
    }

    @Override
    public boolean isOpenFor(BookingRequest request) {
        return !openingTime.isAfter(toLocalTime(request.getStartTime())) //
                && !closingTime.isBefore(toLocalTime(request.getEndTime()));
    }

    private LocalTime toLocalTime(Calendar c) {
        return LocalTime.of(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
    }

    @Override
    public void printBookings() {
        System.out.println("Room " + id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Set<LocalDate> keys = bookings.keySet();
        keys.stream().sorted().forEach(key -> {
            List<BookingRequest> requests = bookings.get(key);
            requests.sort(BookingRequest.START_TIME_COMPARATOR);

            System.out.println(dateFormat.format(requests.get(0).getStartTime().getTime()));

            requests.forEach(request -> {
                StringBuilder sb = new StringBuilder();
                sb.append(timeFormat.format(request.getStartTime().getTime())) //
                        .append(' ') //
                        .append(timeFormat.format(request.getEndTime().getTime())) //
                        .append(' ') //
                        .append(request.getRequestor().getEmployeeCode());

                System.out.println(sb.toString());
            });
        });
    }

    private static int newRandomId(IOffice office) {
        Random generator = new Random();
        while (true) {
            int i = generator.nextInt();
            if (!office.hasRoom(i)) {
                return i;
            }
        }
    }

}
