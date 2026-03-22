package BTTH;

import model.Appointment;
import repository.AppointmentRepository;
import java.sql.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AppointmentRepository repo = new AppointmentRepository();

        Appointment newApp = new Appointment("Phan Thuận Thành", Date.valueOf("2026-03-25"), "Dr. Strange", "Confirmed");
        repo.addAppointment(newApp);

        System.out.println("\n--- DANH SÁCH LỊCH KHÁM ---");
        List<Appointment> list = repo.getAllAppointments();
        for (Appointment a : list) {
            System.out.println(a.getId() + " | " + a.getPatientName() + " | " + a.getDoctorName() + " | " + a.getStatus());
        }
    }
}
