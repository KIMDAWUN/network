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
   Font font = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 40);
   Font font1 = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 20);
   Font font2 = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 13);
   
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
                  Choice list;//�г�
                  list = new Choice();
                  list.add("1�г�");
                  list.add("2�г�");
                  list.add("3�г�");
                  list.add("4�г�");
                  list.add("���п���");
                  list.add("������");
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
                  courseList[0] = new JCheckBox("��ǻ�����α׷���");
                  courseList[1] = new JCheckBox("�����α׷���");
                  courseList[2] = new JCheckBox("�̻����");
                  courseList[3] = new JCheckBox("����Ʈ���������");
                  courseList[4] = new JCheckBox("�κ�����");
                  courseList[5] = new JCheckBox("Ȯ�����");
                  courseList[6] = new JCheckBox("�ڷᱸ��");
                  courseList[7] = new JCheckBox("��ü�������α׷���");
                  courseList[8] = new JCheckBox("�ü��");
                  courseList[9] = new JCheckBox("��ǻ�ͳ�Ʈ��ũ");
                  courseList[10] = new JCheckBox("�˰���");
                  courseList[11] = new JCheckBox("�����ͺ��̽�");
                  courseList[12] = new JCheckBox("��������α׷���");
                  courseList[13] = new JCheckBox("����Ʈ���������̳�");
                  courseList[14] = new JCheckBox("����Ʈ�������");
                  courseList[15] = new JCheckBox("��ǻ�ͱ׷��Ƚ�");
                  courseList[16] = new JCheckBox("��������α׷���");
                  courseList[17] = new JCheckBox("�л�ý���");
                  courseList[18] = new JCheckBox("��ǻ�ͱ���");
                  courseList[19] = new JCheckBox("�����͸��̴�");
                  courseList[20] = new JCheckBox("��Ƽ�̵��");
                  courseList[21] = new JCheckBox("����Ʈ����ű��Ư��");
                  courseList[22] = new JCheckBox("��ǻ�ͺ���");
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
            JOptionPane.showMessageDialog(mntmYes, "push on �Ǿ����ϴ�.", "Title", JOptionPane.INFORMATION_MESSAGE, null);
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
            JOptionPane.showMessageDialog(mntmNo, "push off �Ǿ����ϴ�.", "Title", JOptionPane.INFORMATION_MESSAGE, null);
            dto.setId(MainView.id);
            dto.setPush(0);
            udto.setId(MainView.id);
            udto.setPush(0);
            try{
                     InsertDAO.createPush(dto);
                     UserInsertDAO.createPush(udto);
            }catch(Exception e1){
                     e1.printStackTrace();
                   JOptionPane.showInputDialog(null,"����");
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