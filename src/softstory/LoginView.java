package softstory;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

public class LoginView extends JFrame{
	
	private MainProcess main;
	
	private JLabel userLabel, passLabel;
	private JButton btnLogin, btnInit, btnJoin, btnExit;
	private JPasswordField passText;
	private JTextField userText;
	private boolean bLoginCheck;
	private ImageIcon img;
	
	Font font = new Font("맑은 고딕", Font.CENTER_BASELINE, 50);
	
	public static void main(String[] args) {
		new LoginView();
	}

	LoginView(){
		img = new ImageIcon("images//background.png");
		
		setTitle("Welcome to Soft Story! : )");
		setSize(1300, 700);
		setResizable(false);
		setLocation(200, 70);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel
		JPanel panel_login = new JPanel(){
			public void paintComponent(Graphics g){
				
				g.drawImage(img.getImage(),0,0,null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		placeLoginPanel(panel_login);
		
		// add
		add(panel_login);
		
		// visible
		setVisible(true);
	}

	public void placeLoginPanel(JPanel panel_login) {
		panel_login.setLayout(null);
		
		userLabel = new JLabel("ID: ");
		userLabel.setFont(font);
		userLabel.setBounds(10, 250, 130, 50);
		panel_login.add(userLabel);
		
		passLabel = new JLabel("PW: ");
		passLabel.setFont(font);
		passLabel.setBounds(10, 350, 130, 50);
		panel_login.add(passLabel);
		
		userText = new JTextField(20);
		userText.setBounds(150, 250, 250, 50);
		panel_login.add(userText);

		passText = new JPasswordField(20);
		passText.setBounds(150, 350, 250, 50);
		panel_login.add(passText);
		
		passText.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();		
			}
		});
		
		//reset 버튼을 누를 경우 입력 정보 초기화
		btnInit = new JButton(new ImageIcon("images//reset.png"));
		btnInit.setBounds(10, 480, 160, 160);
		btnInit.setBackground(Color.white);
		btnInit.setBorderPainted(false);
		btnInit.setFocusPainted(false);
		btnInit.setContentAreaFilled(false);

		panel_login.add(btnInit);
		btnInit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userText.setText("");
				passText.setText("");
			}
		});
		
		//로그인버튼을 누르면 아이디 및 비밀번호를 확인함
		btnLogin = new JButton(new ImageIcon("images//login.png"));
		btnLogin.setBounds(175, 480, 160, 160);
		btnLogin.setBackground(Color.white);
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setContentAreaFilled(false);
		
		panel_login.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		
		//회원가입 버튼을 누를시 회원가입 창이 뜸
		btnJoin = new JButton(new ImageIcon("images//joining.png"));
		btnJoin.setBounds(340, 480, 160, 160);
		btnJoin.setBackground(Color.white);
		btnJoin.setBorderPainted(false);
		btnJoin.setFocusPainted(false);
		btnJoin.setContentAreaFilled(false);

		panel_login.add(btnJoin);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//회원가입 창 뜨게 하기
				main.showJoiningView();
			}
		});
		
		//오른쪽 하단의 종료 버튼을 누를시 종료됨
		btnExit = new JButton(new ImageIcon("images//exit.png"));
		btnExit.setBounds(1200,570, 80, 80);
		btnExit.setBackground(Color.white);
		btnExit.setBorderPainted(false);
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);

		panel_login.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public boolean loginCheck(){
		   boolean flag = false;
		   Connection con = null;
		   Statement stmt = null; //데이터를 전송하는 객체
		   ResultSet rs = null; //데이터 주소값 이용
		   try {
			   String userId = null;
			   String userPW = null;
			   String id = userText.getText();
			   char[] pw = passText.getPassword();
			   String sql = "select * from member where id='" + id + "'";
		   
			   Class.forName("com.mysql.jdbc.Driver");
			   con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/softstory", "root", "12345");
			   stmt = (Statement) con.createStatement();
			   rs = stmt.executeQuery(sql);
			   int num = 0;
			   while(rs.next()){
				   userId = rs.getString(1);//String userId = rs.getString("id");
				   userPW = rs.getString(2);
				   System.out.println(userId);
				   System.out.println(userPW);
				   num++;
			   }
			   System.out.println(num);
			   if(num == 1){
				   if(new String(passText.getPassword()).equals(userPW)){
					   System.out.println("log-in success.");
					   flag = true;
				   }
			   }
			   else{
				   //로그인에 문제 생겼다는 팝업창 띄워주기(회원정보없음)
				   flag = false;
			   }
		   } catch (Exception e) {
				System.out.println(e);
				flag = false;
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					if (con != null)
						con.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		 return flag;
	 }
	
	//DB연동해서 로그인 하기
	public void isLoginCheck(){
		if(loginCheck()){  
			JOptionPane.showMessageDialog(null, "Welcome "+userText.getText()+" !");
			bLoginCheck = true;
			
			if(isLogin()){
				main.showMainView(userText.getText(), "127.0.0.1", 9001);
			}	
			
		}else{
			JOptionPane.showMessageDialog(null, "Please check your ID or PW");
		}
	}

	public void setMain(MainProcess main) {
		this.main = main;
	}
	

	public boolean isLogin() {		
		return bLoginCheck;
	}
}

