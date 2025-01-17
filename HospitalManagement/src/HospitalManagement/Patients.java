package HospitalManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
	private Connection connection;
	private Scanner scanner;
	public Patients(Connection connection,Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}
	
	public void addPatients() {
		System.out.print("enter the patient name: ");
		String name =scanner.next();
		System.out.print("enter patient age: ");
		int age = scanner.nextInt();
		System.out.print("enter the gender: ");
		String gender =scanner.next();
		System.out.print("enter patient phoneno: ");
		int phoneno = scanner.nextInt();
		try { 
			String query = "INSERT INTO patients(name,age,gender,phoneno) VALUES(?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,name);
			ps.setInt(2, age);
			ps.setString(3,gender);
			ps.setInt(4, phoneno);
			int status = ps.executeUpdate();
			if(status>0) {
				System.out.println("Patient Added Successfully!!");
			}else {
				System.out.println("Failed to add patients!!");
			}
		}
			
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Patients:");
			System.out.println("+---------+-------------+------+-----+---------+----------------+");
			System.out.println("| Patient Id | Name        | Age | Gender  | Phoneno.   |");
			System.out.println("+---------+-------------+------+-----+---------+----------------+");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
                int phoneno = rs.getInt("phoneno");
				System.out.printf("|%-12s|%-20s|%-10s|%-12s|\n", id, name, age, gender,phoneno);
				System.out.println("+---------+-------------+------+-----+---------+----------------+");
			}
			}catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		public boolean getPatientById(int id) {
			String query = "SELECT * FROM patients WHERE id = ?";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setInt(1,id);
				ResultSet rs = ps.executeQuery();
				return rs.next();
			
		}catch(SQLException e) {
			e.printStackTrace();
		} return false;
	       }
	}
		


	


