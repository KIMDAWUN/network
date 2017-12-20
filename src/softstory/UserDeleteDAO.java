package softstory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/*
 * UserDeleteDAO is used to removes currentUser from DB
 * when the user exits.
 */
public class UserDeleteDAO {
	public static void main(String[] args) {
		 
	}
	public static boolean create(UserDTO Udto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		String id = Udto.getId();		
		
		String userDelete_sql = "DELETE FROM currentUser where id='";

		try {
			userDelete_sql += new String(id.getBytes(), "ISO-8859-1") + "'";
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
			pstmt = (PreparedStatement)con.prepareStatement(userDelete_sql);
			int result = pstmt.executeUpdate();
			flag = true;
			
			if(result > 0){
				System.out.println("update success");
			}
			else{
				System.out.println("update fail");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return flag;
	}
}
