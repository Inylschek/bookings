package com.simon.bookings.requests;

import java.util.Calendar;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.simon.bookings.requests.SimpleBookingRequest;
import com.simon.organization.Grunt;
import com.simon.organization.IEmployee;

public class SimpleBookingRequestTest {

    private static IEmployee EMPLOYEE_A = new Grunt("A");

    private static int DEFAULT_YEAR = 2000;
    private static int DEFAULT_MONTH = 0;
    private static int DEFAULT_DATE = 1;
    private static int DEFAULT_MILLISECOND = 0;

    @Test(expected = IllegalArgumentException.class)
    public void testRequestAfterStartThrowsError() {
        Calendar requestTime = calendar(13, 0, 0);
        Calendar startTime = calendar(12, 0, 0);
        int duration = 1;

        new SimpleBookingRequest(EMPLOYEE_A, requestTime, startTime, duration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroDurationThrowsError() {
        Calendar requestTime = calendar(11, 0, 0);
        Calendar startTime = calendar(12, 0, 0);
        int duration = 0;

        new SimpleBookingRequest(EMPLOYEE_A, requestTime, startTime, duration);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDurationThrowsError() {
        Calendar requestTime = calendar(11, 0, 0);
        Calendar startTime = calendar(12, 0, 0);
        int duration = -1;

        new SimpleBookingRequest(EMPLOYEE_A, requestTime, startTime, duration);
    }

    @Test
    public void testAStartsAfterBEnds_NoClash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(14, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                1);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(false));
    }

    @Test
    public void testAEndsBeforeBStarts_NoClash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(14, 0, 0), //
                1);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(false));
    }

    @Test
    public void testABStartAtSameTimeOnDifferentDays_NoClash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(DEFAULT_YEAR, DEFAULT_MONTH, DEFAULT_DATE + 1, 12, 0, 0), //
                1);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(false));
    }

    @Test
    public void testAEndsWhenBStarts_NoClash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(13, 0, 0), //
                1);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(false));
    }

    @Test
    public void testAStartsWhenBEnds_NoClash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(13, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                1);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(false));
    }

    @Test
    public void testAStartsDuringB_Clash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(13, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                3);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(true));
    }

    @Test
    public void testBStartsDuringA_Clash() {
        SimpleBookingRequest a = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(13, 0, 0), //
                1);
        SimpleBookingRequest b = new SimpleBookingRequest(EMPLOYEE_A, //
                calendar(11, 0, 0), //
                calendar(12, 0, 0), //
                3);

        Assert.assertThat(a.clashes(b), CoreMatchers.is(true));
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
