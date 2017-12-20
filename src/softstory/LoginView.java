package softstory;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

public class LoginView extends JFrame {
   private MainProcess main;
   private ImageIcon img;
   public static String currentID;
   public static boolean LoginFlag = false;

   private JLabel userLabel, passLabel;
   private JButton btnLogin, btnInit, btnJoin, btnExit;
   private JPasswordField passText;
   private JTextField userText;
   private boolean bLoginCheck;
   
   private PreparedStatement pstmt = null;
   
   Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 50);
   Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 40);
   Font font2 = new Font("바탕체", Font.CENTER_BASELINE, 40);
   
   public static void main(String[] args) {
     new LoginView();
   }
   
   /**
   * Make frame for login.
   * and call placeLoginPanel to make login button and things.
   */
   
   LoginView() {
      img = new ImageIcon("images//background_login.png");

      setTitle("Welcome to Soft Story! : )");
      setSize(1300, 700);
      setResizable(false);
      setLocation(300, 100);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Panel
      JPanel panel_login = new JPanel() {
         public void paintComponent(Graphics g) {

            g.drawImage(img.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };

      placeLoginPanel(panel_login);
      
      //add panel_login on JFrame
      add(panel_login);

      // visiible
      setVisible(true);
   }
   
   /**
   * Add thins on login view
   * Parameter: panel_login (Add things on this panel) 
   * 
   */
   public void placeLoginPanel(JPanel panel_login) {
      panel_login.setLayout(null);

      userLabel = new JLabel("ID: ");
      userLabel.setFont(font);
      userLabel.setBounds(100, 250, 130, 50);
      panel_login.add(userLabel);

      passLabel = new JLabel("PW: ");
      passLabel.setFont(font);
      passLabel.setBounds(100, 350, 130, 50);
      panel_login.add(passLabel);

      Color color = new Color(255,255,229);
      
      userText = new JTextField(20);
      userText.setFont(font1);
      userText.setBounds(200, 250, 300, 50);
      userText.setBorder(null);
      userText.setBackground(color);
      panel_login.add(userText);

      passText = new JPasswordField(20);
      passText.setFont(font2);
      passText.setBorder(null);
      passText.setBackground(color);
      passText.setBounds(200, 350, 300, 50);
      panel_login.add(passText);
      passText.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
            isLoginCheck();
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         }
      });
      //Reset written Id, password
      btnInit = new JButton(new ImageIcon("images//ResetButton.png"));
      btnInit.setBounds(70, 440, 160, 170);
      btnInit.setBackground(Color.white);
      btnInit.setBorderPainted(false);
      btnInit.setFocusPainted(false);
      btnInit.setContentAreaFilled(false);
      panel_login.add(btnInit);
      btnInit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            userText.setText("");
            passText.setText("");
         }
      });
      //If user check this button, call isLoginCheck()
      btnLogin = new JButton(new ImageIcon("images//LoginButton.png"));
      btnLogin.setBounds(235, 440, 160, 170);
      btnLogin.setBackground(Color.white);
      btnLogin.setBorderPainted(false);
      btnLogin.setFocusPainted(false);
      btnLogin.setContentAreaFilled(false);
      panel_login.add(btnLogin);
      String id = userText.getText();
      btnLogin.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
            isLoginCheck();            
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         }
      });

      //If user click this button load joining view to get user information
      btnJoin = new JButton(new ImageIcon("images//JoiningButton.png"));
      btnJoin.setBounds(400, 440, 160, 170);
      btnJoin.setBackground(Color.white);
      btnJoin.setBorderPainted(false);
      btnJoin.setFocusPainted(false);
      btnJoin.setContentAreaFilled(false);
      panel_login.add(btnJoin);
      btnJoin.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
        	//show joining view
            main.showJoiningView();
         }
      });
      // If user click this button, program will be end
      btnExit = new JButton(new ImageIcon("images//exit.png"));
      btnExit.setBounds(1200, 570, 80, 80);
      btnExit.setBackground(Color.white);
      btnExit.setBorderPainted(false);
      btnExit.setFocusPainted(false);
      btnExit.setContentAreaFilled(false);

      panel_login.add(btnExit);
      btnExit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      
   }

   /**
   * Check user is member of softstory
   * If he is member of softstory, then num will be 1
   * And check he is login now or not. 'false' means user not access now. 
   * If login value in DB is false then he can login successfully
   * (To prevent concurrent connection)
   * return: flag -> 'true' means login is success
   	       -> 'false' means login is not success 
   */
   public boolean loginCheck() {
      boolean flag = false;
      Connection con = null;
      Statement stmt = null; //data transfer object
      ResultSet rs = null; //use data address
      try {
         String userId = null;
         String userPW = null;
         String id = userText.getText();
         String login_ch =null;
         char[] pw = passText.getPassword();
         String sql = "select * from member where id='" + id + "'";
         String sql_for_login = "UPDATE member SET login=?" + "where id=?";
         
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
         stmt = (Statement) con.createStatement();
         rs = stmt.executeQuery(sql);
       
         int num = 0;
         while (rs.next()) {
            userId = rs.getString(1);
            userPW = rs.getString(2);
            login_ch=rs.getString(7);
            System.out.println(userId);
            System.out.println(userPW);
            num++;
         }
         
         System.out.println(num);
         System.out.println(login_ch);
         
       //user information is exist on DB(If user is member of softstory)
       //and that user is not connected. then login is success.
         if (num == 1 && login_ch.equals("false")) {
            if (new String(passText.getPassword()).equals(userPW)) {
               System.out.println("log in success.");
               
              
             //If login is success, then change the value of login in DB, to notify user is connected
               pstmt=con.prepareStatement(sql_for_login);
               pstmt.setString(1, "true");
               pstmt.setString(2, id);
               
               
               int cnt = pstmt.executeUpdate();
              
               flag = true;
            }
         } else {
        	//Problem on login -> No member information
            flag = false;
         }
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

   /**
   * if user login successfully then, pop up that login is successed.
   * otherwise pop up warning that member is not exist.
   */
   public void isLoginCheck() throws IOException {
      if (loginCheck()) {
         JOptionPane.showMessageDialog(null, "Welcome " + userText.getText() + " !");
         bLoginCheck = true;

         if (isLogin()) {
           currentID = userText.getText();
           LoginFlag = true;
           main.showMainView(userText.getText());
         }

      } else {
         JOptionPane.showMessageDialog(null, "Please check your ID or PW");
      }
   }

   public void setMain(MainProcess main) {
      this.main = main;
   }

   public boolean isLogin() {
      return bLoginCheck;
   }
   
}