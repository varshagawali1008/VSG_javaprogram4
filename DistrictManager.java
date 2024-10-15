import java.sql.*;
import java.util.Scanner;
public class DistrictManager {
 private static final String URL = "jdbc:mysql://localhost:3306/my_database";
 private static final String USER = "root";
 private static final String PASSWORD = "Varsha@123";
 public static void main(String[] args) {
 Scanner scanner = new Scanner(System.in);
 int choice;
 do {
 System.out.println("\nMenu:");
 System.out.println("1. Insert District");
 System.out.println("2. Modify District");
 System.out.println("3. Delete District");
 System.out.println("4. Search District");
 System.out.println("5. View All Districts");
 System.out.println("6. Exit");
 System.out.print("Enter your choice: ");
 choice = scanner.nextInt();
 scanner.nextLine(); // Consume newline
 switch (choice) {
 case 1:
 insertDistrict(scanner);
 break;
 case 2:
 modifyDistrict(scanner);
 break;
 case 3:
 deleteDistrict(scanner);
 break;
 case 4:
 searchDistrict(scanner);
 break;
 case 5:
 viewAllDistricts();
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
 // Method to insert a district
 private static void insertDistrict(Scanner scanner) {
 System.out.print("Enter district name: ");
 String name = scanner.nextLine();
 System.out.print("Enter area of the district: ");
 float area = scanner.nextFloat();
 System.out.print("Enter population of the district: ");
 Double population = scanner.nextDouble();
 String insertSQL = "INSERT INTO District (Name, Area, Population) VALUES (?, ?, ?)";
 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
 pstmt.setString(1, name);
 pstmt.setFloat(2, area);
 pstmt.setDouble(3, population);
 pstmt.executeUpdate();
 System.out.println("District inserted successfully!");
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 // Method to modify a district
 private static void modifyDistrict(Scanner scanner) {
 System.out.print("Enter the district name to modify: ");
 String name = scanner.nextLine();
 System.out.print("Enter new area: ");
 float area = scanner.nextFloat();
 System.out.print("Enter new population: ");
 int population = scanner.nextInt();
 String updateSQL = "UPDATE District SET Area = ?, Population = ? WHERE Name = ?";
 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 PreparedStatement pstmt = con.prepareStatement(updateSQL)) {
 pstmt.setFloat(1, area);
 pstmt.setInt(2, population);
 pstmt.setString(3, name);
 int rowsUpdated = pstmt.executeUpdate();
 if (rowsUpdated > 0) {
 System.out.println("District modified successfully!");
 } else {
 System.out.println("District not found!");
 }
 } catch (SQLException e) {
 e.printStackTrace();
 }
 }
 // Method to delete a district
 private static void deleteDistrict(Scanner scanner) {
 System.out.print("Enter the district name to delete: ");
 String name = scanner.nextLine();
 String deleteSQL = "DELETE FROM District WHERE Name = ?";
 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 PreparedStatement pstmt = con.prepareStatement(deleteSQL)) {
 pstmt.setString(1, name);
 int rowsDeleted = pstmt.executeUpdate();
 if (rowsDeleted > 0) {
 System.out.println("District deleted successfully!");
 } else {
 System.out.println("District not found!");
 }
} catch (SQLException e) {
    e.printStackTrace();
    }
    }
    // Method to search for a district
    private static void searchDistrict(Scanner scanner) {
    System.out.print("Enter the district name to search: ");
    String name = scanner.nextLine();
    String searchSQL = "SELECT * FROM District WHERE Name = ?";
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    PreparedStatement pstmt = con.prepareStatement(searchSQL)) {
    pstmt.setString(1, name);
    ResultSet rs = pstmt.executeQuery();
    if (rs.next()) {
    System.out.printf("Name: %s, Area: %.2f, Population: %d%n",
    rs.getString("Name"),
    rs.getFloat("Area"),
    rs.getInt("Population"));
    } else {
    System.out.println("District not found!");
    }
    } catch (SQLException e) {
    e.printStackTrace();
    }
    }
    // Method to view all districts
    private static void viewAllDistricts() {
    String selectSQL = "SELECT * FROM District";
    try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(selectSQL)) {
    System.out.printf("%-20s %-10s %-15s%n", "Name", "Area", "Population");
    System.out.println("-------------------------------------------");
    while (rs.next()) {
    System.out.printf("%-20s %-10.2f %-15d%n",
    rs.getString("Name"),
    rs.getFloat("Area"),
    rs.getInt("Population"));
    }
    } catch (SQLException e) {
    e.printStackTrace();
    }
}
}