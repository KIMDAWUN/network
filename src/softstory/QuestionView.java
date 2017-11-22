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
      Font font1 = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 30);
      
      panel_Question.setLayout(null);
      
      JLabel LanguageLabel = new JLabel("Language: ");
      LanguageLabel.setFont(font1);
       LanguageLabel.setBounds(10,200,200,40);
       panel_Question.add(LanguageLabel);
        
       Choice list;//���
       list = new Choice();
       list.add("C");
       list.add("C++");
       list.add("Java");
       list.add("JavaScript");
       list.add("Python");
       list.add("PHP");
       list.add("CSS");
       list.add("HTML");
       list.add("Others..");//��Ÿ ��� ó�� ��� �� ���ΰ�
         
       list.setBounds(230,200,140,40);
       panel_Question.add(list);
        
       JLabel CourseLabel = new JLabel("Course: ");
       CourseLabel.setFont(font1);
       CourseLabel.setBounds(10,300,200,40);
       panel_Question.add(CourseLabel);
        
       Choice list2;//����
       list2 = new Choice();
       list2.add("��ǻ�����α׷���");
        list2.add("�����α׷���");
       list2.add("�̻����");
       list2.add("����Ʈ���������");
       list2.add("�κ�����");
       list2.add("Ȯ�����");
       list2.add("�ڷᱸ��");
       list2.add("��ü�������α׷���");
       list2.add("�ü��");
       list2.add("��ǻ�ͳ�Ʈ��ũ");
       list2.add("�˰���");
       list2.add("�����ͺ��̽�");
       list2.add("��������α׷���");
       list2.add("����Ʈ���������̳�");
       list2.add("����Ʈ�������");
       list2.add("��ǻ�ͱ׷��Ƚ�");
       list2.add("��������α׷���");
       list2.add("�л�ý���");
       list2.add("��ǻ�ͱ���");
       list2.add("�����͸��̴�");
       list2.add("��Ƽ�̵��");
       list2.add("����Ʈ����ű��Ư��");
       list2.add("��ǻ�ͺ���");
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