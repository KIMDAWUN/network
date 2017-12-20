package softstory;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.Statement;

/*
 * InsertDAO is used to when users join to SoftStroy
 * connect DB
 * update DB id password name studentNo year egg language course
 * first joining, egg is 20
 */

public class InsertDAO {
	public static boolean create(DTO dto) throws Exception {

		boolean flag = false;
		Connection con = null;
		Statement stmt = null; //data transfer object
		String id = dto.getId();
		String passwd = dto.getPassword();
		String name = dto.getName();
		String studentNo = dto.getStudentNo();
		String year = dto.getYear();
		int egg = dto.getEgg();
		String language = dto.getLanguage();
		String[] languageList = language.split(" ");
		String course = dto.getCourse();
		String[] courseList = course.split(" ");
		
		String member_sql = "INSERT INTO member(id, password, name, studentNo, year, egg) VALUES";

		try {
			//한글처리. mysql상에서 안돼서 여기서 처리함.
			member_sql += "('" + new String(id.getBytes(), "ISO-8859-1") + "','"  
					+ new String(passwd.getBytes(), "ISO-8859-1") + "','"
					+ new String(name.getBytes(), "ISO-8859-1") + "','"
					+ new String(studentNo.getBytes(), "ISO-8859-1") + "','"
					+ new String(year.getBytes(), "ISO-8859-1") + "' , '" 
					+ "20')"; //처음 가입시 주는 egg개수
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
			stmt = (Statement) con.createStatement();
			stmt.executeUpdate(member_sql);
			
			for(int i = 0; i < languageList.length; i++){
				String language_sql = "INSERT INTO language(id, confiLanguage) VALUES";
				language_sql += "('" + new String(id.getBytes(), "ISO-8859-1") + "','" + languageList[i] + "')";
				stmt.executeUpdate(language_sql);
			}
			
			for(int i = 0; i < courseList.length; i++){
				String course_sql = "INSERT INTO course(id, course_name) VALUES";
				course_sql += "('" + new String(id.getBytes(), "ISO-8859-1") + "','" + new String(courseList[i].getBytes(), "ISO-8859-1") + "')";
				stmt.executeUpdate(course_sql);
			}

			flag = true;
		} catch (Exception e) {
			System.out.println(e);
			flag = false;
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
		return flag;
	}
	/*
	 * createPush is used to when user updates push alarm on off
	 * change the DB push attribute in member
	 */
	public static boolean createPush(DTO dto) throws Exception {
		boolean f=false;
		Connection con = null;
		Statement stmt = null; //data transfer object
		
		int push=dto.getpush();
		String id=dto.getId();
		String sql="update member set push="+push+" where id='"+id+"'";
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
