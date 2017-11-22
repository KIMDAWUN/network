package softstory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class SatisInsertDAO {
	public static void main(String[] args) {
		 
	}
	public static boolean create(SatisDTO Sdto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null;// 데이터를 전송하는 객체
		PreparedStatement pstmt = null; //statement확장판
		String id = Sdto.getId();
		int star = Sdto.getStar();
		
		//"UPDATE member SET egg=egg+star where id='a'";
		String satis_sql = "UPDATE member SET egg=egg+";

		try {
			//한글처리. mysql상에서 안돼서 여기서 처리함.
			satis_sql += star + " where id='" + id + "'";
			//satis_sql += star + " where name='" + new String(name.getBytes(), "ISO-8859-1") + "'";
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/softstory", "root", "12345");
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
