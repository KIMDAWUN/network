package softstory;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MessengerView extends JFrame {
	private MainProcess main;
	
	private JButton btnReturn;
	
	MessengerView(){
		setTitle("Chat with your friend!");
		setSize(1300, 700);
		setLocation(200, 70);

		// panel
		JPanel panel_Messenger = new JPanel();
		placeMessengerPanel(panel_Messenger);

		// add
		add(panel_Messenger);

		// visiible
		setVisible(true);
	}
	
	public void placeMessengerPanel(JPanel panel_Messenger) {
		panel_Messenger.setLayout(null);
		
		btnReturn = new JButton(new ImageIcon("images//back.png"));
		btnReturn.setBounds(1200,600, 60, 60);
		btnReturn.setBorderPainted(false);
		btnReturn.setFocusPainted(false);
		btnReturn.setContentAreaFilled(false);

		panel_Messenger.add(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.exitMessenger();
			}
		});
	}
	
	public void setMain(MainProcess main) {
		this.main = main;
	}
}
