package com.simon.bookings.requests;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import com.google.common.base.Preconditions;
import com.simon.organization.IEmployee;

public class SimpleBookingRequest extends BookingRequest {

    private final IEmployee requestor;
    private final Calendar requestTime;
    private final Calendar startTime;
    private final Calendar endTime;

    public SimpleBookingRequest(IEmployee requestor, Calendar requestTime, Calendar startTime, int duration) {
        Preconditions.checkArgument(requestTime.before(startTime));
        Preconditions.checkArgument(duration > 0);

        this.requestor = requestor;
        this.requestTime = requestTime;
        this.startTime = startTime;
        Calendar endTime = (Calendar) this.startTime.clone();
        endTime.add(Calendar.HOUR, duration);
        this.endTime = endTime;
    }

    @Override
    public IEmployee getRequestor() {
        return requestor;
    }

    @Override
    public Calendar getRequestTime() {
        return requestTime;
    }

    @Override
    public Calendar getStartTime() {
        return startTime;
    }

    @Override
    public Calendar getEndTime() {
        return endTime;
    }

    @Override
    public LocalDate getStartDate() {
        return LocalDate.of(startTime.get(Calendar.YEAR), Month.of(startTime.get(Calendar.MONTH) + 1),
                startTime.get(Calendar.DATE));
    }

    @Override
    protected boolean clashes(BookingRequest that) {
        if (!that.getStartTime().before(this.getStartTime()) //
                && that.getStartTime().before(this.getEndTime())) {
            return true;
        }

        if (!this.getStartTime().before(that.getStartTime()) //
                && this.getStartTime().before(that.getEndTime())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean startsOnSameDay(BookingRequest that) {
        Calendar thisStart = this.getStartTime();
        Calendar thatStart = that.getStartTime();
        return thisStart.get(Calendar.YEAR) == thatStart.get(Calendar.YEAR) //
                && thisStart.get(Calendar.MONTH) == thatStart.get(Calendar.MONTH) //
                && thisStart.get(Calendar.DATE) == thatStart.get(Calendar.DATE);
    }

    @Override
    public String toString() {
        return requestor + ", " + requestTime.toInstant() + ", " //
                + startTime.toInstant() + ", " + endTime.toInstant();
    }

}
