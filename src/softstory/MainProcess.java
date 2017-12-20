package softstory;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * MainProcess is used to communicate with Server
 * Process all messages from server, according to the protocol.
 * 
 * pass message from QuestionView and MessengerView to Server
 * run() makes thread for waiting message from other(server,questionView,messengerView)
 */
public class MainProcess {
   static MainProcess main = new MainProcess();

   public static BufferedReader in;
   public static PrintWriter out;
   static String from;
   static String a;

   LoginView loginView;
   MainView mainView;
   JoiningView joiningView;
   QuestionView questionView;
   ScheduleView scheduleView;
   MessengerView messengerView;
   SettingView settingView;
   Satisfaction satisfaction;
   static JTextArea txt;
   static String id;
   static String other_id;
   
   static String serverAddress = "127.0.0.1";

   public void run() throws IOException {

      // Make connection and initialize streams
      Socket socket = new Socket(serverAddress, 9001);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      // Process all messages from server, according to the protocol.
      while (true) {
         String line = in.readLine();
         System.out.println("**" + line);

         if (line.startsWith("SUBMITNAME")) {
            main.loginView = new LoginView();
            main.loginView.setMain(main);
            while (true) {
               if (LoginView.LoginFlag) {
                  a = LoginView.currentID;
                  System.out.println("***" + a + "***");
                  out.println(a);
                  break;
               }
            }

         }

         else if (line.startsWith("NAMEACCEPTED")) {
            System.out.println("client: login success");
         }

         /*
          * server send "QUESTIONPOP:" + fromID + ":" + language + ":" + course + ":" + contents
          * makes frame for questionPop using function Message()
          */
         else if (line.startsWith("QESTIONPOP")) {
            String[] msg = line.split(":");
            String protocol = msg[0];
            String fromID = msg[1];
            String language = msg[2];
            String course = msg[3];
            String contents = msg[4];

            new Message(fromID, language, course, contents);

            // JPanel p1 = new JPanel();
            // setTitle("Question arrived!");
         }

         /*
          * "FCHAT:"+"client2"
          * when client2 is accept the QUESTION of client1
          * make chat frame with client1
          */
         else if (line.startsWith("FCHAT")) {
            other_id = line.substring(6);
            pa frame = new pa();
         }

         /*
          *  "CHAT:"+"client2;"+input
          *  chat message between person who asked Question and who was asked Question 
          *  append textArea
          */
         else if (line.startsWith("CHAT")) {
            int who = line.indexOf(";");
            String msg = line.substring(who + 1);

            System.out.println(other_id + ": " + msg);
            txt.append(other_id + ":" + msg + "\n");
         }

         /*
          * receive by MessengerView "ONETOONE+ client2 name"
          * send to Server the message start with "FONECHAT+my_id+client2_id"
          */
         else if (line.startsWith("ONETOONE:")) {
            int idx = line.indexOf(":");
            other_id = line.substring(idx + 1);

            pa frame = new pa();
            out.println("FONECHAT:" + id + "," + other_id);
         }

         /*
          * receive from MessengerView "BROADCASTCHAT: id;data"
          * send to server the message start with "BROADCASTCHAT"
          * this is for broadcast message from one user to all other users
          */
         else if (line.startsWith("BROADCASTCHAT")) {
            System.out.println(line);
            int idx = line.indexOf(":");
            line = line.substring(idx + 1);
            idx = line.indexOf(";");
            String name = line.substring(0, idx);
            line = line.substring(idx + 1);
            String newLine = ">  " + name + "  :  " + line + "\n";
            // showBroadcastMessage
            this.messengerView.showBroadcastMessage(newLine);
         } 
         /*
          * receive from MessengerView "OUT: id"
          * this is for broadcast that "id" go out from the chat page.
          */
         else if (line.startsWith("OUT")) {
            System.out.println(line);
            int idx = line.indexOf(":");
            line = line.substring(idx + 1);
            // showBroadcastMessage
            this.messengerView.showBroadcastMessage(line + "\n");
         }
      }
   }

   /*
    * Message class is used to make JFrame for QUESITONPOP window
    */
   class Message extends Frame implements ActionListener {

      JButton accept, reject;
      JLabel text;
      String Fromid;
      String language;
      String course;
      String contents;

      public Message(String id, String language, String course, String contents) {
         setTitle("Question Received!");
         setSize(700, 500);
         setResizable(false);
         setLocation(200, 70);
         other_id = id;
         this.Fromid = id;
         this.language = language;
         this.course = course;
         this.contents = contents;
         layset();
      }

      public void layset() {
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               dispose();
            }
         });
         Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);
    
         JPanel p1 = new JPanel();

         JLabel LanguageLabel = new JLabel("Language: ");
         LanguageLabel.setFont(font1);
         p1.add(LanguageLabel);

         JLabel Language = new JLabel(language);
         p1.add(Language);
         add(BorderLayout.NORTH, p1);

         JLabel CourseLabel1 = new JLabel(" / Course: ");
         CourseLabel1.setFont(font1);
         p1.add(CourseLabel1);

         JLabel Course = new JLabel(course);
         p1.add(Course);

         JLabel TitleLabel = new JLabel(" / Title: ");
         TitleLabel.setFont(font1);
         p1.add(TitleLabel);

         JLabel Title = new JLabel(contents);

         JPanel p2 = new JPanel();

         JLabel line = new JLabel("*******************************************************************************",
               SwingConstants.CENTER);
         line.setFont(font1);
         p2.add(line);
         JLabel warning1 = new JLabel("WARNING", SwingConstants.CENTER);
         warning1.setFont(font1);
         p2.add(warning1);
         JLabel warning2 = new JLabel("If the code is found to be copied, you may receive disadvantages.",
               SwingConstants.CENTER);
         warning2.setFont(font1);
         p2.add(warning2);
         add(BorderLayout.CENTER, p2);

         JPanel p3 = new JPanel();
         p3.add(accept = new JButton("accept"));
         accept.addActionListener(this);
         p3.add(reject = new JButton("reject"));
         reject.addActionListener(this);
         add(BorderLayout.SOUTH, p3);

         setBounds(200, 400, 700, 250);
         setVisible(true);
      }
      
      /*
       * actionPerformed when user accept or reject button
       * when accept-> 
       * when reject-> send to Server that "QUESTION2"
       * (non-Javadoc)
       * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
       */
      public void actionPerformed(ActionEvent e) {
         Object o = e.getSource();

         if ((JButton) o == accept) {
            SatisDTO qdto = new SatisDTO();
            qdto.setId(other_id);
            qdto.setStar(1);
            try {
               QuestionDeleteDAO.create(qdto);
            } catch (Exception e1) {
               e1.printStackTrace();
            }
            pa frame = new pa();
            out.println("FONECHAT:" + id + "," + other_id);
         }
         else if((JButton)o==reject){
            out.println("QUESTION2");
         }
         setVisible(false);
         dispose();
      }
   }

   /*
    * make JFrame for chat room when user click accept button
    * for chat with each other send to the Server "ONECHAT +other_id+ message"
    */
   public class pa extends JFrame {

      public pa() {
         
        Color color = new Color(245,245,245);
        Color color2 = new Color(250,250,250);
         JFrame frame = new JFrame(id + "의 채팅방/ " + other_id + "와의");
         JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

         JTextField text = new JTextField();
         text.setBackground(color2);
         txt = new JTextArea(70, 500);
         txt.setBackground(color);
         JScrollPane scroll = new JScrollPane(txt);
         scroll.setBackground(color);
         JButton bt = new JButton("Finish!");

         scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
         scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

         text.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               String s = text.getText();
               out.println("ONECHAT:" + id + "," + other_id + ";" + s);
               txt.append(a + ": " + s + "\n");
               text.setText("");
            }
         });

         bt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

               if (QuestionView.asking) {
                  frame.setVisible(false);
                  showSatisfaction(other_id);
               } else {
                  frame.setVisible(false);
                  main.showNewMain();
               }

            }

         });

         panel.add(text);
         panel.add(scroll);
         frame.add(BorderLayout.SOUTH, bt);

         frame.getContentPane().add(BorderLayout.CENTER, panel);

         frame.setLocationRelativeTo(null);
         frame.setSize(400, 700);
         frame.setLocation(400,100);
         frame.setVisible(true);

      }
   }

   /*
    * function main
    * make new thread and run
    */
   public static void main(String[] args) throws IOException {
      main.run();

   }

   /*
    * function for show main view
    * this requires ID as a parameter 
    */
   public void showMainView(String ID) throws IOException {
      loginView.dispose();
      id = ID;
      this.mainView = new MainView(ID);
      this.mainView.setMain(this);
   }

   /*
    * function for show login view
    */
   public void showLoginView() {
      joiningView.dispose();
   }

   /*
    * function for show joining view
    */
   public void showJoiningView() {
      this.joiningView = new JoiningView();
      this.joiningView.setMain(this);
   }

   /*
    * function for show question view 
    */
   public void showQuestion() throws IOException {
      mainView.dispose();
      this.questionView = new QuestionView();
      this.questionView.setMain(this);
   }

   /*
    * function for exit question view
    */
   public void exitQuestion() {
      questionView.dispose();
      this.mainView = new MainView(id);
      this.mainView.setMain(main);
   }

   /*
    * function for show schedule view
    * this requires user's ID as a parameter
    */
   public void showSchedule(String ID) {
      mainView.dispose();
      this.scheduleView = new ScheduleView(ID);
      this.scheduleView.setMain(this);
   }

   /*
    * function for exit schedule view
    */
   public void exitSchedule() {
      scheduleView.dispose();
      this.mainView = new MainView(id);
      this.mainView.setMain(main);
   }
   
   /*
    * function for show Messenger view
    */
   public void showMessenger() {
      mainView.dispose();
      this.messengerView = new MessengerView();
      this.messengerView.setMain(this);
   }

   /*
    * function for exit Messenger view
    */
   public void exitMessenger() {
      messengerView.dispose();
      this.mainView = new MainView(id);
      this.mainView.setMain(main);
   }

   /*
    * function for show setting view
    */
   public void showSetting() {
      this.settingView = new SettingView();
      this.settingView.setMain(this);
   }

   /*
    * function for exit setting view
    */
   public void exitSetting() {
      settingView.dispose();
   }

   /*
    * function for show satisfaction survey
    */
   public void showSatisfaction(String noteid) {
      this.satisfaction = new Satisfaction(noteid);
      this.satisfaction.setMain(this);
   }

   /*
    * function for show new main view
    * this is for show the change of character after number of egg changed
    */
   public void showNewMain() {
      mainView.dispose();
      this.mainView = new MainView(id);
      this.mainView.setMain(main);
   }

   /*
    * function for exit main
    */
   public void exitMain() {
      System.exit(0);
   }
}