package softstory;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * QuestionView class is used to send Question
 */
public class QuestionView extends JFrame{
   
   
   private MainProcess main;

   Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 40);
   Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);
   Font font2 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 13);
   String[] msg=new String[20];
   
   private ImageIcon img;
   private JButton btnReturn,btnOk;
   static String lan,cour,title;
   
   static boolean asking = false; //asking:true, answered person: false

   /*
    * JFrame for Question window
    */
   
   QuestionView(){
      img = new ImageIcon("images//Question_background.png");
      
      setTitle("Ask your questions!");
      setSize(1300, 700);
      setLocation(200, 70);

      // panel
      JPanel panel_Question = new JPanel() {
         public void paintComponent(Graphics g) {
            
            g.drawImage(img.getImage(),0,0,null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };
      
      
      placeQuestionPanel(panel_Question);

      // add
      add(panel_Question);

      // visiible
      setVisible(true);
 
   }
   
   /*
    * make panel for Question window frame
    */
   public void placeQuestionPanel(JPanel panel_Question) {
      Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 30);
      
      panel_Question.setLayout(null);
      
      JLabel LanguageLabel = new JLabel("Language: ");
      LanguageLabel.setFont(font1);
       LanguageLabel.setBounds(10,200,230,40);
       panel_Question.add(LanguageLabel);
        
       String data[]={"C","C++","Java","JacaScript","Python","HTML","Others.."};
       JComboBox<String> combo;
       combo=new JComboBox<String>(data);       
       combo.setFont(font2);
       combo.setBounds(230,200,180,40);
       panel_Question.add(combo);


         
       JLabel CourseLabel1 = new JLabel("Course: ");
       CourseLabel1.setFont(font1);
       CourseLabel1.setBounds(10,300,140,40);
       panel_Question.add(CourseLabel1);
       
       String data2[]={"컴퓨터프로그래밍","웹프로그래밍","이산수학","소프트웨어구현패턴","로봇공학","확률통계","자료구조","객체지향프로그래밍","운영체제","컴퓨터네트워크","알고리즘","데이터베이스"
               ,"모바일프로그래밍","소프트웨어산업세미나","소프트웨어공학","컴퓨터그래픽스","고급웹프로그래밍","분산시스템"
               ,"컴퓨터구조","데이터마이닝","멀티미디어","소프트웨어신기술특론","컴퓨터보안","HCI"};
          JComboBox<String> combo2;
          combo2=new JComboBox<String>(data2);
          combo2.setFont(font2);
          combo2.setBounds(230,300,200,40);
          panel_Question.add(combo2);
         
          /*
           * outputs the value you selected
           */
          combo.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
                 lan=combo.getSelectedItem().toString();
              }
           });

           combo2.addActionListener(new ActionListener(){
              public void actionPerformed(ActionEvent e){
                 cour=combo2.getSelectedItem().toString();
              }
           });

         
       JLabel TitleLabel = new JLabel("Title: ");
       TitleLabel.setFont(font1);
       TitleLabel.setBounds(10,400,200,40);
       panel_Question.add(TitleLabel);
        
       JTextField TitleText = new JTextField(20);
       TitleText.setBounds(250, 400, 340, 25);
       panel_Question.add(TitleText);
         
      btnReturn = new JButton(new ImageIcon("images//back.png"));
      btnReturn.setBorderPainted(false);
      btnReturn.setFocusPainted(false);
      btnReturn.setContentAreaFilled(false);
      btnReturn.setBounds(1200,600, 60, 60);
      
      panel_Question.add(btnReturn);
      btnReturn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.exitQuestion();
         }
      });
      
      btnOk = new JButton(new ImageIcon("images//ok.png"));
      btnOk.setBounds(340, 480, 160, 160);
      btnOk.setBackground(Color.white);
      btnOk.setBorderPainted(false);
      btnOk.setFocusPainted(false);
      btnOk.setContentAreaFilled(false);

      /*
       * action when you click OK button
       * asking=true mean "I am the one who asked"
       *send MainProcess acting as a client
       *start with "QUSTION1"
       */
      panel_Question.add(btnOk);
      btnOk.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            asking = true;
             MainProcess.out.println("QUESTION1:"+MainView.id+":"+lan+":"+cour+":"+TitleText.getText());
         }
      });

   }
public void gui(String l,String c,String t)
{
   JOptionPane.showMessageDialog(null,l+c+t);
}
   
   public void setMain(MainProcess main) throws IOException {
      this.main = main;
      
 
   }
}