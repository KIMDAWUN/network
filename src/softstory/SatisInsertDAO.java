package softstory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/*
 * SatisInsertDAO is used to when users answer the question,
 * plus the egg after the satisfaction survey
 */
public class SatisInsertDAO {
	public static void main(String[] args) {
		 
	}
	public static boolean create(SatisDTO Sdto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		String id = Sdto.getId();
		int star = Sdto.getStar();
		
		String satis_sql = "UPDATE member SET egg=egg+";

		try {
			satis_sql += star + " where id='" + id + "'";
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
			pstmt = (PreparedStatement)con.prepareStatement(satis_sql);
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
