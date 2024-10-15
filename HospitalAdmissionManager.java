import java.sql.*;
import java.util.Scanner;

public class HospitalAdmissionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Varsha@123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add Admission");
            System.out.println("2. Modify Admission");
            System.out.println("3. Delete Admission");
            System.out.println("4. Search Admission");
            System.out.println("5. View All Admissions");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addAdmission(scanner);
                    break;
                case 2:
                    modifyAdmission(scanner);
                    break;
                case 3:
                    deleteAdmission(scanner);
                    break;
                case 4:
                    searchAdmission(scanner);
                    break;
                case 5:
                    viewAllAdmissions();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please choose again.");
            }
        } while (choice != 6);
        scanner.close();
    }

    // Method to add a new admission
    private static void addAdmission(Scanner scanner) {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter admission date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();

        String insertSQL = "INSERT INTO Admissions (PatientName, AdmissionDate, Diagnosis, RoomNumber) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, diagnosis);
            pstmt.setInt(4, roomNumber);
            pstmt.executeUpdate();
            System.out.println("Admission added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to modify an existing admission
    private static void modifyAdmission(Scanner scanner) {
        System.out.print("Enter the patient name to modify: ");
        String name = scanner.nextLine();
        System.out.print("Enter new admission date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter new diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter new room number: ");
        int roomNumber = scanner.nextInt();

        String updateSQL = "UPDATE Admissions SET AdmissionDate = ?, Diagnosis = ?, RoomNumber = ? WHERE PatientName = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
            pstmt.setString(1, date);
            pstmt.setString(2, diagnosis);
            pstmt.setInt(3, roomNumber);
            pstmt.setString(4, name);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admission modified successfully!");
            } else {
                System.out.println("Admission not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an admission
    private static void deleteAdmission(Scanner scanner) {
        System.out.print("Enter the patient name to delete: ");
        String name = scanner.nextLine();

        String deleteSQL = "DELETE FROM Admissions WHERE PatientName = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(deleteSQL)) {
            pstmt.setString(1, name);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Admission deleted successfully!");
            } else {
                System.out.println("Admission not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for an admission
    private static void searchAdmission(Scanner scanner) {
        System.out.print("Enter the patient name to search: ");
        String name = scanner.nextLine();

        String searchSQL = "SELECT * FROM Admissions WHERE PatientName = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(searchSQL)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.printf("Name: %s, Admission Date: %s, Diagnosis: %s, Room Number: %d%n",
                        rs.getString("PatientName"),
                        rs.getString("AdmissionDate"),
                        rs.getString("Diagnosis"),
                        rs.getInt("RoomNumber"));
            } else {
                System.out.println("Admission not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to view all admissions
    private static void viewAllAdmissions() {
        String selectSQL = "SELECT * FROM Admissions";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            System.out.printf("%-20s %-15s %-20s %-10s%n", "Patient Name", "Admission Date", "Diagnosis", "Room Number");
            System.out.println("-------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-20s %-15s %-20s %-10d%n",
                        rs.getString("PatientName"),
                        rs.getString("AdmissionDate"),
                        rs.getString("Diagnosis"),
                        rs.getInt("RoomNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
