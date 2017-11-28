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
import java.sql.ResultSet;

public class MainView extends JFrame{
	 private MainProcess main;
	 private ImageIcon img,img2;
	 
	 private String id;
	 private String ip;
	 private int port;
	 
	 private Socket socket; // 연결소켓
	 private InputStream is;
	 private OutputStream os;
	 private DataInputStream dis;
	 private DataOutputStream dos;
	 
	 
	MainView(String ID, String ip, int port) {
		
		this.id = ID;
		this.ip = ip;
		this.port = port;
		
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

		// visible
		setVisible(true);
		
		//network
		network();

	}
	
	public void network(){
		try {
			socket = new Socket(ip, port);
			if (socket != null){ //연결되었을때
				Connection(); // 연결 메소드 호출
			}
		} catch (UnknownHostException e) {

		} catch (IOException e) {
			System.out.println("socket connection error");
		}

	}
	
	public void Connection() {

		try { // 스트림 설정
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

		} catch (IOException e) {
			//textArea.append("스트림 설정 에러!!\n");
			System.out.println("stream setting error");
		}
		
		send_Message(id); // 정상적으로 연결되면 나의 id를 전송

		/*Thread th = new Thread(new Runnable() { // 스레드를 돌려서 서버로부터 메세지를 수신
					@Override
					public void run() {
						while (true) {
							try {
								String msg = dis.readUTF(); // 메세지를 수신한다
								//textArea.append(msg + "\n");
							} catch (IOException e) {
								//textArea.append("메세지 수신 에러!!\n");
								// 서버와 소켓 통신에 문제가 생겼을 경우 소켓을 닫는다
								try {
									os.close();
									is.close();
									dos.close();
									dis.close();
									socket.close();
									break; // 에러 발생하면 while문 종료
								} catch (IOException e1) {
								}
							}
						} // while문 끝
					}// run메소드 끝
				});
		th.start();*/

	}
	
	public void send_Message(String str) { // 서버로 메세지를 보내는 메소드
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			//textArea.append("메세지 송신 에러!!\n");
		}
	}



	public void placeMainPanel(JPanel panel_main, String ID){
		Font font_title = new Font("궁서체", Font.CENTER_BASELINE, 60);
	      Font font = new Font("궁서체", Font.CENTER_BASELINE, 40);

		panel_main.setLayout(null);
		JLabel userLabel = new JLabel(ID+" 님 환영합니다.");
		userLabel.setFont(font);
		userLabel.setBounds(20, 30, 1000, 40);
		panel_main.add(userLabel);

		JButton QuestionButton = new JButton("질문하기");
		QuestionButton.setFont(font);
		QuestionButton.setBounds(20,130,400,80);
		panel_main.add(QuestionButton);

		QuestionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{main.showQuestion();
			}
				catch(Exception i)
				{
					System.out.println("오류");
				}
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
		
		JButton Egg= new JButton(new ImageIcon("images//egg.png"));
		Egg.setBounds(450,150,350,350);
		Egg.setBackground(Color.yellow);
		Egg.setBorderPainted(false);
		Egg.setFocusPainted(false);
		Egg.setContentAreaFilled(false);
		panel_main.add(Egg);
		Egg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int eggs=getegg();
				JOptionPane.showMessageDialog(null,"My egg is "+eggs+" .");
			}
		});
	}
	public int getegg()
	{
		int egg=0;
		Connection con = null;
		   Statement stmt = null; //데이터를 전송하는 객체
		   ResultSet rs = null; //데이터 주소값 이용
		   try {
			   String sql = "select egg from member where id='" + id + "'";
		   
			   Class.forName("com.mysql.jdbc.Driver");
			   con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/softstory", "root", "root");
			   stmt = (Statement) con.createStatement();
			   rs = stmt.executeQuery(sql);
			   int num = 0;
			   while(rs.next()){
				   egg = rs.getInt(1);  
				}
			   return egg;
		   }catch(Exception e){
			   return 0;
		   }
	}
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
}
