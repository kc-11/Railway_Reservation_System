package com.railway.model;

import java.sql.Date;

public class Booking {
    private int id;
    private int passengerId;
    private int trainId;
    private int seatNumber;
    private String coach;
    private Date bookingDate;
    private Date travelDate;

    public Booking() {}

    public Booking(int id, int passengerId, int trainId, int seatNumber, String coach, Date bookingDate, Date travelDate) {
        this.id = id;
        this.passengerId = passengerId;
        this.trainId = trainId;
        this.seatNumber = seatNumber;
        this.coach = coach;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
    }

    public Booking(int passengerId, int trainId, int seatNumber, String coach, Date bookingDate, Date travelDate) {
        this.passengerId = passengerId;
        this.trainId = trainId;
        this.seatNumber = seatNumber;
        this.coach = coach;
        this.bookingDate = bookingDate;
        this.travelDate = travelDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    @Override
    public String toString() {
        return "Booking [ID=" + id + ", PassengerID=" + passengerId + ", TrainID=" + trainId +
                ", Seat=" + coach + " " + seatNumber + ", BookingDate=" + bookingDate +
                ", TravelDate=" + travelDate + "]";
    }
}
