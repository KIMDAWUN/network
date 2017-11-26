package softstory;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Server extends JFrame {
	private static final int PORT = 9001;

	private JPanel contentPane;
	JTextArea textArea; // 클라이언트 및 서버 메시지 출력

	private ServerSocket socket; //서버소켓
	private Socket soc; // 연결소켓 
	
	UserDTO udto = new UserDTO();

	private Vector vc = new Vector(); // 연결된 사용자를 저장할 벡터
	private static HashMap<Socket, String> users = new HashMap<Socket, String>(); 

	public static void main(String[] args) {
		Server frame = new Server();
		frame.setVisible(true);
		
		
	}

	public Server() {
		init();
	}

	private void init() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JScrollPane js = new JScrollPane();
				
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		js.setBounds(0, 0, 264, 254);
		contentPane.add(js);
		js.setViewportView(textArea);

		server_start();
		
	}

	private void server_start() {
		try {
			socket = new ServerSocket(PORT); // 서버가 포트 여는부분
			
			if(socket!=null) { // socket 이 정상적으로 열렸을때
				Connection();
			}
		} catch (IOException e) {
			textArea.append("소켓이 이미 사용중입니다...\n");
		}
	}

	private void Connection() {

		Thread th = new Thread(new Runnable() { // 사용자 접속을 받을 스레드

			@Override
			public void run() {
				while (true) { // 사용자 접속을 계속해서 받기 위해 while문
					try {
						textArea.append("사용자 접속 대기중...\n");
						soc = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
						textArea.append("사용자 접속!!\n");

						//UserInfo user = new UserInfo(soc, vc); // 연결된 소켓 정보는 금방 사라지므로, user 클래스 형태로 객체 생성
						                                                     // 매개변수로 현재 연결된 소켓과, 벡터를 담아둔다
						
						UserInfo user = new UserInfo(soc, vc);
						vc.add(user); // 해당 벡터에 사용자 객체를 추가
						
						
						
						user.start(); // 만든 객체의 스레드 실행

					} catch (IOException e) {
						textArea.append("!!!! accept 에러 발생... !!!!\n");
					} 

				}

			}
		});

		th.start();
		

	}

	class UserInfo extends Thread {

		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private Socket user_socket;
		private Vector user_vc;
		private String id = "";
		private String ID = "";

		public UserInfo(Socket soc, Vector vc){ // 생성자메소드
		
			// 매개변수로 넘어온 자료 저장
			this.user_socket = soc;
			this.user_vc = vc;

			ID = User_network();
			
			this.ID = ID;
			users.put(soc, ID);
			
			printUserID();
			udto.setId(ID);
			try{
				UserInsertDAO.create(udto);
			}catch(Exception e){
				e.printStackTrace();
			}
			

		}

		public String User_network() {
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);

				id = dis.readUTF(); // 사용자의 닉네임 받는부분
				textArea.append("접속자 ID " +id+"\n");
				
				//send_Message("정상 접속 되었습니다"); // 연결된 사용자에게 정상접속을 알림
				
				

			} catch (Exception e) {
				textArea.append("스트림 셋팅 에러\n");
			}
			return id;

		}

		/*public void InMessage(String str) {
			textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
			// 사용자 메세지 처리
			broad_cast(str);

		}*/

		/*public void broad_cast(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserInfo imsi = (UserInfo) user_vc.elementAt(i);
				imsi.send_Message(Nickname+" : "+str);

			}

		}*/

		public void send_Message(String str) {
			try {
				dos.writeUTF(str);
			} 
			catch (IOException e) {
				textArea.append("메시지 송신 에러 발생\n");	
			}
		}

		public void run() // 스레드 정의
		{

			while (true) {
				try {
					
					// 사용자에게 받는 메세지
					String msg = dis.readUTF();
					//InMessage(msg);
					
				} 
				catch (IOException e) 
				{
					
					try {
						dos.close();
						dis.close();
						user_socket.close();
						vc.removeElement( this ); // 에러가난 현재 객체를 벡터에서 지운다
						//users.remove(this);
						String currentID = this.id;
						users.values().removeAll(Collections.singleton(currentID));
						printUserID();
						
						udto.setId(ID);
						try{
							UserDeleteDAO.create(udto);
						}catch(Exception e1){
							e1.printStackTrace();
						}
						
						textArea.append(vc.size() +" : 현재 벡터에 담겨진 사용자 수\n");
						textArea.append("사용자 접속 끊어짐 자원 반납\n");
						break;
					
					} catch (Exception ee) {
					
					}// catch문 끝
				}// 바깥 catch문끝

			}
			
			
			
		}// run메소드 끝

	} // 내부 userinfo클래스끝
	
	/*public void printUserID(){
		Iterator<Vector> iterator = users.keySet().iterator();
		while(iterator.hasNext()){
			Vector key = (Vector)iterator.next();
			System.out.println(users.get(key));
		}
	}*/
	public void printUserID(){
		for(Map.Entry<Socket, String> elem : users.entrySet()){
			System.out.println(elem.getKey() + " and " + elem.getValue());
		}
		System.out.println("==================");
	}
	
	

}

