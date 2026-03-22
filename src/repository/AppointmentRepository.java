package repository;

import DBContext.connectionMySql;
import model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public void addAppointment(Appointment app) {
        String sql = "INSERT INTO appointments (patient_name, appointment_date, doctor_name, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionMySql.connectionMySql();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app.getPatientName());
            pstmt.setDate(2, app.getAppointmentDate());
            pstmt.setString(3, app.getDoctorName());
            pstmt.setString(4, app.getStatus());
            pstmt.executeUpdate();
            System.out.println("Thêm lịch khám thành công!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Connection conn = connectionMySql.connectionMySql();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setId(rs.getInt("id"));
                app.setPatientName(rs.getString("patient_name"));
                app.setAppointmentDate(rs.getDate("appointment_date"));
                app.setDoctorName(rs.getString("doctor_name"));
                app.setStatus(rs.getString("status"));
                list.add(app);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void updateAppointment(Appointment app) {
        String sql = "UPDATE appointments SET patient_name=?, appointment_date=?, doctor_name=?, status=? WHERE id=?";
        try (Connection conn = connectionMySql.connectionMySql();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, app.getPatientName());
            pstmt.setDate(2, app.getAppointmentDate());
            pstmt.setString(3, app.getDoctorName());
            pstmt.setString(4, app.getStatus());
            pstmt.setInt(5, app.getId());
            pstmt.executeUpdate();
            System.out.println("Cập nhật thành công!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = connectionMySql.connectionMySql();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Xóa lịch khám ID " + id + " thành công!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
}