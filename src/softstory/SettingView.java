package softstory;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SettingView extends JFrame{
	private MainProcess main;
	private JPanel contentPane;
	private JButton btnReturn;
	
	SettingView(){
		setTitle("Setting");
		setSize(700, 500);
		setLocation(200, 70);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnModification = new JButton("Modification");
		btnModification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame f=new JFrame("학생 수강 프로그램");
				  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				    JTextArea Jta=new JTextArea() ;
				  Jta.setRows(3);
				  JPanel jp=new JPanel();
				  JPanel jp1=new JPanel();
				  JPanel jp2=new JPanel();
				  JPanel jp3=new JPanel();
				  jp.setLayout(new GridLayout(5,2));
				  JLabel jl1=new JLabel("학번");
				        JLabel jl2=new JLabel("성명");
				  JLabel jl3=new JLabel("학과");
				  JLabel jl4=new JLabel("학년");
				  JLabel jl5=new JLabel("수강과목");
				  JTextField jf1=new JTextField(); 
				JTextField jf2=new JTextField();
				JRadioButton jb1=new JRadioButton("1학년");
				JRadioButton jb2=new JRadioButton("2학년");
				JRadioButton jb3=new JRadioButton("3학년");
				JRadioButton jb4=new JRadioButton("4학년");
				ButtonGroup group = new ButtonGroup();
				    group.add(jb1);
				    group.add(jb2);
				    group.add(jb3);
				    group.add(jb4);
				jp1.add(jb1);jp1.add(jb2);jp1.add(jb3);jp1.add(jb4);
				JCheckBox jc1=new JCheckBox("VB"); 
				JCheckBox jc2=new JCheckBox("DBMS");
				JCheckBox jc3=new JCheckBox("JAVA");  
				JCheckBox jc4=new JCheckBox("LINUX");
				ButtonGroup group1 = new ButtonGroup();
				    group1.add(jc1);
				    group1.add(jc2);
				    group1.add(jc3);
				    group1.add(jc4);
				jp2.add(jc1);jp2.add(jc2);jp2.add(jc3);jp2.add(jc4);

				Vector<String> data = new Vector<String>();
				data.add("컴퓨터공학");data.add("물리학");data.add("경제학");
				JComboBox mycom = new JComboBox(data);
				JButton jbu1=new JButton("입력");
				JButton jbu2=new JButton("취소");
				jp.add(jl1);jp.add(jf1);jp.add(jl2);jp.add(jf2);jp.add(jl3);jp.add(mycom);
				jp.add(jl4);jp.add(jp1);jp.add(jl5);jp.add(jp2);
				jp3.add(jbu1);jp3.add(jbu2);
				  f.getContentPane().add(Jta,BorderLayout.NORTH);
				  f.getContentPane().add(jp,BorderLayout.CENTER);
				  f.getContentPane().add(jp3,BorderLayout.SOUTH);
				        f.pack();
				  f.setVisible(true);
				 }
				}
		);

		menuBar.add(btnModification);
		
		JMenu mnPush = new JMenu("push");
		mnPush.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "알림메세지입니다.","알림메세지",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		JButton btnMyEggs = new JButton("My eggs");
		btnMyEggs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "My eggs are %d now.");
			}
		});
		menuBar.add(btnMyEggs);
		menuBar.add(mnPush);
		
		JMenuItem mntmYes = new JMenuItem("yes");
		mnPush.add(mntmYes);
		
		JMenuItem mntmNo = new JMenuItem("no");
		mnPush.add(mntmNo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		// panel
		JPanel panel_Setting = new JPanel();
		placeSettingPanel(panel_Setting);

		// add
		add(panel_Setting);

		// visiible
		setVisible(true);
	}
	
	public void placeSettingPanel(JPanel panel_Setting) {
		panel_Setting.setLayout(null);
		
		btnReturn = new JButton(new ImageIcon("images//back.png"));
		btnReturn.setBounds(600,400, 50, 50);
		btnReturn.setBackground(Color.white);
		btnReturn.setBorderPainted(false);
		btnReturn.setFocusPainted(false);
		btnReturn.setContentAreaFilled(false);

		panel_Setting.add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.exitSetting();
			}
		});
	}
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
}

