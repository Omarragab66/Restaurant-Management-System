package PL2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        Employee employee = new Employee();
        Customer customer = new Customer();

        while (true) {
            System.out.println("\n=== System Login ===");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int roleChoice = readInt(scanner, "Main menu choice");

            if (roleChoice == 4) {
                System.out.println("Exiting system...");
                break;
            }

            switch (roleChoice) {
                case 1:
                    admin.LoginAdmin(scanner);
                    adminMenu(admin, scanner);
                    break;
                case 2:
                    if (employee.LoginEmployee(scanner)) {
                        employeeMenu(employee, scanner);
                    } else {
                        System.out.println("Returning to main menu due to login failure.");
                    }
                    break;
                case 3:
                    customer.LoginCustomer(scanner);
                    customerMenu(customer, scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.println("[DEBUG] Waiting for input: " + prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("Input cannot be empty. Please enter a number.");
                        continue;
                    }
                    int value = Integer.parseInt(input);
                    System.out.println("[DEBUG] Read value: " + value);
                    return value;
                } else {
                    System.out.println("No input available. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private static void adminMenu(Admin admin, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Update Password");
            System.out.println("2. Manage Employees");
            System.out.println("3. Manage Meals");
            System.out.println("4. Make Report");
            System.out.println("5. Give Gift or Offer");
            System.out.println("6. Programs");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Admin menu choice");

            if (choice == 7) break;

            switch (choice) {
                case 1:
                    admin.UpdatePasswordAdmin(scanner);
                    break;
                case 2:
                    manageEmployeesMenu(scanner);
                    break;
                case 3:
                    manageMealsMenu(scanner);
                    break;
                case 4:
                    admin.MakeReports(scanner);
                    break;
                case 5:
                    admin.GiveGiftAndOffer(scanner);
                    break;
                case 6:
                    manageProgramsMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageEmployeesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Manage Employees ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. List Employees");
            System.out.println("5. Search Employee");
            System.out.println("6. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Manage Employees menu choice");

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    Admin.ManageEmployees.AddEmployee(scanner);
                    break;
                case 2:
                    Admin.ManageEmployees.DeleteEmployee(scanner);
                    break;
                case 3:
                    Admin.ManageEmployees.UpdateEmployee(scanner);
                    break;
                case 4:
                    Admin.ManageEmployees.ListEmployee();
                    break;
                case 5:
                    Admin.ManageEmployees.SearchEmployee(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageMealsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Manage Meals ===");
            System.out.println("1. Add Meal");
            System.out.println("2. Delete Meal");
            System.out.println("3. Update Meal");
            System.out.println("4. List Meals");
            System.out.println("5. Search Meal");
            System.out.println("6. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Manage Meals menu choice");

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    Admin.ManageMeals.AddMeal(scanner);
                    break;
                case 2:
                    Admin.ManageMeals.DeleteMeal(scanner);
                    break;
                case 3:
                    Admin.ManageMeals.UpdateMeal(scanner);
                    break;
                case 4:
                    Admin.ManageMeals.listMeals();
                    break;
                case 5:
                    Admin.ManageMeals.SearchMeal(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageProgramsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Manage Programs ===");
            System.out.println("1. Add Program");
            System.out.println("2. Delete Program");
            System.out.println("3. Back to Admin Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Manage Programs menu choice");

            if (choice == 3) break;

            switch (choice) {
                case 1:
                    Admin.AvailablePrograms.AddProgram(scanner);
                    break;
                case 2:
                    Admin.AvailablePrograms.DeleteProgram(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void employeeMenu(Employee employee, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Update Password");
            System.out.println("2. Manage Customers");
            System.out.println("3. Orders and Bills");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Employee menu choice");

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    employee.UpdatePasswordEmployee(scanner);
                    break;
                case 2:
                    manageCustomersMenu(scanner);
                    break;
                case 3:
                    recordOrdersAndBillsMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageCustomersMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Manage Customers ===");
            System.out.println("1. Add Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. List Customers");
            System.out.println("5. Search Customer");
            System.out.println("6. Back to Employee Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Manage Customers menu choice");

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    Employee.ManageCustomers.AddCustomer(scanner);
                    break;
                case 2:
                    Employee.ManageCustomers.DeleteCustomer(scanner);
                    break;
                case 3:
                    Employee.ManageCustomers.UpdateCustomer(scanner);
                    break;
                case 4:
                    Employee.ManageCustomers.ListCustomers();
                    break;
                case 5:
                    Employee.ManageCustomers.SearchCustomer(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void recordOrdersAndBillsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== Orders and Bills ===");
            System.out.println("1. Make Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Back to Employee Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Orders and Bills menu choice");

            if (choice == 3) break;

            switch (choice) {
                case 1:
                    Employee.RecordOrdersAndBills.MakeOrder(scanner);
                    break;
                case 2:
                    Employee.RecordOrdersAndBills.CancelOrder(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void customerMenu(Customer customer, Scanner scanner) {
        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Update Password");
            System.out.println("2. Check if you have Offer");
            System.out.println("3. Register in Program");
            System.out.println("4. Show Enrolled Program");
            System.out.println("5. Show Payments");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner, "Customer menu choice");

            if (choice == 6) break;

            switch (choice) {
                case 1:
                    customer.UpdatePasswordCustomer(scanner);
                    break;
                case 2:
                    customer.CheckIfHasOffer(scanner);
                    break;
                case 3:
                    customer.RegisterInProgram(scanner);
                    break;
                case 4:
                    customer.ShowEnrolledProgram(scanner);
                    break;
                case 5:
                    customer.ShowPayments(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}