package softstory;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuestionView extends JFrame{
   
   private MainProcess main;
   
   private ImageIcon img;
   private JButton btnReturn;
   
   QuestionView(){
      img = new ImageIcon("images//background.png");
      
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
   
   public void placeQuestionPanel(JPanel panel_Question) {
      Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 30);
      
      panel_Question.setLayout(null);
      
      JLabel LanguageLabel = new JLabel("Language: ");
      LanguageLabel.setFont(font1);
       LanguageLabel.setBounds(10,200,200,40);
       panel_Question.add(LanguageLabel);
        
       Choice list;//언어
       list = new Choice();
       list.add("C");
       list.add("C++");
       list.add("Java");
       list.add("JavaScript");
       list.add("Python");
       list.add("PHP");
       list.add("CSS");
       list.add("HTML");
       list.add("Others..");//기타 언어 처리 어떻게 할 것인가
         
       list.setBounds(230,200,140,40);
       panel_Question.add(list);
        
       JLabel CourseLabel = new JLabel("Course: ");
       CourseLabel.setFont(font1);
       CourseLabel.setBounds(10,300,200,40);
       panel_Question.add(CourseLabel);
        
       Choice list2;//과목
       list2 = new Choice();
       list2.add("컴퓨터프로그래밍");
        list2.add("웹프로그래밍");
       list2.add("이산수학");
       list2.add("소프트웨어구현패턴");
       list2.add("로봇공학");
       list2.add("확률통계");
       list2.add("자료구조");
       list2.add("객체지향프로그래밍");
       list2.add("운영체제");
       list2.add("컴퓨터네트워크");
       list2.add("알고리즘");
       list2.add("데이터베이스");
       list2.add("모바일프로그래밍");
       list2.add("소프트웨어산업세미나");
       list2.add("소프트웨어공학");
       list2.add("컴퓨터그래픽스");
       list2.add("고급웹프로그래밍");
       list2.add("분산시스템");
       list2.add("컴퓨터구조");
       list2.add("데이터마이닝");
       list2.add("멀티미디어");
       list2.add("소프트웨어신기술특론");
       list2.add("컴퓨터보안");
       list2.add("HCI");
        
       list2.setBounds(230,300,140,40);
       panel_Question.add(list2);
        
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
   }
   
   public void setMain(MainProcess main) {
      this.main = main;
   }
}