package b1;
import DBContext.connectionMySql;
import java.sql.Connection;
import java.sql.SQLException;

public class PatientManager {
    /* * PHẦN 1 - PHÂN TÍCH:
     * Tại sao việc khởi tạo kết nối liên tục mà không có cơ chế đóng (Close) lại gây nguy hiểm:
     * 1. Cạn kiệt Connection Pool: Database có giới hạn số lượng kết nối đồng thời. Nếu không đóng,
     * các kết nối cũ vẫn chiếm chỗ, khiến người dùng mới không thể truy cập (Lỗi Communications link failure).
     * 2. Treo hệ thống: RAM máy chủ sẽ bị đầy do phải duy trì quá nhiều kết nối "rác", dẫn đến app bị treo.
     * 3. Ngắt quãng dịch vụ 24/7: Hệ thống y tế cần sự ổn định tuyệt đối. Việc rò rỉ kết nối sẽ làm
     * sập hệ thống sau vài tiếng vận hành, gây gián đoạn việc cứu chữa bệnh nhân.
     */

    public void runModule() {
        try (Connection conn = connectionMySql.connectionMySql()) {
            if (conn != null) {
                System.out.println("Kết nối thành công! Đang xử lý hồ sơ bệnh án...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PatientManager().runModule();
    }
}