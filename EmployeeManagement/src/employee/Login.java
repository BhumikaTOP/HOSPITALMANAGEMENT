package employee;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Login {

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con;
			
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root","BHUMIKA2004");
			System.out.println(con);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}