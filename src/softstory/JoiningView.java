package softstory;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JoiningView extends JFrame{
	private MainProcess main;
	
	private JButton Submit;
	private JButton Cancel;
	
	DTO dto = new DTO();
	
	Font font = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 40);
	Font font1 = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 20);
	Font font2 = new Font("����ǹ��� �־�", Font.CENTER_BASELINE, 13);
	
	JoiningView(){
		setTitle("Be member of SoftStory!");
		//setSize(1000, 650);
		setSize(1050, 650);
		//setSize(410, 1000);
		setLocation(240, 100);
		//setLocation(400,0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.cyan);
		
		// panel
		JPanel panel_join = new JPanel();
		placeJoiningPanel(panel_join);

		// add
		add(panel_join);

		// visible
		setVisible(true);
	}
	
	public void placeJoiningPanel(JPanel panel_join) {
		panel_join.setLayout(null);

		/**
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		JScrollPane scrollPane=new JScrollPane(panel_join,v,h);
		panel_join.add(scrollPane, BorderLayout.CENTER);
		*/
		JLabel FirstLabel1 = new JLabel("Welcome to Soft Story!");
		JLabel FirstLabel2 = new JLabel("Please type your real information!");
		FirstLabel1.setFont(font);
		FirstLabel2.setFont(font1);
		
		  FirstLabel1.setBounds(10,2,1000,50);
	      panel_join.add(FirstLabel1);
	      FirstLabel2.setBounds(10,55,1000,20);
	      panel_join.add(FirstLabel2);
	      
	      JLabel IDLabel = new JLabel("ID: ");
	      IDLabel.setFont(font1);
	      IDLabel.setBounds(10, 100, 80, 25);
	      panel_join.add(IDLabel);
	      
	      JTextField IDText = new JTextField(20);
	      IDText.setBounds(100, 100, 250, 25);
	      panel_join.add(IDText);
	      
	      JLabel PWLabel = new JLabel("PW: ");
	      PWLabel.setFont(font1);
	      PWLabel.setBounds(10, 140, 80, 25);
	      panel_join.add(PWLabel);

	      JPasswordField PWText = new JPasswordField(20);
	      PWText.setBounds(100, 140, 250, 25);
	      panel_join.add(PWText);
	      
	      JLabel NAMELabel = new JLabel("NAME: ");
	      NAMELabel.setFont(font1);
	      NAMELabel.setBounds(10,180,80,25);
	      panel_join.add(NAMELabel);
	      
	      JTextField NAMEText = new JTextField(20);
	      NAMEText.setBounds(100, 180, 250, 25);
	      panel_join.add(NAMEText);
	   
	      JLabel STLabel = new JLabel("Student Number: ");
	      STLabel.setFont(font1);
	      STLabel.setBounds(10,220,250,25);
	      panel_join.add(STLabel);
	      
	      JTextField STText = new JTextField(20);
	      STText.setBounds(10, 250, 340, 25);
	      panel_join.add(STText);
	      
	      JLabel YEARLabel = new JLabel("Year: ");
	      YEARLabel.setFont(font1);
	      YEARLabel.setBounds(10,300,80,25);
	      panel_join.add(YEARLabel);
	      
	      Choice list;//�г�
	      list = new Choice();
	      list.add("1�г�");
	      list.add("2�г�");
	      list.add("3�г�");
	      list.add("4�г�");
	      list.add("���п���");
	      list.add("������");
	      list.setBounds(100,300,140,25);
	      panel_join.add(list);
	            
	      JLabel LanguageLabel = new JLabel("Confident Language: ");
	      LanguageLabel.setFont(font1);
	      LanguageLabel.setBounds(10,350,250,25);
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
	      languageList[8] = new JCheckBox("Others..");//��Ÿ ��� ó�� ��� �� ���ΰ�
	      
	      int yAxis = 380;
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
	      //TakeLabel.setBounds(10,465,250,50);
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
	      
		//�Է� ���� ����
		Submit = new JButton("Submit");
		Submit.setBounds(500, 500, 150, 30);
		//Submit.setBounds(30, 900, 150, 30);
		panel_join.add(Submit);
		Submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String language = "";
	        	 for(int i = 0; i < 9; i++){
	    			if(languageList[i].isSelected()){
	    				language += languageList[i].getText() + " "; 
	    				//dto.setLanguage(languageList[i].getText());
	    			}
	    		 }
	        	 
	        	 String course = "";
	        	 for(int i = 0; i < 23; i++){
	     			if(courseList[i].isSelected()){
	     				course += courseList[i].getText() + " "; 
	     				//dto.setLanguage(languageList[i].getText());
	     			}
	     		 }
	        	 //�Էµ� ������ ������ dto�� ����
	       		 dto.setId(IDText.getText());
	       		 dto.setPassword(PWText.getText());
	       		 dto.setName(NAMEText.getText());
	       		 dto.setStudentNo(STText.getText());
	       		 dto.setYear(list.getSelectedItem());
	       		 dto.setLanguage(language);
	       		 dto.setCourse(course);
	       		 
	       	  try{
	       		  InsertDAO.create(dto);
	       	  }catch(Exception e1){
	       		  e1.printStackTrace();
	       	  }

			}
		});
				
		//�Է� ���� �ʱ�ȭ
		Cancel = new JButton("Cancel");
		Cancel.setBounds(700, 500, 150, 30);
	    //Cancel.setBounds(210, 900, 150, 30);
		panel_join.add(Cancel);
		Cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showLoginView();
			}
		});
	}

	public void setMain(MainProcess main) {
		this.main = main;
	}
}
