package com.railway.daoImpl;

import com.railway.dao.TrainDAO;
import com.railway.model.Train;
import com.railway.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAOImpl implements TrainDAO {

    private Connection connection;

    public TrainDAOImpl() {
        connection = DBConnection.getConnection();
    }

    @Override
    public boolean addTrain(Train train) {
        String query = "INSERT INTO train (name, source, destination, totalSeats) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, train.getName());
            preparedStatement.setString(2, train.getSource());
            preparedStatement.setString(3, train.getDestination());
            preparedStatement.setInt(4, train.getTotalSeats());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding train: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTrain(int trainId) {
        String query = "DELETE FROM train WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, trainId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting train: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Train getTrainById(int trainId) {
        String query = "SELECT * FROM train WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, trainId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Train(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("source"),
                    resultSet.getString("destination"),
                    resultSet.getInt("totalSeats")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching train by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Train> getAllTrains() {
        List<Train> trainList = new ArrayList<>();
        String query = "SELECT * FROM train";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Train train = new Train(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("source"),
                    resultSet.getString("destination"),
                    resultSet.getInt("totalSeats")
                );
                trainList.add(train);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all trains: " + e.getMessage());
        }
        return trainList;
    }
}
