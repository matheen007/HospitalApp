import java.sql.*;
import java.util.*;

public class HospitalApp {

    public static void addPatient(Patient p) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO patients (name, age, disease) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getDisease());
            stmt.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchPatient(String keyword) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM patients WHERE name LIKE ? OR id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Age: %d, Disease: %s\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("disease"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePatient(int id) {
        try (Connection conn = Database.getConnection()) {
            String sql = "DELETE FROM patients WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0)
                System.out.println("Patient deleted successfully.");
            else
                System.out.println("Patient ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePatient(int id, String newName, int newAge, String newDisease) {
        try (Connection conn = Database.getConnection()) {
            String sql = "UPDATE patients SET name = ?, age = ?, disease = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newName);
            stmt.setInt(2, newAge);
            stmt.setString(3, newDisease);
            stmt.setInt(4, id);
            int affected = stmt.executeUpdate();
            if (affected > 0)
                System.out.println("Patient updated successfully.");
            else
                System.out.println("Patient ID not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDoctor(String name, String specialty) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO doctors (name, specialty) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, specialty);
            stmt.executeUpdate();
            System.out.println("Doctor added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listDoctors() {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM doctors";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Specialty: %s\n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("specialty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void scheduleAppointment(int patientId, int doctorId, String appointmentDateTime) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patientId);
            stmt.setInt(2, doctorId);
            stmt.setString(3, appointmentDateTime); // Date and time in "YYYY-MM-DD HH:MM:SS" format
            stmt.executeUpdate();
            System.out.println("Appointment scheduled successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listPatients() {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM patients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Age: %d, Disease: %s\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("disease"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Patient");
            System.out.println("2. List All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Update Patient");
            System.out.println("6. Add Doctor");
            System.out.println("7. List Doctors");
            System.out.println("8. Schedule Appointment");
            System.out.println("9. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Age: ");
                int age = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Disease: ");
                String disease = scanner.nextLine();
                addPatient(new Patient(name, age, disease));
            } else if (choice == 2) {
                listPatients();
            } else if (choice == 3) { // Search
                System.out.print("Enter name or ID: ");
                String key = scanner.nextLine();
                searchPatient(key);
            }

            else if (choice == 4) {// Delete
                System.out.print("Enter ID to delete: ");
                int delId = scanner.nextInt();
                deletePatient(delId);
            }

            else if (choice == 5) { // Update
                System.out.print("Enter ID to update: ");
                int upId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("New Name: ");
                String newName = scanner.nextLine();
                System.out.print("New Age: ");
                int newAge = scanner.nextInt();
                scanner.nextLine();
                System.out.print("New Disease: ");
                String newDis = scanner.nextLine();
                updatePatient(upId, newName, newAge, newDis);
            } else if (choice == 6) {
                // Add Doctor
                System.out.print("Doctor Name: ");
                String docName = scanner.nextLine();
                System.out.print("Specialty: ");
                String docSpecialty = scanner.nextLine();
                addDoctor(docName, docSpecialty);
            }

            else if (choice == 7) { // List Doctors
                listDoctors();
            }

            else if (choice == 8) { // Schedule Appointment
                System.out.print("Enter Patient ID: ");
                int patientId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Doctor ID: ");
                int doctorId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Appointment Date and Time (YYYY-MM-DD HH:MM): ");
                String dateTime = scanner.nextLine();
                scheduleAppointment(patientId, doctorId, dateTime);
            }

            else if (choice == 9) {
                System.out.println("Thank You ...");
                break;
            } else {
                System.out.println("Invalid choice.");

            }
        }
    }
}
