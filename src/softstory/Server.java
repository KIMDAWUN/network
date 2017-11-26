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
	JTextArea textArea; // Ŭ���̾�Ʈ �� ���� �޽��� ���

	private ServerSocket socket; //��������
	private Socket soc; // ������� 
	
	UserDTO udto = new UserDTO();

	private Vector vc = new Vector(); // ����� ����ڸ� ������ ����
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
			socket = new ServerSocket(PORT); // ������ ��Ʈ ���ºκ�
			
			if(socket!=null) { // socket �� ���������� ��������
				Connection();
			}
		} catch (IOException e) {
			textArea.append("������ �̹� ������Դϴ�...\n");
		}
	}

	private void Connection() {

		Thread th = new Thread(new Runnable() { // ����� ������ ���� ������

			@Override
			public void run() {
				while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
					try {
						textArea.append("����� ���� �����...\n");
						soc = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
						textArea.append("����� ����!!\n");

						//UserInfo user = new UserInfo(soc, vc); // ����� ���� ������ �ݹ� ������Ƿ�, user Ŭ���� ���·� ��ü ����
						                                                     // �Ű������� ���� ����� ���ϰ�, ���͸� ��Ƶд�
						
						UserInfo user = new UserInfo(soc, vc);
						vc.add(user); // �ش� ���Ϳ� ����� ��ü�� �߰�
						
						
						
						user.start(); // ���� ��ü�� ������ ����

					} catch (IOException e) {
						textArea.append("!!!! accept ���� �߻�... !!!!\n");
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

		public UserInfo(Socket soc, Vector vc){ // �����ڸ޼ҵ�
		
			// �Ű������� �Ѿ�� �ڷ� ����
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

				id = dis.readUTF(); // ������� �г��� �޴ºκ�
				textArea.append("������ ID " +id+"\n");
				
				//send_Message("���� ���� �Ǿ����ϴ�"); // ����� ����ڿ��� ���������� �˸�
				
				

			} catch (Exception e) {
				textArea.append("��Ʈ�� ���� ����\n");
			}
			return id;

		}

		/*public void InMessage(String str) {
			textArea.append("����ڷκ��� ���� �޼��� : " + str+"\n");
			// ����� �޼��� ó��
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
				textArea.append("�޽��� �۽� ���� �߻�\n");	
			}
		}

		public void run() // ������ ����
		{

			while (true) {
				try {
					
					// ����ڿ��� �޴� �޼���
					String msg = dis.readUTF();
					//InMessage(msg);
					
				} 
				catch (IOException e) 
				{
					
					try {
						dos.close();
						dis.close();
						user_socket.close();
						vc.removeElement( this ); // �������� ���� ��ü�� ���Ϳ��� �����
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
						
						textArea.append(vc.size() +" : ���� ���Ϳ� ����� ����� ��\n");
						textArea.append("����� ���� ������ �ڿ� �ݳ�\n");
						break;
					
					} catch (Exception ee) {
					
					}// catch�� ��
				}// �ٱ� catch����

			}
			
			
			
		}// run�޼ҵ� ��

	} // ���� userinfoŬ������
	
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

