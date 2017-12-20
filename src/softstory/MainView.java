package softstory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mysql.jdbc.Statement;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainView extends JFrame {
   private MainProcess main;
   private ImageIcon img, img2;
   static int eggs;
   static String id;

   Connection con = null;
   Statement stmt = null;
   ResultSet rs = null;
   PreparedStatement pstmt = null;

   /**
   * Make main frame
   * and call placeMainPanel to make button for menu 
   */
   MainView(String ID) {

      this.id = ID;

      img = new ImageIcon("images//background.png");

      setTitle("Soft Story!");
      setSize(1300, 700);
      setLocation(200, 70);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // panel
      JPanel panel_main = new JPanel() {
         public void paintComponent(Graphics g) {
            g.drawImage(img.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };

      placeMainPanel(panel_main, ID);

      // add
      add(panel_main);

      // visible
      setVisible(true);

   }

   /**
   *Add buttons for menu and make icon.
   *Input: panel_main (add buttons and icon on this panel)
   *ID (user id)
   */
   public void placeMainPanel(JPanel panel_main, String ID) {
      Font font_title = new Font("배달의민족 주아", Font.CENTER_BASELINE, 60);
      Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 40);
      Color color = new Color(255, 255, 229);

      panel_main.setLayout(null);
      JLabel userLabel = new JLabel(ID + " 님 환영합니다.");
      userLabel.setFont(font);
      userLabel.setBounds(20, 30, 1000, 40);
      panel_main.add(userLabel);

      JButton QuestionButton = new JButton(new ImageIcon("images//QuestionButton.png"));
      QuestionButton.setFont(font);
      QuestionButton.setBackground(color);
      QuestionButton.setBorderPainted(false);
      QuestionButton.setBounds(30, 100, 470, 110);
      panel_main.add(QuestionButton);

      /**
      * Load Question view.
      * If user has 0 egg, then user can't ask a question.
      */
      QuestionButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (eggs == 0)
               JOptionPane.showMessageDialog(null, "Egg가 부족합니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            else {
               try {
                  main.showQuestion();
               } catch (Exception i) {
                  System.out.println("오류");
               }
            }
         }
      });

      JButton ScheduleButton = new JButton(new ImageIcon("images//ScheduleButton.png"));
      ScheduleButton.setFont(font);
      ScheduleButton.setBackground(color);
      ScheduleButton.setBorderPainted(false);
      ScheduleButton.setBounds(30, 240, 460, 110);
      panel_main.add(ScheduleButton);

    //Load Schedule view
      ScheduleButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.showSchedule(ID);
         }
      });

      JButton MessengerButton = new JButton(new ImageIcon("images//MessengerButton.png"));
      MessengerButton.setFont(font);
      MessengerButton.setBackground(color);
      MessengerButton.setBorderPainted(false);
      MessengerButton.setBounds(30, 380, 480, 110);
      panel_main.add(MessengerButton);

    //Load Messenger view 
      MessengerButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.showMessenger();
         }
      });

    
      JButton SettingButton = new JButton(new ImageIcon("images//SettingButton.png"));
      SettingButton.setFont(font);
      SettingButton.setBackground(color);
      SettingButton.setBorderPainted(false);
      SettingButton.setBounds(30, 520, 480, 110);
      panel_main.add(SettingButton);
    //Load Setting view 
      SettingButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.showSetting();
         }
      });
      eggs = getegg();
      JButton Egg = new JButton(new ImageIcon("images//egg.png"));
      Egg.setBounds(1000, 475, 180, 180);
      Egg.setBackground(Color.yellow);
      Egg.setBorderPainted(false);
      Egg.setFocusPainted(false);
      Egg.setContentAreaFilled(false);
      panel_main.add(Egg);
      /**
      * Show the number of egg which user has.
      * If number of eggs is over 150, then get user's phone number  to send gift.
      */
      Egg.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "My egg is " + eggs + " .");
            if (eggs >= 150) {
               String resultStr = null;
               String name;
               name = getID();
               resultStr = JOptionPane.showInputDialog("받으실 핸드폰 번호를 입력해주시오.");
               chicken send = new chicken(name, resultStr);
               send.insert();
            }
         }
      });

      /**
      *Load character according number of egg.
      *If number of eggs is between 0 and 40 -> egg character
      *If number of eggs is between 40 and 90 -> chick character
      *If number of eggs is between 90 and 150 -> bigger chick character
      *If number of eggs is over 150 ->chicken character
      */
      String egg_character = null;
      if (eggs >= 0 && eggs < 40)
         egg_character = "images//anigif1.gif";
      else if (eggs >= 40 && eggs < 90)
         egg_character = "images//anigif2.gif";
      else if (eggs >= 90 && eggs < 150)
         egg_character = "images//anigif3.gif";
      else if (eggs >= 150) {
         egg_character = "images//anigif4.gif";
      }

    //Load character according user's number of eggs.
      JButton animation = new JButton(new ImageIcon(egg_character));

      animation.setBounds(530, 50, 600, 580);
      animation.setBackground(Color.yellow);
      animation.setBorderPainted(false);
      animation.setFocusPainted(false);
      animation.setContentAreaFilled(false);
      panel_main.add(animation);


      /**
       *If user log out then have to chane login state in DB
       *Change the login value as 'false'.
       */
      JButton logOut = new JButton(new ImageIcon("images//logout.png"));
      logOut.setBounds(1150, 520, 130, 130);
      logOut.setBackground(color);
      logOut.setBorderPainted(false);
      logOut.setFocusPainted(false);
      logOut.setContentAreaFilled(false);
      panel_main.add(logOut);
      logOut.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            String sql_for_login = "UPDATE member SET login=?" + "where id=?";

            try {
               pstmt = con.prepareStatement(sql_for_login);
               pstmt.setString(1, "false");
               pstmt.setString(2, id);

               int cnt = pstmt.executeUpdate();
               // dispose();
               main.exitMain();
            } catch (SQLException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }

         }
      });

   }

   /**
   * Get number of egg on 'member' table.
   * Find the value on DB
   */
   public int getegg() {
      int egg = 0;

      try {

         String sql = "select egg from member where id='" + id + "'";

         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
         stmt = (Statement) con.createStatement();
         rs = stmt.executeQuery(sql);
         int num = 0;
         while (rs.next()) {
            egg = rs.getInt(1);
         }
         return egg;
      } catch (Exception e) {
         return 0;
      }
   }

   /**
   * Get user name of ID from DB
   */
   public String getName() {
      String userName = "";

      try {
         String sql = "select name from member where id='" + id + "'";
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory", "root", "12345");
         stmt = (Statement) con.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            userName = rs.getString(1);
            System.out.println(userName);
         }
         return userName;
         // return new String(userName.getBytes(), "ISO-8859-1");
      } catch (Exception e) {
         return null;
      }
   }

   /**
   * return ID of user
   */
   public static String getID() {
      return id;
   }

   public void setMain(MainProcess main) {
      this.main = main;
   }
}
