package com.railway.daoImpl;

import com.railway.dao.BookingDAO;
import com.railway.model.Booking;
import com.railway.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private final Connection connection;

    public BookingDAOImpl() {
        connection = DBConnection.getConnection();
    }

    @Override
    public boolean createBooking(Booking booking) {
        String query = "INSERT INTO booking (passengerId, trainId, seatNumber, coach, bookingDate, travel_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getPassengerId());
            preparedStatement.setInt(2, booking.getTrainId());
            preparedStatement.setInt(3, booking.getSeatNumber());
            preparedStatement.setString(4, booking.getCoach());
            preparedStatement.setDate(5, booking.getBookingDate());
            preparedStatement.setDate(6, booking.getTravelDate());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error creating booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String query = "DELETE FROM booking WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookingId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Booking> getBookingsByPassengerId(int passengerId) {
        List<Booking> bookingList = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE passengerId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passengerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = extractBooking(resultSet);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            System.err.println(" Error fetching bookings by passenger: " + e.getMessage());
        }
        return bookingList;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        String query = "SELECT * FROM booking";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Booking booking = extractBooking(resultSet);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            System.err.println(" Error fetching all bookings: " + e.getMessage());
        }
        return bookingList;
    }

    @Override
    public List<Booking> getBookedSeatsByTrainAndDate(int trainId, Date travelDate) {
        List<Booking> bookedSeats = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE trainId = ? AND travel_date = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, trainId);
            preparedStatement.setDate(2, travelDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookedSeats.add(extractBooking(resultSet));
            }
        } catch (SQLException e) {
            System.err.println(" Error fetching booked seats: " + e.getMessage());
        }
        return bookedSeats;
    }

    private Booking extractBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        booking.setPassengerId(resultSet.getInt("passengerId"));
        booking.setTrainId(resultSet.getInt("trainId"));
        booking.setSeatNumber(resultSet.getInt("seatNumber"));
        booking.setCoach(resultSet.getString("coach"));
        booking.setBookingDate(resultSet.getDate("bookingDate"));
        booking.setTravelDate(resultSet.getDate("travel_date"));
        return booking;
    }
}
