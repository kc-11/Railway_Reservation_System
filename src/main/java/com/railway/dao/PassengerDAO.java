package com.railway.dao;

import com.railway.model.Passenger;
import java.util.List;

public interface PassengerDAO {
    boolean addPassenger(Passenger passenger);
    boolean deletePassenger(int passengerId);
    Passenger getPassengerById(int passengerId);
    List<Passenger> getAllPassengers();
}
