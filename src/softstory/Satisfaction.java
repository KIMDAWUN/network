package softstory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Satisfaction extends JFrame implements ActionListener{
	
	JLabel text;
	JButton[] star = new JButton[5];
	private JButton Submit;
	private JButton Reset;
	boolean[] flag = {false, false, false, false, false};
	SatisDTO sdto = new SatisDTO(); //DTO 객체 생성
	int starNum = 0;
	String answeredID = "";
	
	Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);
	
	Satisfaction(){
		setTitle("Satisfaction Survey");
		setSize(500, 250);
		setResizable(false);
		setLocation(200, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel_survey = new JPanel(){
			
		};
		
		placeSurveyPanel(panel_survey);
		
		// add
		add(panel_survey);
		
		// visible
		setVisible(true);
		
	}
	
	public void placeSurveyPanel(JPanel panel_survey) {
		panel_survey.setLayout(null);
		
		text = new JLabel("How satisfied are you with the answer?", SwingConstants.CENTER);
		text.setFont(font1);
		text.setBounds(20,2,450,50);
		panel_survey.add(text);
		
		int xAxis = 120;
		for(int i = 0; i < 5; i++){
			star[i] = new JButton(new ImageIcon("images//emptystar.png"));
			star[i].setRolloverIcon(new ImageIcon("images//fullstar.png"));
			star[i].setBounds(xAxis, 70, 50, 50);
			xAxis = xAxis + 50;
			star[i].setBorderPainted(false);
			star[i].setFocusPainted(false);
			star[i].setContentAreaFilled(false);
			star[i].addActionListener(this);
			panel_survey.add(star[i]);
			
			int num1 = i;
			star[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//System.out.println(num1+1);
					starNum = num1+1;
					ImageIcon image = new ImageIcon("images//fullstar.png");
					for(int j = 0; j <= num1; j++){
						star[j].setIcon(image);
					}
				}
			});
			
			int num2=i;
			star[i].addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e){
					boolean clicked = star[num2].getModel().isPressed();
					//boolean out = star[num2].getModel().isArmed();
					//boolean out1 =star[num2].getModel().isEnabled();
					boolean rollover = star[num2].getModel().isRollover();

					if(clicked){
						for(int j = 0; j <= num2; j++){
							flag[j] = true;
						}
					}
					else{
						if(rollover&&!flag[num2]){
							ImageIcon image = new ImageIcon("images//fullstar.png");
							for(int j = 0; j < num2; j++){
								star[j].setIcon(image);
							}
						}
						else{
							if(!flag[num2] && !rollover){
								ImageIcon image = new ImageIcon("images//emptystar.png");
								for(int j = 0; j < num2; j++){
									star[j].setIcon(image);
								}
							}
						}
					}	
				}
			});
		}
		
		Submit = new JButton("Submit");
		Submit.setBounds(255, 160, 100, 30);
		panel_survey.add(Submit);
		Submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {     	 
	        	 
	        	 //입력된 정보를 가져와 dto에 저장
				//받는사람 아이디랑 star값 전달
				//sdto.setId(answeredID.getText());
				sdto.setStar(starNum);
	       		 
	       		 
	       	  try{
	       		  SatisInsertDAO.create(sdto);
	       	  }catch(Exception e1){
	       		  e1.printStackTrace();
	       	  }

			}
		});
		
		Reset = new JButton("Reset");
		Reset.setBounds(135, 160, 100, 30);
		panel_survey.add(Reset);
		Reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {     	 
				ImageIcon image = new ImageIcon("images//emptystar.png");
				for(int j = 0; j < 5; j++){
					star[j].setIcon(image);
					flag[j] = false;
				}
	       	  try{
	       		  
	       	  }catch(Exception e1){
	       		  e1.printStackTrace();
	       	  }

			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	public static void main(String[] args) {
        Satisfaction satisfaction_survey = new Satisfaction();
    }

}


