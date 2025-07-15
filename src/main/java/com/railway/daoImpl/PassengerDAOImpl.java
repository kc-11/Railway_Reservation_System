package com.railway.daoImpl;

import com.railway.dao.PassengerDAO;
import com.railway.model.Passenger;
import com.railway.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAOImpl implements PassengerDAO {

    private Connection connection;

    public PassengerDAOImpl() {
        connection = DBConnection.getConnection();
    }


    @Override
    public boolean addPassenger(Passenger passenger) {
        String query = "INSERT INTO passenger (name, age, gender, phone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, passenger.getName());
            preparedStatement.setInt(2, passenger.getAge());
            preparedStatement.setString(3, passenger.getGender());
            preparedStatement.setString(4, passenger.getPhone());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        passenger.setId(generatedId); // Set ID into object
                        System.out.println(" Passenger registered with ID: " + generatedId);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding passenger: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deletePassenger(int passengerId) {
        String query = "DELETE FROM passenger WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passengerId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting passenger: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Passenger getPassengerById(int passengerId) {
        String query = "SELECT * FROM passenger WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, passengerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Passenger(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("gender"),
                    resultSet.getString("phone")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching passenger by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengerList = new ArrayList<>();
        String query = "SELECT * FROM passenger";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Passenger passenger = new Passenger(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getString("gender"),
                    resultSet.getString("phone")
                );
                passengerList.add(passenger);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all passengers: " + e.getMessage());
        }
        return passengerList;
    }
}
