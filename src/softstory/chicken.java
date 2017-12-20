package softstory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @name: Chicken 
 * @content This class is used to give chicken. If someone approach the goal, they get chicken
 *
 */
public class chicken {
   private String driver = "com.mysql.jdbc.Driver";
   private String url = "jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory";
   String phone;
   String id;

   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   public chicken(String id, String phone)
   {
      this.id=id;
      this.phone=phone;
   }
   
   //Save the information into database, If users enter their phone number, we give giftcon to their phone.
   public void insert()
   {
      String query1 = "insert into egg(id,phone)" + "values"+"('"+id+"','"+phone+"')";
      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, "root", "12345");
         pstmt = con.prepareStatement(query1);

         int cnt = pstmt.executeUpdate();
         
      } catch (Exception eeee) {
         System.out.println(eeee.getMessage());
      } finally {
         try {
            pstmt.close();
            con.close();
         } catch (Exception e2) {
         }
      }
   }
}