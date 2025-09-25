import java.io.*;
import java.util.*;

/**
 * EmployeeManagement.java
 * Single-file menu-driven Employee app:
 * 1. Add Employee
 * 2. Display All
 * 3. Exit
 *
 * Employee records are stored as simple CSV lines in "employees.txt".
 */
public class EmployeeManagement {
    private static final String FILE_NAME = "employees.txt";
    private static final Scanner sc = new Scanner(System.in);

    // Simple Employee model as a private static nested class
    private static class Employee {
        String name;
        String id;
        String designation;
        double salary;

        Employee(String name, String id, String designation, double salary) {
            this.name = name;
            this.id = id;
            this.designation = designation;
            this.salary = salary;
        }

        // Convert to CSV line for storage (simple escaping of pipe char)
        String toCSV() {
            return escape(name) + "," + escape(id) + "," + escape(designation) + "," + salary;
        }

        // Parse CSV line back to Employee (assumes fields do not contain commas)
        static Employee fromCSV(String line) {
            String[] parts = line.split(",", 4);
            if (parts.length < 4) return null;
            String name = unescape(parts[0]);
            String id = unescape(parts[1]);
            String designation = unescape(parts[2]);
            double salary;
            try {
                salary = Double.parseDouble(parts[3]);
            } catch (NumberFormatException e) {
                salary = 0.0;
            }
            return new Employee(name, id, designation, salary);
        }

        @Override
        public String toString() {
            return String.format("%s | %s | %s | %.2f", name, id, designation, salary);
        }

        // Minimal escaping/unescaping helpers
        private static String escape(String s) {
            return s.replace("|", "\\|").replace("\n", " ").trim();
        }

        private static String unescape(String s) {
            return s.replace("\\|", "|");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choiceLine = sc.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2 or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Please enter a valid option (1-3).");
            }
        }
    }

    private static void addEmployee() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty. Aborting add.");
            return;
        }

        System.out.print("ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Designation: ");
        String designation = sc.nextLine().trim();

        double salary = 0.0;
        while (true) {
            System.out.print("Salary: ");
            String salaryStr = sc.nextLine().trim();
            try {
                salary = Double.parseDouble(salaryStr);
                if (salary < 0) {
                    System.out.println("Salary cannot be negative. Enter again.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary. Please enter a numeric value (e.g., 75000 or 75000.50).");
            }
        }

        Employee emp = new Employee(name, id, designation, salary);

        // Append employee to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(emp.toCSV());
            writer.newLine();
            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void displayEmployees() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No employee records found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean any = false;
            System.out.println("\nEmployee List:");
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Employee e = Employee.fromCSV(line);
                if (e != null) {
                    System.out.println(e.toString());
                    any = true;
                }
            }
            if (!any) {
                System.out.println("No employee records found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading employee file: " + e.getMessage());
        }
    }
}
