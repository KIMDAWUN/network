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
		Statement stmt = null;// �����͸� �����ϴ� ��ü
		PreparedStatement pstmt = null; //statementȮ����
		String id = Sdto.getId();
		int star = Sdto.getStar();
		
		//"UPDATE member SET egg=egg+star where id='a'";
		String satis_sql = "UPDATE member SET egg=egg+";

		try {
			//�ѱ�ó��. mysql�󿡼� �ȵż� ���⼭ ó����.
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
