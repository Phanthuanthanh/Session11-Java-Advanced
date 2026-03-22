package DBContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionMySql {

    public static Connection connectionMySql(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/thanhpttt",
                    "root",
                    "270606."
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = connectionMySql();

        if (conn != null) {
            System.out.println("Kết nối thành công Phan Thuận Thành");
        } else {
            System.out.println("Kết nối thất bại");
        }
    }
}