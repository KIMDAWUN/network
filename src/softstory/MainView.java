package softstory;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainView extends JFrame{
	private MainProcess main;
	 private ImageIcon img;
	 
	MainView(String ID) {
		img = new ImageIcon("images//background.png");
		
		setTitle("Soft Story!");
		setSize(1300, 700);
		setLocation(200, 70);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// panel
		JPanel panel_main =  new JPanel(){
			public void paintComponent(Graphics g){
				
				g.drawImage(img.getImage(),0,0,null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		placeMainPanel(panel_main,ID);

		// add
		add(panel_main);

		// visiible
		setVisible(true);
	}

	public void placeMainPanel(JPanel panel_main, String ID){
		Font font_title = new Font("궁서체", Font.CENTER_BASELINE, 60);
	      Font font = new Font("궁서체", Font.CENTER_BASELINE, 40);

		panel_main.setLayout(null);
		JLabel userLabel = new JLabel(ID+" 님 환영합니다.");
		userLabel.setFont(font);
		userLabel.setBounds(20, 30, 500, 40);
		panel_main.add(userLabel);

		JButton QuestionButton = new JButton("질문하기");
		QuestionButton.setFont(font);
		QuestionButton.setBounds(20,130,400,80);
		panel_main.add(QuestionButton);

		QuestionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showQuestion();
			}
		});

		JButton ScheduleButton = new JButton("스케줄");
		ScheduleButton.setFont(font);
		ScheduleButton.setBounds(20,230,400,80);
		panel_main.add(ScheduleButton);

		ScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSchedule();
			}
		});

		JButton MessengerButton = new JButton("메신저");
		MessengerButton.setFont(font);
		MessengerButton.setBounds(20,330,400,80);
		panel_main.add(MessengerButton);

		MessengerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMessenger();
			}
		});

		JButton SettingButton = new JButton("설정");
		SettingButton.setFont(font);
		SettingButton.setBounds(20,430,400,80);
		panel_main.add(SettingButton);

		SettingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSetting();
			}
		});
	}
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
}
