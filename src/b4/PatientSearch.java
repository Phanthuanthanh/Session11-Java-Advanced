package b4;

import DBContext.connectionMySql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientSearch {

    /**
     * Hàm loại bỏ các ký tự nguy hiểm để ngăn chặn SQL Injection đơn giản
     */
    public String sanitizeInput(String input) {
        if (input == null) return "";
        return input.replace("'", "")
                .replace(";", "")
                .replace("--", "");
    }

    public void searchPatient(String patientName) {
        String cleanName = sanitizeInput(patientName);

        String sql = "SELECT * FROM Patients WHERE full_name = '" + cleanName + "'";

        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("--- KẾT QUẢ TRA CỨU BỆNH NHÂN ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Bệnh nhân: " + rs.getString("full_name") +
                        " | Bệnh lý: " + rs.getString("diagnosis"));
            }

            if (!found) {
                System.out.println("Không tìm thấy bệnh nhân nào với tên: " + cleanName);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PatientSearch searcher = new PatientSearch();

        String hackerInput = "'' OR '1'='1'";

        System.out.println("Đang tìm kiếm với đầu vào nguy hiểm: " + hackerInput);
        searcher.searchPatient(hackerInput);
    }
}