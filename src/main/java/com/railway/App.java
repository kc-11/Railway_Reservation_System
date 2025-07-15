package com.railway;

import com.railway.model.Admin;
import com.railway.model.Passenger;
import com.railway.service.RailwayService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        RailwayService service = new RailwayService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Welcome to Railway Reservation System ====");

        while (true) {
            System.out.println("\n--- Choose Role ---");
            System.out.println("1. Admin");
            System.out.println("2. Passenger");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    handleAdminFlow(service, scanner);
                    break;
                case "2":
                    handlePassengerFlow(service, scanner);
                    break;
                case "3":
                    System.out.println(" Exiting the system. Goodbye!");
                    service.closeScanner();
                    scanner.close();
                    return;
                default:
                    System.out.println(" Invalid choice. Please try again.");
            }
        }
    }

    private static void handleAdminFlow(RailwayService service, Scanner scanner) {
        Admin admin = null;
        while (admin == null) {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. Register as Admin");
            System.out.println("2. Login as Admin");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.print("Enter new admin username: ");
                String username = scanner.nextLine();
                System.out.print("Enter new admin password: ");
                String password = scanner.nextLine();
                admin = new Admin(username, password);
                boolean success = service.registerAdmin(admin);
                if (success) {
                    System.out.println(" Admin registered. Please login to continue.");
                    admin = null;
                } else {
                    System.out.println("❌ Registration failed.");
                }
            } else if (input.equals("2")) {
                admin = service.adminLogin();
            } else {
                System.out.println(" Invalid input.");
            }
        }

        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add New Train");
            System.out.println("2. View All Trains");
            System.out.println("3. View All Passengers");
            System.out.println("4. View All Bookings");
            System.out.println("5. Delete Passenger");
            System.out.println("6. Delete Train");
            System.out.println("7. Update My Password");
            System.out.println("8. Delete My Admin Account");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number input.");
                continue;
            }

            switch (choice) {
                case 1:
                    service.addTrain();
                    break;
                case 2:
                    service.viewAllTrains();
                    break;
                case 3:
                    service.viewAllPassengers();
                    break;
                case 4:
                    service.viewAllBookings();
                    break;
                case 5:
                    service.deletePassenger();
                    break;
                case 6:
                    service.deleteTrain();
                    break;
                case 7:
                    service.updateAdminPassword(admin.getId());
                    break;
                case 8:
                    if (service.deleteAdmin(admin.getId())) {
                        System.out.println(" Admin account deleted.");
                        return;
                    } else {
                        System.out.println(" Failed to delete admin account.");
                    }
                    break;
                case 9:
                    System.out.println(" Logged out.");
                    return;
                default:
                    System.out.println(" Invalid choice.");
            }
        } while (true);
    }

    private static void handlePassengerFlow(RailwayService service, Scanner scanner) {
        Passenger passenger = null;
        while (passenger == null) {
            System.out.println("\n--- Passenger Panel ---");
            System.out.println("1. Register as Passenger");
            System.out.println("2. Login as Passenger");
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                service.registerPassenger();
                System.out.println(" Now login using your passenger ID.");
            } else if (input.equals("2")) {
                passenger = service.passengerLogin();
            } else {
                System.out.println(" Invalid input.");
            }
        }

        int choice;
        do {
            System.out.println("\n--- Passenger Menu ---");
            System.out.println("1. View All Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid number input.");
                continue;
            }

            switch (choice) {
                case 1:
                    service.viewAllTrains();
                    break;
                case 2:
                    service.bookTicketAsPassenger(passenger.getId());
                    break;
                case 3:
                    service.viewBookingsByPassenger(passenger.getId());
                    break;
                case 4:
                    service.cancelBookingAsPassenger(passenger.getId());
                    break;
                case 5:
                    System.out.println(" Logged out.");
                    return;
                default:
                    System.out.println(" Invalid choice.");
            }
        } while (true);
    }
}
