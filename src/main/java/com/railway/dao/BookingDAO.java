package com.railway.dao;

import com.railway.model.Booking;
import java.sql.Date;
import java.util.List;

public interface BookingDAO {
    boolean createBooking(Booking booking);
    boolean cancelBooking(int bookingId);
    List<Booking> getBookingsByPassengerId(int passengerId);
    List<Booking> getAllBookings();

    // 🔄 New Method to get all booked seats for a train on a particular date
    List<Booking> getBookedSeatsByTrainAndDate(int trainId, Date travelDate);
}
