package b2;

import DBContext.connectionMySql;
import java.sql.*;

public class PharmacyManager {
    /* * PHẦN 1 - PHÂN TÍCH:
     * 1. Tại sao 'if' không đủ: Lệnh 'if (rs.next())' chỉ kiểm tra dòng đầu tiên.
     * Nếu có dữ liệu, nó thực hiện lệnh in một lần rồi kết thúc luôn.
     * 2. Con trỏ ResultSet:
     * - Ban đầu ở vị trí "Before First Row".
     * - Mỗi lần gọi next(), con trỏ di chuyển xuống dòng tiếp theo.
     * - Trả về 'true' nếu có dữ liệu, 'false' nếu đã duyệt hết bảng.
     */

    public void printPharmacyCatalogue() {
        String sql = "SELECT medicine_name, stock FROM Pharmacy";
        
        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("--- DANH MỤC THUỐC KIỂM KÊ ---");
            System.out.printf("%-20s | %-10s%n", "Tên thuốc", "Số lượng");
            System.out.println("------------------------------------");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String name = rs.getString("medicine_name");
                int stock = rs.getInt("stock");
                System.out.printf("%-20s | %-10d%n", name, stock);
            }

            if (!hasData) {
                System.out.println("Thông báo: Hiện tại kho trống.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PharmacyManager().printPharmacyCatalogue();
    }
}