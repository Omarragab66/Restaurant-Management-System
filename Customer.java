package PL2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class Customer {
    public void LoginCustomer(Scanner input) {
        while (true) {
            int id = InputValidator.getValidInt(input, "Enter your ID: ");
            input.nextLine();
            String username = InputValidator.getValidString(input, "Enter your username: ");
            int password = InputValidator.getValidInt(input, "Enter your password: ");
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
                String sql = "SELECT * FROM Customers WHERE IDOfCustomer = ? AND UserNameCustomer = ? AND PasswordCustomer = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.setString(2, username);
                stmt.setInt(3, password);
                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    System.out.println("Login successful");
                    break;
                } else {
                    System.out.println("Incorrect ID, username, password. Please try again");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                break;
            }
        }
    }

    public void UpdatePasswordCustomer(Scanner input) {
        int id = InputValidator.getValidInt(input, "Enter your ID: ");
        input.nextLine();
        String username = InputValidator.getValidString(input, "Enter your username: ");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            boolean valid = false;
            int currentPassword;
            while (!valid) {
                currentPassword = InputValidator.getValidInt(input, "Enter your current password: ");
                String checkSql = "SELECT * FROM Customers WHERE IDOfCustomer = ? AND UserNameCustomer = ? AND PasswordCustomer = ?";
                PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setInt(1, id);
                checkStmt.setString(2, username);
                checkStmt.setInt(3, currentPassword);
                ResultSet result = checkStmt.executeQuery();
                if (result.next()) {
                    valid = true;
                } else {
                    System.out.println("Incorrect password. Please try again.");
                }
            }
            int newPassword = InputValidator.getValidInt(input, "Enter your new password: ");
            String updateSql = "UPDATE Customers SET PasswordCustomer = ? WHERE IDOfCustomer = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, newPassword);
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
            System.out.println("Password updated successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void CheckIfHasOffer(Scanner input) {
        int customerId = InputValidator.getValidInt(input, "Enter your ID: ");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            String checkSql = "SELECT OfferOrGift FROM OffersAndGifts WHERE IDOfCustomer = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, customerId);
            ResultSet result = checkStmt.executeQuery();
            if (result.next()) {
                String offer = result.getString("OfferOrGift");
                System.out.println("You have an offer/gift: " + offer);
            } else {
                System.out.println("Sorry, you don't have any offers.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void RegisterInProgram(Scanner scanner) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            String selectProgramsSQL = "SELECT * FROM Programs";
            PreparedStatement selectProgramsStmt = connection.prepareStatement(selectProgramsSQL);
            ResultSet programsResult = selectProgramsStmt.executeQuery();
            System.out.println("Available Programs:");
            while (programsResult.next()) {
                int programNumber = programsResult.getInt("ProgramNumber");
                String programName = programsResult.getString("Program");
                System.out.println(programNumber + ": " + programName);
            }
            int customerID;
            while (true) {
                customerID = InputValidator.getValidInt(scanner, "Enter your Customer ID: ");
                String checkIDSQL = "SELECT * FROM Customers WHERE IDOfCustomer = ?";
                PreparedStatement checkIDStmt = connection.prepareStatement(checkIDSQL);
                checkIDStmt.setInt(1, customerID);
                ResultSet idResult = checkIDStmt.executeQuery();
                if (idResult.next()) {
                    break;
                } else {
                    System.out.println("Invalid ID. Try again.");
                }
            }
            int chosenProgramNumber;
            while (true) {
                chosenProgramNumber = InputValidator.getValidInt(scanner, "Enter Program Number you want to register in: ");
                String checkProgramSQL = "SELECT * FROM Programs WHERE ProgramNumber = ?";
                PreparedStatement checkProgramStmt = connection.prepareStatement(checkProgramSQL);
                checkProgramStmt.setInt(1, chosenProgramNumber);
                ResultSet checkProgramResult = checkProgramStmt.executeQuery();
                if (checkProgramResult.next()) {
                    break;
                } else {
                    System.out.println("Error: Program not found. Try again.");
                }
            }
            String insertSQL = "INSERT INTO ChosenProgram (IDOfCustomer, ProgramNumber) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            insertStmt.setInt(1, customerID);
            insertStmt.setInt(2, chosenProgramNumber);
            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Program registered successfully!");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void ShowEnrolledProgram(Scanner input) {
        int customerID = InputValidator.getValidInt(input, "Enter your ID: ");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT ProgramNumber FROM ChosenProgram WHERE IDOfCustomer = ?");
            checkStmt.setInt(1, customerID);
            ResultSet chosenResult = checkStmt.executeQuery();
            if (chosenResult.next()) {
                int programNumber = chosenResult.getInt("ProgramNumber");
                PreparedStatement programStmt = conn.prepareStatement("SELECT Program FROM Programs WHERE ProgramNumber = ?");
                programStmt.setInt(1, programNumber);
                ResultSet programResult = programStmt.executeQuery();
                if (programResult.next()) {
                    String programName = programResult.getString("Program");
                    System.out.println("You are enrolled in the following program: " + programName);
                } else {
                    System.out.println("Sorry, the program name could not be found.");
                }
            } else {
                System.out.println("You are not enrolled in any program.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving data: " + e.getMessage());
        }
    }

    public void ShowPayments(Scanner input) {
        int customerID = InputValidator.getValidInt(input, "Enter your ID: ");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT Payments FROM OrdersAndBills WHERE IDOfCustomer = ?");
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int payments = rs.getInt("Payments");
                System.out.println("Your total payments: " + payments + " EGP.");
            } else {
                System.out.println("You haven't made any orders yet.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving payments: " + e.getMessage());
        }
    }
}