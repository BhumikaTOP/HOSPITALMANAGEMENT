package HospitalManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


   public class Doctors {
	   private Connection connection;
		public Doctors(Connection connection) {
			this.connection = connection;
			
			
		}
		
		public void viewDoctors() {
			String query = "select * from doctors";
			try {
				PreparedStatement ps = connection.prepareStatement(query);
				ResultSet rs = ps.executeQuery();
				System.out.println("Doctors:");
				System.out.println("+---------+-------------+------+-----+---------+----------------+");
				System.out.println("| Doctor Id | Name        | Specialisation     | Mobileno.   |");
				System.out.println("+---------+-------------+------+-----+---------+----------------+");
				while(rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String specialisation = rs.getString("specialisation");
					int phoneno = rs.getInt("mobileno");
					System.out.printf("|%-12s|%-20s|%-18s|\n", id, name, specialisation, phoneno);
					System.out.println("+---------+-------------+------+-----+---------+----------------+");
					}
			}catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
			public boolean getDoctorById(int id) {
				String query = "SELECT * FROM doctors WHERE id = ?";
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


