package com.railway.service;

import com.railway.dao.*;
import com.railway.daoImpl.*;
import com.railway.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RailwayService {

    private final AdminDAO adminDAO;
    private final PassengerDAO passengerDAO;
    private final TrainDAO trainDAO;
    private final BookingDAO bookingDAO;
    private final Scanner scanner;

    public RailwayService() {
        adminDAO = new AdminDAOImpl();
        passengerDAO = new PassengerDAOImpl();
        trainDAO = new TrainDAOImpl();
        bookingDAO = new BookingDAOImpl();
        scanner = new Scanner(System.in);
    }

    // === ADMIN SECTION ===

    public boolean registerAdmin(Admin admin) {
        return adminDAO.registerAdmin(admin);
    }

    public Admin adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        Admin admin = adminDAO.login(username, password);
        if (admin != null) {
            System.out.println(" Login successful. Welcome, " + admin.getUsername() + "!");
            return admin;
        } else {
            System.out.println(" Invalid credentials.");
            return null;
        }
    }

    public void addTrain() {
        System.out.print("Enter train name: ");
        String name = scanner.nextLine();
        System.out.print("Enter source: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination: ");
        String destination = scanner.nextLine();
        System.out.print("Enter total seats: ");
        int totalSeats = Integer.parseInt(scanner.nextLine());

        Train train = new Train(name, source, destination, totalSeats);
        boolean success = trainDAO.addTrain(train);
        System.out.println(success ? " Train added successfully!" : "❌ Failed to add train.");
    }

    public void viewAllTrains() {
        List<Train> trains = trainDAO.getAllTrains();
        if (trains.isEmpty()) {
            System.out.println(" No trains available.");
        } else {
            trains.forEach(System.out::println);
        }
    }

    public void viewAllPassengers() {
        List<Passenger> passengers = passengerDAO.getAllPassengers();
        if (passengers.isEmpty()) {
            System.out.println(" No passengers found.");
        } else {
            passengers.forEach(System.out::println);
        }
    }

    public void viewAllBookings() {
        List<Booking> bookings = bookingDAO.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println(" No bookings available.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    public void deleteTrain() {
        System.out.print("Enter train ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean success = trainDAO.deleteTrain(id);
        System.out.println(success ? " Train deleted." : "❌ Train not found.");
    }

    public void deletePassenger() {
        System.out.print("Enter passenger ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        boolean success = passengerDAO.deletePassenger(id);
        System.out.println(success ? " Passenger deleted." : "❌ Passenger not found.");
    }

    public void updateAdminPassword(int adminId) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        boolean success = adminDAO.updatePassword(adminId, newPassword);
        System.out.println(success ? " Password updated." : "❌ Failed to update password.");
    }

    public boolean deleteAdmin(int adminId) {
        System.out.print("Are you sure you want to delete your admin account? (yes/no): ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("yes")) {
            return adminDAO.deleteAdmin(adminId);
        } else {
            System.out.println(" Operation cancelled.");
            return false;
        }
    }

    // === PASSENGER SECTION ===

    public void registerPassenger() {
        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        Passenger passenger = new Passenger(name, age, gender, phone);
        boolean success = passengerDAO.addPassenger(passenger);
        System.out.println(success ? " Passenger registered successfully!" : "❌ Failed to register passenger.");
    }

    public Passenger passengerLogin() {
        System.out.print("Enter passenger ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Passenger passenger = passengerDAO.getPassengerById(id);
        if (passenger != null) {
            System.out.println(" Welcome, " + passenger.getName() + "!");
            return passenger;
        } else {
            System.out.println(" Passenger not found.");
            return null;
        }
    }

    public void bookTicketAsPassenger(int passengerId) {
    viewAllTrains();
    System.out.print("Enter train ID: ");
    int trainId = Integer.parseInt(scanner.nextLine());

    System.out.print("Enter travel date (yyyy-mm-dd): ");
    Date travelDate = Date.valueOf(scanner.nextLine());

 
    List<Booking> existingBookings = bookingDAO.getBookedSeatsByTrainAndDate(trainId, travelDate);

    List<String> bookedSeats = new ArrayList<>();
    for (Booking b : existingBookings) {
        bookedSeats.add(b.getCoach() + "-" + b.getSeatNumber());
    }

    Random rand = new Random();
    String coach;
    int seatNumber;
    String assigned;

    int attempts = 0;
    do {
        int coachNum = rand.nextInt(10) + 1; 
        coach = "S" + coachNum;
        seatNumber = rand.nextInt(60) + 1; 
        assigned = coach + "-" + seatNumber;
        attempts++;
        if (attempts > 600) {
            System.out.println(" All seats are booked for this train on selected date.");
            return;
        }
    } while (bookedSeats.contains(assigned));

    // Final booking
    Date bookingDate = new Date(System.currentTimeMillis());
    Booking booking = new Booking(passengerId, trainId, seatNumber, coach, bookingDate,  travelDate);
    boolean success = bookingDAO.createBooking(booking);

    if (success) {
        System.out.println(" Ticket booked successfully!");
        System.out.println(" Assigned Coach: " + coach + ", Seat No: " + seatNumber);
    } else {
        System.out.println(" Booking failed.");
    }
}


    public void viewBookingsByPassenger(int passengerId) {
        List<Booking> bookings = bookingDAO.getBookingsByPassengerId(passengerId);
        if (bookings.isEmpty()) {
            System.out.println(" No bookings found for this passenger.");
        } else {
            bookings.forEach(System.out::println);
        }
    }

    public void cancelBookingAsPassenger(int passengerId) {
        List<Booking> bookings = bookingDAO.getBookingsByPassengerId(passengerId);
        if (bookings.isEmpty()) {
            System.out.println(" No bookings found.");
            return;
        }
        bookings.forEach(System.out::println);
        System.out.print("Enter booking ID to cancel: ");
        int bookingId = Integer.parseInt(scanner.nextLine());
        boolean success = bookingDAO.cancelBooking(bookingId);
        System.out.println(success ? " Booking cancelled." : " Failed to cancel.");
    }

    public void closeScanner() {
        scanner.close();
    }
}
