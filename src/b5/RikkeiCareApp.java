package b5;

import DBContext.connectionMySql;
import java.sql.*;
import java.util.Scanner;

public class RikkeiCareApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- HỆ THỐNG QUẢN LÝ RIKKEI-CARE ---");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ mới");
            System.out.println("3. Thống kê theo chuyên khoa");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": showDoctors(); break;
                case "2": addDoctor(); break;
                case "3": statisticBySpecialty(); break;
                case "4": System.out.println("Tạm biệt!"); return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
    private static void showDoctors() {
        String sql = "SELECT * FROM Doctors";
        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s | %-20s | %-20s%n", "Mã BS", "Họ Tên", "Chuyên Khoa");
            while (rs.next()) {
                System.out.printf("%-10s | %-20s | %-20s%n",
                        rs.getString("doctor_id"), rs.getString("full_name"), rs.getString("specialty"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static void addDoctor() {
        System.out.print("Nhập mã bác sĩ: "); String id = sc.nextLine();
        System.out.print("Nhập họ tên: "); String name = sc.nextLine();
        System.out.print("Nhập chuyên khoa: "); String spec = sc.nextLine();

        String sql = "INSERT INTO Doctors (doctor_id, full_name, specialty) VALUES (?, ?, ?)";
        try (Connection conn = connectionMySql.connectionMySql();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, spec);

            int row = pstmt.executeUpdate();
            if (row > 0) System.out.println("Thêm bác sĩ thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }

    private static void statisticBySpecialty() {
        String sql = "SELECT specialty, COUNT(*) as total FROM Doctors GROUP BY specialty";
        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("--- THỐNG KÊ CHUYÊN KHOA ---");
            while (rs.next()) {
                System.out.println(rs.getString("specialty") + ": " + rs.getInt("total") + " bác sĩ");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}