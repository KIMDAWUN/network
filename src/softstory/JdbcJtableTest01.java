package softstory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.GregorianCalendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/*
 * JdbcJtableTest01 is used to update user's entered date to DB 
 */
public class JdbcJtableTest01 extends JPanel {
   private static final long serialVersionUID = 1L;
   private JButton jBtnAddRow = null;
   private JButton jBtnSaveRow = null;
   private JButton jBtnEditRow = null;
   private JButton jBtnDelRow = null;
   private JTable table;
   private JScrollPane scrollPane;

   private String driver = "com.mysql.jdbc.Driver";
   private String url = "jdbc:mysql://"+MainProcess.serverAddress+":3306/softstory";
   private String colNames[] = { "Subject", "Content", "Professor", "Deadline", "D-Day", "" };
   private DefaultTableModel model = new DefaultTableModel(colNames, 0);

   private String colNames_schedule[] = { "Subject", "Content", "Professor", "Deadline" };
   private DefaultTableModel model_schedule = new DefaultTableModel(colNames_schedule, 0);

   JTextField textBox = new JTextField();

   TableColumn tcol;
   TableColumn tcol2;

   int dDay;
   private Connection con = null;
   private PreparedStatement pstmt = null;
   private ResultSet rs = null;

   Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 17);
   Font font2 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 13);

   //Schedule table
   public JdbcJtableTest01(String ID) {
      setLayout(null);
      table = new JTable(model);
      table.setFont(font2);
      table.setRowHeight(25);

      table.setAutoCreateRowSorter(true);
      TableRowSorter tablesorter = new TableRowSorter(table.getModel());
      table.setRowSorter(tablesorter);

      table.setSize(200, 50);
      table.addMouseListener(new JTableMouseListener());

      scrollPane = new JScrollPane(table);
      scrollPane.setSize(500, 200);

      add(scrollPane);

      initialize(ID);
      select(ID);
   }

   //Make table event
   private class JTableMouseListener implements MouseListener {
      public void mouseClicked(java.awt.event.MouseEvent e) {

         JTable jtable = (JTable) e.getSource();
         int row = jtable.getSelectedRow();
         int col = jtable.getSelectedColumn();

         System.out.println(model.getValueAt(row, col));
      }

      public void mouseEntered(java.awt.event.MouseEvent e) {
      }

      public void mouseExited(java.awt.event.MouseEvent e) {
      }

      public void mousePressed(java.awt.event.MouseEvent e) {
      }

      public void mouseReleased(java.awt.event.MouseEvent e) {
      }
   }

   /**
    * Read schedule database of user
    * Reading database, calculate d-day and write on schedule table
    * 
    * Input: ID (user ID)
    * 
    */
   private void select(String ID) {
      System.out.println(ID);
      String query = "select subject,content,professor,deadline from schedule where id='" + ID + "'";

      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, "root", "12345");
         pstmt = con.prepareStatement(query);
         rs = pstmt.executeQuery();
         int row = 0;

         while (rs.next()) {
            dDay = calculate_dDay(rs.getString("deadline"));
            String DDAY;

            if (dDay < 0) {
               DDAY = "D+ " + ((-1) * dDay);
            }

            else {
               DDAY = "D- " + dDay;
            }

            String subject = rs.getString("subject");
            String content = rs.getString("content");
            String professor = rs.getString("professor");
            String deadline = rs.getString("deadline");

            Object[] add = { subject, content, professor, deadline, DDAY, "" };

            model.addRow(add);
         }

         //Change column 5 with proper color according d-day
         tcol = table.getColumnModel().getColumn(5); 
         tcol.setCellRenderer(new CustomTableCellRenderer());

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

   /**
    * Coloring column 5
    * if deadline is today or left to 3 days ->Red
    * if deadline is left to 4 to 6 days ->Orange
    * if deadline is left to 7 to 10 days -> Yellow
    * if deadline is left over 10 days ->blue
    * if deadline is overed then color is green.
    */
   public class CustomTableCellRenderer extends DefaultTableCellRenderer {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus,
            int row, int column) {
         Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

         String value = (String) model.getValueAt(row, 3);
         int day = calculate_dDay(value);

         if (day < 0) {
            cell.setBackground(new Color(146, 208, 80));
         }

         else if (day >= 0 && day < 4) {
            cell.setBackground(new Color(255, 0, 0));
         }

         else if (day >= 4 && day < 7) {
            cell.setBackground(new Color(255, 192, 0));
         }

         else if (day >= 7 && day <= 10) {
            cell.setBackground(new Color(255, 255, 0));
         }

         else {
            cell.setBackground(new Color(0, 176, 240));
         }

         return cell;
      }
   }

   /**
    * Make schedule panel
    * Make input(subject, content, professor, deadline) part to add schedule
    * Input: ID (user ID)
    */
   private void initialize(String ID) {
      Font font1 = new Font("배달의민족 주아", Font.CENTER_BASELINE, 20);

      JPanel panel_add = new JPanel();
      panel_add.setLayout(null);
      panel_add.setSize(1000, 500);

      JTextField subject = new JTextField();
      JTextField content = new JTextField();
      JTextField professor = new JTextField();
      JTextField deadline = new JTextField();

      JLabel subjectL = new JLabel("Subject: ");
      JLabel contentL = new JLabel("Content: ");
      JLabel professorL = new JLabel("Professor: ");
      JLabel deadlineL = new JLabel("Deadline: ");

      subjectL.setFont(font1);
      subjectL.setBounds(0, 0, 120, 40);
      panel_add.add(subjectL);

      contentL.setFont(font1);
      contentL.setBounds(0, 40, 120, 40);
      panel_add.add(contentL);

      professorL.setFont(font1);
      professorL.setBounds(0, 80, 120, 40);
      panel_add.add(professorL);

      deadlineL.setFont(font1);
      deadlineL.setBounds(0, 120, 120, 40);
      panel_add.add(deadlineL);

      subject.setBounds(150, 0, 200, 35);
      content.setBounds(150, 40, 200, 35);
      professor.setBounds(150, 80, 200, 35);
      deadline.setBounds(150, 120, 200, 35);

      subject.setFont(font2);
      content.setFont(font2);
      professor.setFont(font2);
      deadline.setFont(font2);

      panel_add.add(subject);
      panel_add.add(content);
      panel_add.add(professor);
      panel_add.add(deadline);

      JButton addBtn = new JButton(new ImageIcon("images//addButton.png"));
      addBtn.setBounds(350, 120, 200, 40);
      addBtn.setBorderPainted(false);
      addBtn.setFocusPainted(false);
      addBtn.setContentAreaFilled(false);
      panel_add.add(addBtn);
      addBtn.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String inputStr[] = new String[5];

            inputStr[0] = subject.getText();
            inputStr[1] = content.getText();
            inputStr[2] = professor.getText();
            inputStr[3] = deadline.getText();

            String DDAY;
            dDay = calculate_dDay(inputStr[3]);

            if (dDay < 0) {
               DDAY = "D+ " + ((-1) * dDay);
            }

            else {
               DDAY = "D- " + dDay;
            }

            inputStr[4] = DDAY;

            //Add to schedule table
            model.addRow(inputStr);

            //Add to database
            String query = "insert into schedule(id,subject,content,professor,deadline)" + "values(?,?,?,?,?)";

            try {
               Class.forName(driver);
               con = DriverManager.getConnection(url, "root", "12345");
               pstmt = con.prepareStatement(query);

               pstmt.setString(1, ID);
               pstmt.setString(2, inputStr[0]);
               pstmt.setString(3, inputStr[1]);
               pstmt.setString(4, inputStr[2]);
               pstmt.setString(5, inputStr[3]);

               int cnt = pstmt.executeUpdate();

            } catch (Exception eeee) {
               System.out.println(eeee.getMessage());
            } finally {
               try {
                  pstmt.close();
                  con.close();
               } catch (Exception e2) {
               }
            }

            subject.setText("");
            content.setText("");
            professor.setText("");
            deadline.setText("");
         }
      });

      panel_add.setBounds(0, 300, 700, 500);
      panel_add.setOpaque(false);
      add(panel_add, BorderLayout.SOUTH);

      //To delete schedule table row
      jBtnDelRow = new JButton(new ImageIcon("images//deleteButton.png"));
      jBtnDelRow.addActionListener(new ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent e) {
            System.out.println(e.getActionCommand());
            DefaultTableModel model2 = (DefaultTableModel) table.getModel();
            
            int row = table.getSelectedRow();
            if (row < 0)
               return;
            
            String query = "delete from schedule where subject= ?";
            try {
               Class.forName(driver);
               con = DriverManager.getConnection(url, "root", "12345");
               pstmt = con.prepareStatement(query);

               pstmt.setString(1, (String) model2.getValueAt(row, 0));
               int cnt = pstmt.executeUpdate();

            } catch (Exception eeee) {
               System.out.println(eeee.getMessage());
            } finally {
               try {
                  pstmt.close();
                  con.close();
               } catch (Exception e2) {
               }
            }
            
            model2.removeRow(row);
         }
      });

      jBtnDelRow.setSize(220, 70);
      jBtnDelRow.setBounds(170, 220, 200, 40);
      jBtnDelRow.setBorderPainted(false);
      jBtnDelRow.setFocusPainted(false);
      jBtnDelRow.setContentAreaFilled(false);
      add(jBtnDelRow);
   }

   /**
    * Calculate d-day value
    * Input: deadline
    * Return: int type -> d-day
    */
   public int calculate_dDay(String deadline) {
      deadline = deadline.trim();
      int first = deadline.indexOf("/");
      int last = deadline.lastIndexOf("/");

      int year = Integer.parseInt(deadline.substring(0, first));
      int month = Integer.parseInt(deadline.substring(first + 1, last));
      int day = Integer.parseInt(deadline.substring(last + 1, deadline.length()));

      GregorianCalendar cal = new GregorianCalendar();
      long currentTime = cal.getTimeInMillis() / (1000 * 60 * 60 * 24);
      cal.set(year, month - 1, day);
      long Time = cal.getTimeInMillis() / (1000 * 60 * 60 * 24);
      int dDay = (int) (Time - currentTime);

      System.out.println(dDay);
      return dDay;
   }
}