import java.sql.*;
import java.util.Scanner;

public class HospitalAdmissionForm {

    // Database URL, username and password (adjust these as needed)
    static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get patient details from user input
        System.out.println("Enter Patient Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Patient Age:");
        int age = scanner.nextInt();

        System.out.println("Enter Patient Gender (Male/Female):");
        String gender = scanner.next();

        scanner.nextLine(); // Consume the newline

        System.out.println("Enter Patient Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Contact Number:");
        String contactNumber = scanner.nextLine();

        System.out.println("Enter Admission Date (YYYY-MM-DD):");
        String admissionDate = scanner.nextLine();

        // Insert the details into the database
        insertPatientData(name, age, gender, address, contactNumber, admissionDate);

        scanner.close();
    }

    public static void insertPatientData(String name, int age, String gender, String address, String contactNumber, String admissionDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare SQL query
            String sql = "INSERT INTO patients (name, age, gender, address, contact_number, admission_date) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Set the values for the query
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, contactNumber);
            pstmt.setDate(6, Date.valueOf(admissionDate));

            // Execute the query
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Patient admission record inserted successfully.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
