package com.simon.bookings.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.simon.bookings.requests.BookingRequest;
import com.simon.bookings.requests.SimpleBookingRequest;
import com.simon.bookings.spaces.IOffice;
import com.simon.bookings.spaces.SimpleOffice;
import com.simon.organization.Grunt;
import com.simon.organization.IEmployee;

public class SimpleBookingRequestFileReader extends AbstractBookingRequestReader {

    private static final String LINE_SPLITTER = "\\s|:|-";

    private final String location;

    private SimpleOffice office;
    private List<BookingRequest> requests;

    public SimpleBookingRequestFileReader(String location) throws IOException {
        this.location = location;
    }

    @Override
    public void read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(location));

        String line = reader.readLine();
        office = readOffice(line);
        requests = collectBookingRequests(reader);
    }

    private SimpleOffice readOffice(String line) {
        String[] times = line.split(" ");

        LocalTime openingTime = convertStringToLocalTime(times[0]);
        LocalTime closingTime = convertStringToLocalTime(times[1]);

        return new SimpleOffice(openingTime, closingTime);
    }

    private LocalTime convertStringToLocalTime(String string) {
        return LocalTime.of(Integer.parseInt(string.substring(0, 2)), Integer.parseInt(string.substring(2, 4)));
    }

    private List<BookingRequest> collectBookingRequests(BufferedReader reader) throws IOException {
        List<BookingRequest> list = new LinkedList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(LINE_SPLITTER);
            Calendar requestMadeDate = buildCalendar(split[0], split[1], split[2], split[3], split[4], split[5]);
            IEmployee employee = new Grunt(split[6]);

            line = reader.readLine();
            split = line.split(LINE_SPLITTER);
            Calendar requestStartDate = buildCalendar(split[0], split[1], split[2], split[3], split[4]);
            int duration = Integer.parseInt(split[5]);

            list.add(new SimpleBookingRequest(employee, requestMadeDate, requestStartDate, duration));
        }

        return list;
    }

    private Calendar buildCalendar(String year, String month, String date, String hour, String min, String second) {
        Calendar.Builder builder = new Calendar.Builder();
        return builder.set(Calendar.YEAR, Integer.parseInt(year)) //
                .set(Calendar.MONTH, Integer.parseInt(month) - 1) //
                .set(Calendar.DATE, Integer.parseInt(date)) //
                .set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour)) //
                .set(Calendar.MINUTE, Integer.parseInt(min)) //
                .set(Calendar.SECOND, Integer.parseInt(second)) //
                .build();
    }

    private Calendar buildCalendar(String year, String month, String date, String hour, String min) {
        return buildCalendar(year, month, date, hour, min, "0");
    }

    @Override
    public List<BookingRequest> getBookingRequests() {
        return requests;
    }

    @Override
    public IOffice getOffice() {
        return office;
    }

}
