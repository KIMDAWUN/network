package softstory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JList;

/*
 * MessengerView is used to chat with everyone currently login
 * or chat with anyone you want 1:1
 */
public class MessengerView extends JFrame {
   private MainProcess main;
   private String driver = "com.mysql.jdbc.Driver";
   private String url = "jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory";
   DefaultListModel model = new DefaultListModel();

   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   private JButton btnReturn;
   private JTextArea txLog;
   private JTextField txChat = new JTextField();

   String id;
   
   /*
    * JFrame for Messenger window
    */

   MessengerView() {
      setTitle("Chat with your friend!");
     setSize(1300, 700);
      setLocation(250, 70);
      // panel
      ImageIcon img = new ImageIcon("images//background.png");
      JPanel panel_Messenger = new JPanel() {
         public void paintComponent(Graphics g) {

            g.drawImage(img.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };
      panel_Messenger.setBorder(new EmptyBorder(5,5,5,5));
      setContentPane(panel_Messenger);
      panel_Messenger.setLayout(null);
      
      JLabel lblMessages = new JLabel("Messages");
      lblMessages.setBounds(100, 100, 62, 18);
      panel_Messenger.add(lblMessages);
      
      JLabel lblMembers = new JLabel("Members");
      lblMembers.setBounds(965, 100, 107, 24);
      panel_Messenger.add(lblMembers);
      
      JScrollPane scrollPane_1=new JScrollPane();
      scrollPane_1.setBounds(920, 135, 291, 417);
      panel_Messenger.add(scrollPane_1);

      select(driver);
      JList list = new JList();
      list.setModel(model);
      scrollPane_1.setBorder(new EmptyBorder(5,5,5,5));
      scrollPane_1.setViewportView(list);
      
      /*
       * list has names for the people currently login
       * if you click one of them, you can 1:1 chat with him/her
       * by sending message with "QNETOONE"
       */
      list.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {

            String str = (String) list.getSelectedValue();

            MainProcess.out.println("ONETOONE:" + str);

         }
      });
      
      txChat = new JTextField();
      txChat.setBounds(70, 594, 946, 24);
      panel_Messenger.add(txChat);
      txChat.setColumns(10);
      
      
      /*
	   * write down text and press enter or click send button 
	   * multiple chat with currently login 
	   * send MainProcess acting as client
	   * start with message "BROADCASTCHAT"
	   * (non-Javadoc)
	   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	   */

      txChat.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent e) {
             String text = txChat.getText();
              txChat.setText("");
              MainProcess.out.println("BROADCASTCHAT:" + text);
          }
       });
      JButton btnSend = new JButton("Send");
      btnSend.setBounds(1063, 593, 76, 27);
      btnSend.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String text = txChat.getText();
            txChat.setText("");
            MainProcess.out.println("BROADCASTCHAT:" + text);
         }
      });
      panel_Messenger.add(btnSend);

      /*
       *If someone get out from messenger view, to notify that someone get out from messenger view, broadcast that.
       *send MainProcess acting as client
       *start with message "OUT:>>>>> " and send ID.
       */
      btnReturn = new JButton(new ImageIcon("images//back.png"));
      btnReturn.setBounds(1182, 593, 65, 37);
      btnReturn.setBorderPainted(false);
      btnReturn.setFocusPainted(false);
      btnReturn.setContentAreaFilled(false);

      btnReturn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            MainProcess.out.println("OUT:>>>>>     " + MainProcess.id + " exited.     <<<<<");
            main.exitMessenger();
         }
      });
      panel_Messenger.add(btnReturn);
      
    JScrollPane scrollPane = new JScrollPane();
   scrollPane.setBounds(70, 135, 750, 418);
   panel_Messenger.add(scrollPane);
      
   txLog=new JTextArea();
   scrollPane.setBorder(new EmptyBorder(5,5,5,5));
   scrollPane.setViewportView(txLog);
      
   setVisible(true);
   }
   
   /*
    *To show who are login. 
    *Initially all are set login value in DB as false
    *But if someone login, then its value change as true.
    *So to find someone who are login, login value in DB is ture.
    *Input: ID (user ID)
    */
   private void select(String ID) {
         id = ID;
         System.out.println(ID);
         String query = "select id from member where login='true'";

         try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "12345");
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            int row = 0;

            while (rs.next()) {
               String id = rs.getString("id");
               // text_id.setText(rs.getString("number").trim());
               model.addElement(id);

            }
         } catch (Exception e) {
            System.out.println(e.getMessage());
         } finally {
            try {
               rs.close();
               pstmt.close();
               con.close();
            } catch (Exception e2) {
            }
         }
      }

   /*
    * If someone send message on messenger
    * Then server broadcast it to all who are login
    * Get that message and write on the messenger view
    * Input: line (message from someone) 
    */
   public void showBroadcastMessage(String line) {
      txLog.append(line);
   }

   public void setMain(MainProcess main) {
      this.main = main;
   }
}