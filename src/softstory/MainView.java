package softstory;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.*;
import java.net.*;

public class MainView extends JFrame{
	 private MainProcess main;
	 private ImageIcon img;
	 
	 private String id;
	 private String ip;
	 private int port;
	 
	 private Socket socket; // �������
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
			if (socket != null){ //����Ǿ�����
				Connection(); // ���� �޼ҵ� ȣ��
			}
		} catch (UnknownHostException e) {

		} catch (IOException e) {
			System.out.println("socket connection error");
		}

	}
	
	public void Connection() {

		try { // ��Ʈ�� ����
			is = socket.getInputStream();
			dis = new DataInputStream(is);

			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

		} catch (IOException e) {
			//textArea.append("��Ʈ�� ���� ����!!\n");
			System.out.println("stream setting error");
		}
		
		send_Message(id); // ���������� ����Ǹ� ���� id�� ����

		/*Thread th = new Thread(new Runnable() { // �����带 ������ �����κ��� �޼����� ����

					@Override
					public void run() {
						while (true) {

							try {
								String msg = dis.readUTF(); // �޼����� �����Ѵ�
								//textArea.append(msg + "\n");
							} catch (IOException e) {
								//textArea.append("�޼��� ���� ����!!\n");
								// ������ ���� ��ſ� ������ ������ ��� ������ �ݴ´�
								try {
									os.close();
									is.close();
									dos.close();
									dis.close();
									socket.close();
									break; // ���� �߻��ϸ� while�� ����
								} catch (IOException e1) {

								}

							}
						} // while�� ��

					}// run�޼ҵ� ��
				});

		th.start();*/

	}
	
	public void send_Message(String str) { // ������ �޼����� ������ �޼ҵ�
		try {
			dos.writeUTF(str);
		} catch (IOException e) {
			//textArea.append("�޼��� �۽� ����!!\n");
		}
	}



	public void placeMainPanel(JPanel panel_main, String ID){
		Font font_title = new Font("�ü�ü", Font.CENTER_BASELINE, 60);
	      Font font = new Font("�ü�ü", Font.CENTER_BASELINE, 40);

		panel_main.setLayout(null);
		JLabel userLabel = new JLabel(ID+" �� ȯ���մϴ�.");
		userLabel.setFont(font);
		userLabel.setBounds(20, 30, 1000, 40);
		panel_main.add(userLabel);

		JButton QuestionButton = new JButton("�����ϱ�");
		QuestionButton.setFont(font);
		QuestionButton.setBounds(20,130,400,80);
		panel_main.add(QuestionButton);

		QuestionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showQuestion();
			}
		});

		JButton ScheduleButton = new JButton("������");
		ScheduleButton.setFont(font);
		ScheduleButton.setBounds(20,230,400,80);
		panel_main.add(ScheduleButton);

		ScheduleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showSchedule();
			}
		});

		JButton MessengerButton = new JButton("�޽���");
		MessengerButton.setFont(font);
		MessengerButton.setBounds(20,330,400,80);
		panel_main.add(MessengerButton);

		MessengerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.showMessenger();
			}
		});

		JButton SettingButton = new JButton("����");
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
