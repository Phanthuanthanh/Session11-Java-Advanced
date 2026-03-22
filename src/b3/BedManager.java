package b3;

import DBContext.connectionMySql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BedManager {

    /* * PHẦN 1 - PHÂN TÍCH:
     * - executeUpdate() trả về kiểu int: là số dòng bị thay đổi trong Database.
     * - Nếu mã giường không tồn tại (ví dụ Bed_999), hàm trả về 0.
     * - Ta dùng giá trị này để thông báo chính xác cho y tá, tránh việc hệ thống im lặng
     * khi không có dữ liệu nào thực sự được cập nhật.
     */

    public void updateBedStatus(String inputId) {
        String sql = "UPDATE Beds SET bed_status = 'Occupied' WHERE bed_id = '" + inputId + "'";

        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                int rowsAffected = stmt.executeUpdate(sql);

                if (rowsAffected > 0) {
                    System.out.println("Thành công: Đã cập nhật giường " + inputId + " sang trạng thái 'Đang sử dụng'.");
                } else {
                    System.out.println("Lỗi: Không tìm thấy mã giường '" + inputId + "'. Vui lòng kiểm tra lại!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi hệ thống khi cập nhật: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BedManager manager = new BedManager();
        manager.updateBedStatus("1");
        manager.updateBedStatus("Bed_999");
    }
}