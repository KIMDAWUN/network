package softstory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Satisfaction extends JFrame implements ActionListener{
	private MainProcess main;
	
	JLabel text;
	JButton[] star = new JButton[5];
	private JButton Submit;
	private JButton Reset;
	boolean[] flag = {false, false, false, false, false};
	SatisDTO sdto = new SatisDTO(); //create DTO object
	int starNum = 0;
	String answeredID = "";
	
	Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);
	
	Satisfaction(String id){
		setTitle("Satisfaction Survey");
		setSize(500, 250);
		setResizable(false);
		setLocation(200, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		answeredID = id;
		
		//Panel
		JPanel panel_survey = new JPanel(){
			
		};
		
		placeSurveyPanel(panel_survey, answeredID);
		
		// add
		add(panel_survey);
		
		// visible
		setVisible(true);
		
	}
	
	/**
	 * function for panel that show the satisfaction survey view
	 * after one to one question chat
	 * if user clicked nth star, first~nth star selected
	 * */
	public void placeSurveyPanel(JPanel panel_survey, String answeredID) {
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
		
		//submit the number of selected star
		Submit = new JButton("Submit");
		Submit.setBounds(255, 160, 100, 30);
		panel_survey.add(Submit);
		Submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {     	 
	        	 //put number of star that received from user
				sdto.setId(answeredID);
				sdto.setStar(starNum);
	       		 
	       		 
	       	  try{
	       		  SatisInsertDAO.create(sdto);
	       	  }catch(Exception e1){
	       		  e1.printStackTrace();
	       	  }
	       	  dispose();
			}
		});
		
		
		//reset the selected star
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

	public void setMain(MainProcess main) {
		this.main = main;
		
	}

}



