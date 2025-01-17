package HospitalManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalAdminstration {
	final static String url = "jdbc:mysql://localhost:3306/hospital";
	final static String username = "root";
	final static String password = "BHUMIKA2004";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			Patients patient = new Patients (connection,scanner);
			Doctors doctor = new Doctors(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT");
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				int choice = scanner.nextInt(); 
				switch(choice) {
				case 1 :
					// Add Patients
					patient.addPatients();
					System.out.println();
					break;
				case 2 :
					// View Patients
					patient.viewPatients();
					System.out.println();
					break;
				case 3 :
					// View Doctors
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4 :
					// Book Appointment
					bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
					
				case 5 :
					return ;
				default:
					System.out.println("Enter valid choice!!!");
					break;
				}
				
				}
			}catch(SQLException e) {
			e.printStackTrace();
		}
		  }

	public static void bookAppointment(Patients patient, Doctors doctor,Connection connection,Scanner scanner) {
		System.out.println("Enter Patient Id:");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id:");
		int doctorId = scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD):");
		String appoinmentDate = scanner.next();
		if(patient.getPatientById(patientId)&&doctor.getDoctorById(doctorId)) {
		   if(checkDoctorAvailability(doctorId,appoinmentDate, connection)) {
			 String appointmentquery = "INSERT INTO appointments(doctor_id,patient_id,appointment_date) VALUES(?,?,?)";
			 try {
				 PreparedStatement ps = connection.prepareStatement(appointmentquery);
				 ps.setInt(1,patientId);
				 ps.setInt(2, doctorId);
				 ps.setString(3, appoinmentDate);
				 int status = ps.executeUpdate();
				 if(status>0) {
					 System.out.println("Appointment Booked!");
				 }else {
					 System.out.println("Failed to Book Appointment!");
				 }
			 }catch(SQLException e) {
				 e.printStackTrace();
			 }
		   }else {
			   System.out.println("Doctor not available on this date!!");
		   }
	}else {
		System.out.println("Either doctor or patient doesnot exists!!"); }
	}

		public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection) {
			String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ? ";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1, doctorId);
				ps.setString(2, appointmentDate);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					int count = rs.getInt(1);
					if(count == 0) {
						return true;
					}else {
						return false;
					}
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
}
