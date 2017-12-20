package softstory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

public class Server extends JFrame {
   private static final int PORT = 9001;
   private static HashSet<String> names = new HashSet<String>();
   private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
   private static HashSet<PrintWriter> chatwriter = new HashSet<PrintWriter>();
   private static HashMap<String, PrintWriter> users = new HashMap<String, PrintWriter>();
   static UserDTO udto = new UserDTO();
   
   static String fromID;
   static String language;
   static String course;
   static String contents;
   static String[] user;
   static int i,count=0;

   /**
   *Implement Server
   * If server run -> make Handler
   * when it is only first run
   */
   public static void main(String[] args) throws Exception {
      System.out.println("The chat server is running.");
      ServerSocket listener = new ServerSocket(PORT);
      try {
         while (true) {
            new Handler(listener.accept()).start();
         }
      } finally {
         listener.close();
      }
   }

   /**
    * Constructs a handler thread, squirreling away the socket. All the interesting
    * work is done in the run method.
    */
   private static class Handler extends Thread {
      private String name;
      private Socket socket;
      private BufferedReader in;
      private PrintWriter out;

      /**
       * Constructs a handler thread, squirreling away the socket. All the interesting
       * work is done in the run method.
       */
      public Handler(Socket socket) {
         this.socket = socket;
      }

      /**
       * Services this thread's client by repeatedly requesting a screen name until a
       * unique one has been submitted, then acknowledges the name and registers the
       * output stream for the client in a global set, then repeatedly gets inputs and
       * broadcasts them.
       */
      public void run() {
         try {

            // Create character streams for the socket.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Request a name from this client. Keep requesting until
            // a name is submitted that is not already used. Note that
            // checking for the existence of a name and adding the name
            // must be done while locking the set of names.
            while (true) {
               out.println("SUBMITNAME");
               name = in.readLine();
               if (name == null) {
                  return;
               }
               synchronized (names) {
                  if (!names.contains(name)) {
                     names.add(name);
                     break;
                  }
               }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            out.println("NAMEACCEPTED");
            writers.add(out);
            users.put(name, out);
            udto.setId(name);
            try {
               UserInsertDAO.create(udto);
            } catch (Exception e) {
               e.printStackTrace();
            }

            out.println("ACCESSED:" + name);

            // broadcast the entrance state to all clients in the room
            /*
             * for (PrintWriter writer : writers) { writer.println("MESSAGE " + "===\"" +
             * name + "\" entered.==="); }
             */

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcasted to.
            while (true) {
               String input = in.readLine();
               if (input == null) {
                  return;
               }
               /*
                * Question1-> first found the person who receive the question when client ask the question
                * Question2-> found more person when i was rejected both questions
                */
               else if (input.startsWith("QUESTION")) {
                if(input.startsWith("QUESTION1"))
                  {
                   String[] msg = input.split(":");
                   String protocol = msg[0];
                   fromID = msg[1];
                   language = msg[2];
                   course = msg[3];
                     contents = msg[4];
                     System.out.println("asking: " + fromID);
                     System.out.println("language: " + language);
                     System.out.println("subject: " + course);
                     System.out.println("content: " + contents);

                     Connection con = null;
                     Statement stmt = null;
                     ResultSet rs, rs2 = null;
                     String userId = "";
                     user = new String[20];
                     i = 0;

                   //find the person who is matched conditions
                     String sql = "select distinct id from language where id in (select distinct id from currentUser where push=1) and confiLanguage='"
                           + language + "'";
                     Class.forName("com.mysql.jdbc.Driver");
                     con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/softstory", "root", "12345");
                     stmt = (Statement) con.createStatement();
                     rs = stmt.executeQuery(sql);

                     for (int k = 0; k < 20; k++) {
                        user[k] = null;
                     }
                     // if문 나에게는 보내지 않기위해
                     while (rs.next()) {
                        userId = rs.getString("id");// String userId = rs.getString("id");

                        if (userId.equals(fromID)) {
                           continue;
                        }
                        user[i] = userId;
                        System.out.println(i + "-" + user[i]);
                        i++;
                     }

                     i--;
                  }
                  
                if(input.startsWith("QUESTION2"))
                   {
                      count++;
                      if(count%2==1)
                         return;
                   }
                
                int select1, select2;
              //Make 2 random number to choose person randomly
                  Random random = new Random();
                  select1 = random.nextInt(i / 2 + 1);
                  System.out.println("random1: " + select1);
                  select2 = random.nextInt(i) + i / 2;
                  if (select2 == select1) {
                     while (true) {
                        select2 = random.nextInt(i) + i / 2;
                        if (select1 != select2)
                           break;
                     }
                  }
                  System.out.println("random2: " + select2);
                  Object obj1 = users.get(user[select1]);
                  System.out.println(user[select1]);
                  if (obj1 != null) {
                     PrintWriter pw = (PrintWriter) obj1;
                     pw.println("QESTIONPOP:" + fromID + ":" + language + ":" + course + ":" + contents);
                  }
                  Object obj2 = users.get(user[select2]);
                  System.out.println(user[select2]);
                  if (obj2 != null) {
                     PrintWriter pw = (PrintWriter) obj2;
                     pw.println("QESTIONPOP:" + fromID + ":" + language + ":" + course + ":" + contents);
                  }

               }

               /*
                * If someone submit the question, then to notify the fact
                * * server send message to clients.
                */
               else if (input.startsWith("FONECHAT")) {
                  System.out.println(input);
                  int my = input.indexOf(",");
                  String client1 = input.substring(9, my);
                  String client2 = input.substring(my + 1);

                  
                  Object obj2 = users.get(client2);
                  System.out.println(client2);
                  if (obj2 != null) {
                     PrintWriter pw = (PrintWriter) obj2;
                     pw.println("FCHAT:" + client1);
                  }

               }

               // client-> ("ONECHAT:b,bbbb;help)
               // send message to client2 1:1
               else if (input.startsWith("ONECHAT")) {
                  System.out.println(input);
                  int ind = input.indexOf(",");
                  int inm = input.indexOf(";");
                  String client1 = input.substring(7, ind);
                  String client2 = input.substring(ind + 1, inm);
                  String chatmsg = input.substring(inm + 1);

                  Object obj2 = users.get(client2);
                  System.out.println(client2);
                  if (obj2 != null) {
                     PrintWriter pw = (PrintWriter) obj2;
                     pw.println("CHAT" + client1 + ";" + chatmsg);
                  }

               }

                /*
       		     * User chose someone who want to chat 1:1
       		     * Server get the message starts with "ONETOONE"
                 * like ONETOONE:(user message)
       		     * so server broadcast the message ONETOONE+message which one after ':' to only chosen person
       		     * server have to contain 'ONETOONE' because notify it is only for special user
                 */
               else if (input.startsWith("ONETOONE:")) {
                  int idx = input.indexOf(":");
                  input = "ONETOONE:" + input.substring(idx + 1);

                  out.println(input);
               }

               /*
       		   * If someone want to send the message, then server get message
       		   * which starts with "BROADCASTCHAT"
               * like BROADCASTCHAT:(user message)
       		   * so server broadcast the message which one after ':' to all connected member
               */
               else if (input.startsWith("BROADCASTCHAT")) {
                  // broadcast messages to all clients in the room
                  int idx = input.indexOf(":");
                  input = input.substring(idx + 1);
                  for (PrintWriter writer : writers) {
                     writer.println("BROADCASTCHAT:" + name + ";" + input);
                  }
               }

               /*
                * If someone get out messenger view, then have to notify that fact
		        * So message start with 'OUT', broadcast to all of connected member.
                */
               else if (input.startsWith("OUT")) {
                  for (PrintWriter writer : writers) {
                     writer.println(input);
                  }
               }
            }
         } catch (IOException e) {
            System.out.println(e);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            // This client is going down! Remove its name and its print
            // writer from the sets, and close its socket.
            if (name != null) {
               names.remove(name);
            }
            if (out != null) {
               writers.remove(out);
               udto.setId(name);
               try {
                  UserDeleteDAO.create(udto);
               } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }
            try {
               socket.close();
            } catch (IOException e) {
            }
         }
      }
   }
}