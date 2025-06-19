# Restaurant Management System (Java Console-Based)

## Overview

This project is a **Java-based console system** for managing restaurant operations using role-based access. It supports three types of users:
- Admin
- Employee
- Customer

Each role has specific permissions and menu-driven functionality to manage different aspects of the system such as meals, employees, customers, programs, and orders.

## Features

### Admin
- Login authentication
- Update admin password
- Manage Employees: Add, Delete, Update, List, Search
- Manage Meals: Add, Delete, Update, List, Search
- Generate reports and manage offers
- Manage programs

### Employee
- Login authentication
- Update employee password
- Manage Customers: Add, Delete, Update, List, Search
- Record Orders and Bills: Make and cancel orders

### Customer
- Login authentication
- Update customer password
- Check for offers
- Register in a program and view enrolled programs
- View payment history

## Technologies Used

- Java (console-based)
- Scanner class for input
- Object-Oriented Programming (OOP) principles
- Modular class design (Admin, Employee, Customer)
- File handling or JDBC (if persistence is implemented)

## How to Run

1. Ensure Java (version 8 or above) is installed.
2. Place all class files in the `PL2` directory/package.
3. Compile using the command:

   ```bash
   javac PL2/Main.java
