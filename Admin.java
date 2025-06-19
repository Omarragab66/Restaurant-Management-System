package PL2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class Admin {
    public void LoginAdmin(Scanner input) {
        String correctUsername = "Mark";
        int correctPassword = 11545454;

        while (true) {
            String enteredUsername = InputValidator.getValidString(input, "Enter your name: ");
            int enteredPassword = InputValidator.getValidInt(input, "Enter your password: ");

            if (enteredUsername.equals(correctUsername) && enteredPassword == correctPassword) {
                System.out.println("Login successful");
                break;
            } else {
                System.out.println("Incorrect username or password, please try again");
            }
        }
    }

    public void UpdatePasswordAdmin(Scanner input) {
        int correctPassword = 11545454;
        int enteredPassword;
        while (true) {
            enteredPassword = InputValidator.getValidInt(input, "Enter your current password: ");
            if (enteredPassword == correctPassword) {
                break;
            } else {
                System.out.println("Incorrect password try again");
            }
        }
        int newPassword = InputValidator.getValidInt(input, "Enter your new password: ");
        correctPassword = newPassword;
        System.out.println("Password updated successfully");
    }

    public static class ManageEmployees {
        static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

        public static void AddEmployee(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int id = InputValidator.getValidInt(scanner, "Enter Employee ID: ");
                scanner.nextLine();
                String userName = InputValidator.getValidString(scanner, "Enter Employee username: ");
                int password = InputValidator.getValidInt(scanner, "Enter Employee password: ");
                int salary = InputValidator.getValidInt(scanner, "Enter Employee salary: ");

                String sql = "INSERT INTO Employee (ID, userName, password, salary) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, id);
                    pstmt.setString(2, userName);
                    pstmt.setInt(3, password);
                    pstmt.setInt(4, salary);
                    pstmt.executeUpdate();
                    System.out.println("Employee added successfully.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void DeleteEmployee(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                while (true) {
                    int id = InputValidator.getValidInt(scanner, "Enter Employee ID to delete: ");
                    String checkSql = "SELECT * FROM Employee WHERE ID = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                        checkStmt.setInt(1, id);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next()) {
                            String deleteSql = "DELETE FROM Employee WHERE ID = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                deleteStmt.setInt(1, id);
                                deleteStmt.executeUpdate();
                                System.out.println("Employee deleted successfully.");
                                break;
                            }
                        } else {
                            System.out.println("Employee ID not found. Try again.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void UpdateEmployee(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                while (true) {
                    int id = InputValidator.getValidInt(scanner, "Enter Employee ID to update salary: ");
                    String checkSql = "SELECT * FROM Employee WHERE ID = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                        checkStmt.setInt(1, id);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next()) {
                            int newSalary = InputValidator.getValidInt(scanner, "Enter new salary: ");
                            String updateSql = "UPDATE Employee SET salary = ? WHERE ID = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                updateStmt.setInt(1, newSalary);
                                updateStmt.setInt(2, id);
                                updateStmt.executeUpdate();
                                System.out.println("Salary updated successfully.");
                                break;
                            }
                        } else {
                            System.out.println("Employee ID not found. Try again.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void ListEmployee() {
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {
                System.out.println("Employee List:");
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("ID") +
                            ", Name: " + rs.getString("userName") +
                            ", Password: " + rs.getInt("password") +
                            ", Salary: " + rs.getInt("salary"));
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void SearchEmployee(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                while (true) {
                    int id = InputValidator.getValidInt(scanner, "Enter Employee ID to search: ");
                    String sql = "SELECT * FROM Employee WHERE ID = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, id);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            System.out.println("Employee found:");
                            System.out.println("ID: " + rs.getInt("ID"));
                            System.out.println("Name: " + rs.getString("userName"));
                            System.out.println("Password: " + rs.getInt("password"));
                            System.out.println("Salary: " + rs.getInt("salary"));
                            break;
                        } else {
                            System.out.println("Employee ID not found. Try again.");
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    public static class ManageMeals {
        static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

        public static void AddMeal(Scanner input) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int number = InputValidator.getValidInt(input, "Enter the number of the meal: ");
                input.nextLine();
                String name = InputValidator.getValidString(input, "Enter the name of the meal: ");
                int price = InputValidator.getValidInt(input, "Enter the price of the meal: ");

                String sql = "INSERT INTO Meals (Number, Name, Price) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, number);
                pstmt.setString(2, name);
                pstmt.setInt(3, price);
                pstmt.executeUpdate();

                System.out.println("The meal is added successfully.");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void DeleteMeal(Scanner input) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int number = InputValidator.getValidInt(input, "Enter the number of the meal that you want to delete: ");
                String sql = "DELETE FROM Meals WHERE Number = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, number);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Meal deleted successfully.");
                } else {
                    System.out.println("The meal was not found.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void UpdateMeal(Scanner input) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int number = InputValidator.getValidInt(input, "Enter the meal number that you want to update its price: ");
                int newPrice = InputValidator.getValidInt(input, "Enter the new price of the meal: ");

                String sql = "UPDATE Meals SET Price = ? WHERE Number = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, newPrice);
                pstmt.setInt(2, number);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("The meal's price is updated successfully.");
                } else {
                    System.out.println("The meal was not found.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void listMeals() {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM Meals";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                System.out.println("Number\tName\t\tPrice");
                System.out.println("-----------------------------------------");
                while (rs.next()) {
                    System.out.printf("%d\t%s\t\t%d%n", rs.getInt("Number"), rs.getString("Name"), rs.getInt("Price"));
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void SearchMeal(Scanner input) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                input.nextLine();
                String name = InputValidator.getValidString(input, "Enter the name of the meal you want to search: ");
                String sql = "SELECT * FROM Meals WHERE Name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Meal found:");
                    System.out.println("Number: " + rs.getInt("Number"));
                    System.out.println("Price: " + rs.getInt("Price"));
                } else {
                    System.out.println("The meal was not found.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    public void MakeReports(Scanner input) {
        System.out.println("Choose Report Type:");
        System.out.println("1 - Employees");
        System.out.println("2 - Customers");
        int choice = InputValidator.getValidInt(input, "Enter choice: ");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            if (choice == 1) {
                String countEmpSql = "SELECT COUNT(*) AS Total FROM Employee";
                PreparedStatement countEmpStmt = connection.prepareStatement(countEmpSql);
                ResultSet empCountResult = countEmpStmt.executeQuery();
                int totalEmployees = empCountResult.getInt("Total");
                System.out.println("Total Employees: " + totalEmployees);
                int totalHours = totalEmployees * 8;
                System.out.println("Total Working Hours: " + totalHours);
                String avgSalarySql = "SELECT AVG(Salary) AS AvgSalary FROM Employee";
                PreparedStatement avgSalaryStmt = connection.prepareStatement(avgSalarySql);
                ResultSet avgSalaryResult = avgSalaryStmt.executeQuery();
                double avgSalary = avgSalaryResult.getDouble("AvgSalary");
                System.out.println("Average Salary: " + avgSalary);
            } else if (choice == 2) {
                String countCustSql = "SELECT COUNT(*) AS Total FROM Customers";
                PreparedStatement countCustStmt = connection.prepareStatement(countCustSql);
                ResultSet custCountResult = countCustStmt.executeQuery();
                int totalCustomers = custCountResult.getInt("Total");
                System.out.println("Total Customers: " + totalCustomers);
                String sumOrdersSql = "SELECT SUM(NumOfOrders) AS TotalOrders FROM OrdersAndBills";
                PreparedStatement sumOrdersStmt = connection.prepareStatement(sumOrdersSql);
                ResultSet ordersResult = sumOrdersStmt.executeQuery();
                int totalOrders = ordersResult.getInt("TotalOrders");
                System.out.println("Total Orders: " + totalOrders);
                String sumPaymentsSql = "SELECT SUM(Payments) AS TotalPayments FROM OrdersAndBills";
                PreparedStatement sumPaymentsStmt = connection.prepareStatement(sumPaymentsSql);
                ResultSet paymentsResult = sumPaymentsStmt.executeQuery();
                int totalPayments = paymentsResult.getInt("TotalPayments");
                System.out.println("Total Payments: " + totalPayments);
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " +  e.getMessage());
        }
    }

    public void GiveGiftAndOffer(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db")) {
            String eligibleSql = "SELECT IDOfCustomer FROM OrdersAndBills WHERE Payments >= 500";
            PreparedStatement eligibleStmt = conn.prepareStatement(eligibleSql);
            ResultSet eligibleResult = eligibleStmt.executeQuery();
            boolean hasEligibleCustomers = false;

            conn.setAutoCommit(false); // Start transaction
            try {
                while (eligibleResult.next()) {
                    int customerId = eligibleResult.getInt("IDOfCustomer");
                    String checkSql = "SELECT * FROM OffersAndGifts WHERE IDOfCustomer = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setInt(1, customerId);
                    ResultSet checkResult = checkStmt.executeQuery();

                    if (!checkResult.next()) {
                        hasEligibleCustomers = true;
                        System.out.println("Customer ID " + customerId + " is eligible for a gift/offer.");
                        String giftOrOffer = InputValidator.getValidString(scanner, "Enter the offer or gift for Customer ID " + customerId + ": ");
                        String insertSql = "INSERT INTO OffersAndGifts (IDOfCustomer, OfferOrGift)VALUES(?,?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                        insertStmt.setInt(1, customerId);
                        insertStmt.setString(2, giftOrOffer);
                        insertStmt.executeUpdate();
                        System.out.println("Gift/Offer added for Customer ID " + customerId);
                    }
                }

                if (!hasEligibleCustomers) {
                    System.out.println("There are no customers eligible to receive an offer or gift.");
                }

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                System.out.println("Error processing gifts/offers: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // Restore auto-commit
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static class AvailablePrograms {
        private static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

        public static void AddProgram(Scanner input) {
            int programNumber = InputValidator.getValidInt(input, "Enter a number for the program: ");
            input.nextLine();
            String programName = InputValidator.getValidString(input, "Enter the program name: ");
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                String insertSql = "INSERT INTO Programs (ProgramNumber, Program) VALUES (?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, programNumber);
                insertStmt.setString(2, programName);
                insertStmt.executeUpdate();
                System.out.println("Program added successfully!");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }

        public static void DeleteProgram(Scanner input) {
            while (true) {
                int programNumber = InputValidator.getValidInt(input, "Enter the number of the program that you want to delete: ");
                try (Connection connection = DriverManager.getConnection(DB_URL)) {
                    String checkSql = "SELECT * FROM Programs WHERE ProgramNumber = ?";
                    PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                    checkStmt.setInt(1, programNumber);
                    ResultSet result = checkStmt.executeQuery();
                    if (result.next()) {
                        String deleteSql = "DELETE FROM Programs WHERE ProgramNumber = ?";
                        PreparedStatement deleteStmt = connection.prepareStatement(deleteSql);
                        deleteStmt.setInt(1, programNumber);
                        deleteStmt.executeUpdate();
                        System.out.println("Program deleted successfully!");
                        break;
                    } else {
                        System.out.println("Program not found. Please enter a valid program number.");
                    }
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                    break;
                }
            }
        }
    }
}