package PL2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employee {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

    public boolean LoginEmployee(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            int id = InputValidator.getValidInt(scanner, "Enter your ID: ");
            scanner.nextLine();
            String username = InputValidator.getValidString(scanner, "Enter your username: ");
            int password = InputValidator.getValidInt(scanner, "Enter your password: ");

            String sql = "SELECT * FROM Employee WHERE ID = ? AND userName = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, username);
            pstmt.setInt(3, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Login failed.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void UpdatePasswordEmployee(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            int id = InputValidator.getValidInt(scanner, "Enter your ID: ");
            scanner.nextLine();
            int newPassword = InputValidator.getValidInt(scanner, "Enter your new password: ");

            String sql = "UPDATE Employee SET Password = ? WHERE ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newPassword);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Employee ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }

    public static class ManageCustomers {
        private static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

        public static void AddCustomer(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int id = InputValidator.getValidInt(scanner, "Enter the customer ID: ");
                scanner.nextLine();
                String username = InputValidator.getValidString(scanner, "Enter the username of the customer: ");
                int password = InputValidator.getValidInt(scanner, "Enter the customer password: ");

                String sql = "INSERT INTO Customers (IDOfCustomer, UserNameCustomer, PasswordCustomer) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, username);
                pstmt.setInt(3, password);
                pstmt.executeUpdate();

                System.out.println("Customer added successfully.");
            } catch (SQLException e) {
                System.out.println("Error adding customer: " + e.getMessage());
            }
        }

        public static void DeleteCustomer(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int id = InputValidator.getValidInt(scanner, "Enter the ID of the Customer that you want to delete: ");
                String sql = "DELETE FROM Customers WHERE IDOfCustomer = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Customer deleted successfully.");
                } else {
                    System.out.println("Customer ID not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting customer: " + e.getMessage());
            }
        }

        public static void UpdateCustomer(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int id = InputValidator.getValidInt(scanner, "Enter the ID of the customer: ");
                scanner.nextLine();
                int newPassword = InputValidator.getValidInt(scanner, "Enter the new password: ");

                String sql = "UPDATE Customers SET PasswordCustomer = ? WHERE IDOfCustomer = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, newPassword);
                pstmt.setInt(2, id);

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Customer password updated successfully.");
                } else {
                    System.out.println("Customer ID not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error updating password: " + e.getMessage());
            }
        }

        public static void ListCustomers() {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                String sql = "SELECT * FROM Customers";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("IDOfCustomer") +
                                       ", Username: " + rs.getString("UserNameCustomer") +
                                       ", Password: " + rs.getInt("PasswordCustomer"));
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving customers: " + e.getMessage());
            }
        }

        public static void SearchCustomer(Scanner scanner) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int id = InputValidator.getValidInt(scanner, "Enter the ID of the customer to search: ");
                String sql = "SELECT * FROM Customers WHERE IDOfCustomer = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("IDOfCustomer") +
                                       ", Username: " + rs.getString("UserNameCustomer") +
                                       ", Password: " + rs.getInt("PasswordCustomer"));
                } else {
                    System.out.println("Customer not found.");
                }
            } catch (SQLException e) {
                System.out.println("Error searching customer: " + e.getMessage());
            }
        }
    }

    public static class RecordOrdersAndBills {
        private static final String DB_URL = "jdbc:sqlite:src/main/resources/sqlite_files/TABLE_EMPLOYEE.db";

        public static void MakeOrder(Scanner input) {

        	    int customerId = InputValidator.getValidInt(input, "Enter the ID of the customer: ");
        	    try (Connection connection = DriverManager.getConnection(DB_URL)) {
        	        String checkCustomerSql = "SELECT * FROM Customers WHERE IDOfCustomer = ?";
        	        PreparedStatement checkCustomerStmt = connection.prepareStatement(checkCustomerSql);
        	        checkCustomerStmt.setInt(1, customerId);
        	        ResultSet customerResult = checkCustomerStmt.executeQuery();

        	        if (!customerResult.next()) {
        	            System.out.println("Customer not found");
        	            return;
        	        }

        	        String selectMealsSql = "SELECT * FROM Meals";
        	        PreparedStatement selectMealsStmt = connection.prepareStatement(selectMealsSql);
        	        ResultSet mealsResult = selectMealsStmt.executeQuery();

        	        System.out.println("Available Meals:");
        	        while (mealsResult.next()) {
        	            System.out.println("Number: " + mealsResult.getInt("Number") +
        	                               " | Name: " + mealsResult.getString("Name") +
        	                               " | Price: " + mealsResult.getInt("Price")); // Fixed: Changed rs to mealsResult
        	        }

        	        int mealCount = InputValidator.getValidInt(input, "How many meals do you want to order? ");
        	        int totalPrice = 0;

        	        for (int i = 0; i < mealCount; i++) {
        	            int mealNumber = InputValidator.getValidInt(input, "Please enter the number of the meal you want: ");
        	            String mealPriceSql = "SELECT Price FROM Meals WHERE Number = ?";
        	            PreparedStatement mealPriceStmt = connection.prepareStatement(mealPriceSql);
        	            mealPriceStmt.setInt(1, mealNumber);
        	            ResultSet priceResult = mealPriceStmt.executeQuery();

        	            if (priceResult.next()) {
        	                int price = priceResult.getInt("Price");
        	                totalPrice += price;
        	            } else {
        	                System.out.println("Meal not found. Skipping.");
        	            }
        	        }

        	        String checkOrderSql = "SELECT * FROM OrdersAndBills WHERE IDOfCustomer = ?";
        	        PreparedStatement checkOrderStmt = connection.prepareStatement(checkOrderSql);
        	        checkOrderStmt.setInt(1, customerId);
        	        ResultSet orderResult = checkOrderStmt.executeQuery();

        	        if (orderResult.next()) {
        	            int existingOrders = orderResult.getInt("NumOfOrders");
        	            int existingPayments = orderResult.getInt("Payments");
        	            String updateSql = "UPDATE OrdersAndBills SET NumOfOrders = ?, Payments = ? WHERE IDOfCustomer = ?";
        	            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
        	            updateStmt.setInt(1, existingOrders + 1);
        	            updateStmt.setInt(2, existingPayments + totalPrice);
        	            updateStmt.setInt(3, customerId);
        	            updateStmt.executeUpdate();
        	            System.out.println("Orders updated successfully!");
        	        } else {
        	            String insertSql = "INSERT INTO OrdersAndBills (IDOfCustomer, NumOfOrders, Payments) VALUES (?, ?, ?)";
        	            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
        	            insertStmt.setInt(1, customerId);
        	            insertStmt.setInt(2, 1);
        	            insertStmt.setInt(3, totalPrice);
        	            insertStmt.executeUpdate();
        	            System.out.println("Order added successfully!");
        	        }
        	    } catch (SQLException e) {
        	        System.out.println("Database error: " + e.getMessage());
        	    }
        	}

        public static void CancelOrder(Scanner input) {
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                int customerId = InputValidator.getValidInt(input, "Enter the ID of the customer to cancel the order: ");
                String checkSql = "SELECT NumOfOrders, Payments FROM OrdersAndBills WHERE IDOfCustomer = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, customerId);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    System.out.println("No orders found for this customer");
                    return;
                }

                int currentOrders = rs.getInt("NumOfOrders");
                int currentPayments = rs.getInt("Payments");

                if (currentOrders == 0) {
                    System.out.println("This customer has no orders to cancel");
                    return;
                }

                int lastOrderPrice = InputValidator.getValidInt(input, "Enter the total price of the last order to cancel: ");
                int updatedOrders = currentOrders - 1;
                int updatedPayments = currentPayments - lastOrderPrice;

                String updateSql = "UPDATE OrdersAndBills SET NumOfOrders = ?, Payments = ? WHERE IDOfCustomer = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, updatedOrders);
                updateStmt.setInt(2, updatedPayments);
                updateStmt.setInt(3, customerId);
                updateStmt.executeUpdate();

                System.out.println("The order is cancelled");
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }
}