package softstory;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/*
* SettingView class is used to modify clients's information
* or set up push alarms on off
*/
public class SettingView extends JFrame{
   private MainProcess main;
   private JPanel contentPane;
   private JButton btnReturn;
   Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 40);
   Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);
   Font font2 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 13);
   
   DTO dto = new DTO();
   UserDTO udto = new UserDTO();
   
    /*
	 * JFrame for settingView
	 */
   SettingView(){
      setTitle("Setting");
      setSize(300, 75);
      setLocation(500, 70);
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      
      JButton btnModification = new JButton("Modification");
      btnModification.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JFrame f=new JFrame("Modification");
              f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              f.setBounds(100,100,100,100);
              Dimension dim= new Dimension(1100,600);
              f.setPreferredSize(dim);
              f.pack();
              JPanel panel_join= new JPanel();
              panel_join.setLayout(null);

              JLabel FirstLabel1 = new JLabel("Welcome to Soft Story!");
               JLabel FirstLabel2 = new JLabel("Please type your real information!");
               FirstLabel1.setFont(font);
               FirstLabel2.setFont(font1);
               FirstLabel1.setBounds(10,2,1000,50);
                  panel_join.add(FirstLabel1);
                  FirstLabel2.setBounds(10,55,1000,20);
                  panel_join.add(FirstLabel2);
                  
                  JLabel NAMELabel = new JLabel("NAME: ");
                  NAMELabel.setFont(font1);
                  NAMELabel.setBounds(10, 100, 80, 25);
                  panel_join.add(NAMELabel);
                  
                  JTextField NAMEText = new JTextField(20);
                  NAMEText.setBounds(120, 100, 100, 25);
                  panel_join.add(NAMEText);
                  
                  JLabel SNLabel = new JLabel("Student Number: ");
                  SNLabel.setFont(font1);
                  SNLabel.setBounds(10, 140, 200, 25);
                  panel_join.add(SNLabel);

                  JTextField SNText = new JTextField(20);
                  SNText.setBounds(200, 140, 150, 25);
                  panel_join.add(SNText);
                  
                  JLabel YEARLabel = new JLabel("Year: ");
                  YEARLabel.setFont(font1);
                  YEARLabel.setBounds(10,180,80,25);
                  panel_join.add(YEARLabel);
                  Choice list;//학년
                  list = new Choice();
                  list.add("1학년");
                  list.add("2학년");
                  list.add("3학년");
                  list.add("4학년");
                  list.add("대학원생");
                  list.add("졸업생");
                  list.setBounds(100,180,140,25);
                  panel_join.add(list);
                  JLabel LanguageLabel = new JLabel("Confident Language: ");
                  LanguageLabel.setFont(font1);
                  LanguageLabel.setBounds(10,240,250,25);
                  panel_join.add(LanguageLabel);
                  
                  JCheckBox[] languageList = new JCheckBox[9];
                  languageList[0] = new JCheckBox("C");
                  languageList[1] = new JCheckBox("C++");
                  languageList[2] = new JCheckBox("Java");
                  languageList[3] = new JCheckBox("JavaScript");
                  languageList[4] = new JCheckBox("Python");
                  languageList[5] = new JCheckBox("PHP");
                  languageList[6] = new JCheckBox("CSS");
                  languageList[7] = new JCheckBox("HTML");
                  languageList[8] = new JCheckBox("Others..");
                  
                  int yAxis = 280;
                  for(int i = 0; i < 9; i++){
                     if(i%2 == 0){
                        languageList[i].setFont(font2);
                        languageList[i].setBounds(10,yAxis,150,25);
                         panel_join.add(languageList[i]);
                     }
                     else if(i%2 == 1){
                        languageList[i].setFont(font2);
                        languageList[i].setBounds(190,yAxis,150,25);
                         panel_join.add(languageList[i]);
                         yAxis += 40;
                     }
                     
                  }
                  
                  JLabel TakeLabel = new JLabel("Taken/Taking Course: ");
                  TakeLabel.setFont(font1);
                  TakeLabel.setBounds(450,100,250,25);
                  panel_join.add(TakeLabel);
                  
                  JCheckBox[] courseList = new JCheckBox[24];
                  courseList[0] = new JCheckBox("컴퓨터프로그래밍");
                  courseList[1] = new JCheckBox("웹프로그래밍");
                  courseList[2] = new JCheckBox("이산수학");
                  courseList[3] = new JCheckBox("소프트웨어구현패턴");
                  courseList[4] = new JCheckBox("로봇공학");
                  courseList[5] = new JCheckBox("확률통계");
                  courseList[6] = new JCheckBox("자료구조");
                  courseList[7] = new JCheckBox("객체지향프로그래밍");
                  courseList[8] = new JCheckBox("운영체제");
                  courseList[9] = new JCheckBox("컴퓨터네트워크");
                  courseList[10] = new JCheckBox("알고리즘");
                  courseList[11] = new JCheckBox("데이터베이스");
                  courseList[12] = new JCheckBox("모바일프로그래밍");
                  courseList[13] = new JCheckBox("소프트웨어산업세미나");
                  courseList[14] = new JCheckBox("소프트웨어공학");
                  courseList[15] = new JCheckBox("컴퓨터그래픽스");
                  courseList[16] = new JCheckBox("고급웹프로그래밍");
                  courseList[17] = new JCheckBox("분산시스템");
                  courseList[18] = new JCheckBox("컴퓨터구조");
                  courseList[19] = new JCheckBox("데이터마이닝");
                  courseList[20] = new JCheckBox("멀티미디어");
                  courseList[21] = new JCheckBox("소프트웨어신기술특론");
                  courseList[22] = new JCheckBox("컴퓨터보안");
                  courseList[23] = new JCheckBox("HCI");
                  
                  yAxis = 150;
                  for(int i = 0; i < 24; i++){
                     if(i%3 == 0){
                        courseList[i].setFont(font2);
                         courseList[i].setBounds(450,yAxis,200,25);
                         panel_join.add(courseList[i]);
                     }
                     else if(i%3 == 1){
                        courseList[i].setFont(font2);
                         courseList[i].setBounds(650,yAxis,200,25);
                         panel_join.add(courseList[i]);
                     }
                     else if(i%3 == 2){
                        courseList[i].setFont(font2);
                         courseList[i].setBounds(850,yAxis,200,25);
                         panel_join.add(courseList[i]);
                         yAxis += 40;
                     }
                     
                  }
                  f.getContentPane().add(panel_join);
                    f.pack();
              f.setVisible(true);
         }
            }
      );

      menuBar.add(btnModification);
      
      JMenu mnPush = new JMenu("push");
      menuBar.add(mnPush);
      
      JMenuItem mntmYes = new JMenuItem("yes");
      mnPush.add(mntmYes);

      /*
	   * mntmYes is push on 
	   * when you click mntmYes,
	   * update push information in the database to yes
	   * you can receive Question window
	  */
      mntmYes.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(mntmYes, "push on 되었습니다.", "Title", JOptionPane.INFORMATION_MESSAGE, null);
            dto.setName(MainView.id);
            udto.setId(MainView.id);
            dto.setPush(1);
            udto.setPush(1);
            try{
                     InsertDAO.createPush(dto);
                   UserInsertDAO.createPush(udto);
            }catch(Exception e1){
                     e1.printStackTrace();
                  }
            
         }
      });
      
      JMenuItem mntmNo = new JMenuItem("no");
      mnPush.add(mntmNo);
      
      /*
		 * mntmNo is push off 
		 * when you click mntmNo,
		 * update push information in the database to No
		 * you will not receive Question window
		 */
      mntmNo.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(mntmNo, "push off 되었습니다.", "Title", JOptionPane.INFORMATION_MESSAGE, null);
            dto.setId(MainView.id);
            dto.setPush(0);
            udto.setId(MainView.id);
            udto.setPush(0);
            try{
                     InsertDAO.createPush(dto);
                     UserInsertDAO.createPush(udto);
            }catch(Exception e1){
                     e1.printStackTrace();
                   JOptionPane.showInputDialog(null,"실패");
                  }
            
         }
      });
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane.setLayout(new BorderLayout(0, 0));
      setContentPane(contentPane);
      // panel
      JPanel panel_Setting = new JPanel();
      placeSettingPanel(panel_Setting);

      // add
      add(panel_Setting);

      // visiible
      setVisible(true);
   }
   
   /*
	 * panel for return button
	 */
   public void placeSettingPanel(JPanel panel_Setting) {
      panel_Setting.setLayout(null);
      
      btnReturn = new JButton(new ImageIcon("images//back.png"));
      btnReturn.setBounds(300,200, 50, 50);
      btnReturn.setBackground(Color.white);
      btnReturn.setBorderPainted(false);
      btnReturn.setFocusPainted(false);
      btnReturn.setContentAreaFilled(false);

      panel_Setting.add(btnReturn);
      btnReturn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.exitSetting();
         }
      });
   }
   
   
   public void setMain(MainProcess main) {
      this.main = main;
   }
}