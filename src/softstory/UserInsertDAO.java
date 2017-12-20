package softstory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/*
 * UserInsertDAO is used to Insert into currentUser in DB
 * when user login 
 */
public class UserInsertDAO {
	public static void main(String[] args) {
		 
	}
	public static boolean create(UserDTO Udto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		String id = Udto.getId();		
		
		
		String user_sql = "INSERT INTO currentUser(id) VALUES";

		try {
			user_sql += " ('" + new String(id.getBytes(), "ISO-8859-1") + "')";
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
			pstmt = (PreparedStatement)con.prepareStatement(user_sql);
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
	
	/*
	 * createPush is used to when user updates push alarm on off
	 * change the DB push attribute in currentUser
	 */
	public static boolean createPush(UserDTO Udto) throws Exception {
		boolean f=false;
		Connection con = null;
		Statement stmt = null;
		
		int push=Udto.getPush();
		String id=Udto.getId();
		String sql="update currentUser set push="+push+" where id='"+id+"'";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e) {
			System.out.println(e);
			f = false;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return f;
	}
}
