package softstory;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JdbcJtableTest01 extends JPanel {
   private static final long serialVersionUID=1L;
   private JButton jBtnAddRow=null;
   private JButton jBtnSaveRow=null;
   private JButton jBtnEditRow=null;
   private JButton jBtnDelRow=null;
   private JTable table;
   private JScrollPane scrollPane;
   
   private String driver="com.mysql.jdbc.Driver";
   private String url="jdbc:mysql://localhost:3306/softstory";
   private String colNames[]={"Subject","Content","Professor","deadline","최근접속날짜"};
   private DefaultTableModel model=new DefaultTableModel(colNames,0);
   
   private Connection con=null;
   private PreparedStatement pstmt=null;
   private ResultSet rs=null;
   
   public JdbcJtableTest01(){
      setLayout(null);
      table=new JTable(model);
      table.addMouseListener(new JTableMouseListener());
      scrollPane=new JScrollPane(table);
      scrollPane.setSize(440,200);
      add(scrollPane);
      initialize();
      select();
   }
   
   private class JTableMouseListener implements MouseListener{
      public void mouseClicked(java.awt.event.MouseEvent e){
         JTable jtable=(JTable)e.getSource();
         int row=jtable.getSelectedRow();
         int col=jtable.getSelectedColumn();
         
         System.out.println(model.getValueAt(row, col));
      }
      public void mouseEntered(java.awt.event.MouseEvent e){
      }
      public void mouseExited(java.awt.event.MouseEvent e){
      }
      public void mousePressed(java.awt.event.MouseEvent e){
      }
      public void mouseReleased(java.awt.event.MouseEvent e){
      }
   }
   
   private void select(){
      String query="select subject,content,professor,deadline,latest from schedule";
      try{
         Class.forName(driver);
         con=DriverManager.getConnection(url,"root","12345");
         pstmt=con.prepareStatement(query);
         rs=pstmt.executeQuery();
         
         while(rs.next()){
            model.addRow(new Object[]{rs.getString("subject"),rs.getString("content"),
                  rs.getString("professor"),rs.getString("deadline")
                  ,rs.getString("latest")   
            });
         }

      }catch(Exception e){
         System.out.println(e.getMessage());
      }finally{
         try{
            rs.close();
            pstmt.close();
            con.close();
         }catch(Exception e2){}
      }
   }
   private void initialize(){
      jBtnAddRow=new JButton();
      jBtnAddRow.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            System.out.println(e.getActionCommand());
            DefaultTableModel model2=(DefaultTableModel)table.getModel();
            model2.addRow(new String[]{"","","","",""});
      }      
      });
      jBtnAddRow.setBounds(2,222,105,35);
      jBtnAddRow.setText("추가");
      add(jBtnAddRow);
      
      jBtnSaveRow=new JButton();
      jBtnSaveRow.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
         System.out.println(e.getActionCommand());
         DefaultTableModel model2=(DefaultTableModel)table.getModel();
         int row=table.getSelectedRow();
         if(row<0)
            return;
         String query="insert into schedule(subject,content,professor,deadline,LATEST)"+"values(?,?,?,?,SYSDATE())";
         try{
            Class.forName(driver);
            con=DriverManager.getConnection(url,"root","12345");
            pstmt=con.prepareStatement(query);
            
            pstmt.setString(1, (String)model2.getValueAt(row,0));
            pstmt.setString(2, (String)model2.getValueAt(row,1));
            pstmt.setString(3, (String)model2.getValueAt(row,2));
            pstmt.setString(4, (String)model2.getValueAt(row,3));
            
            int cnt=pstmt.executeUpdate();
            //pstmt.executeUpdate();
            //pstmt.executeQuery();
         }catch(Exception eeee){
            System.out.println(eeee.getMessage());
         }finally{
            try{
               pstmt.close();
               con.close();
            }catch(Exception e2){}
         }
         
         model2.setRowCount(0);
         select();
      }
      });
      jBtnSaveRow.setBounds(112, 222, 105, 35);
      jBtnSaveRow.setText("저장");
      add(jBtnSaveRow);
      
      jBtnEditRow=new JButton();
      jBtnEditRow.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            System.out.println(e.getActionCommand());
            DefaultTableModel model2=(DefaultTableModel)table.getModel();
            int row=table.getSelectedRow();
            if(row<0)
               return;
            String query="UPDATE schedule SET content=?,professor=?,deadline=?"+"where subject=?";
            try{
               Class.forName(driver);
               con=DriverManager.getConnection(url,"root","12345");
               pstmt=con.prepareStatement(query);

               pstmt.setString(1, (String)model2.getValueAt(row, 1));
               pstmt.setString(2, (String)model2.getValueAt(row, 2));
               pstmt.setString(3, (String)model2.getValueAt(row, 3));
               pstmt.setString(4, (String)model2.getValueAt(row, 0));
               
               int cnt=pstmt.executeUpdate();
               //pstmt.executeUpdate();
               //pstmt.executeQuery();
            }catch(Exception eeee){
               System.out.println(eeee.getMessage());
            }finally{
               try{
                  pstmt.close();
                  con.close();
               }catch(Exception e2){}
            }
            model2.setRowCount(0);
            select();
         }
      });
      //jBtnEditRow.setBounds(182,270, 120, 25);
      jBtnEditRow.setBounds(222,222, 105, 35);
      jBtnEditRow.setText("수정");
      add(jBtnEditRow);
      
      jBtnDelRow=new JButton();
      jBtnDelRow.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent e){
         System.out.println(e.getActionCommand());
         DefaultTableModel model2=(DefaultTableModel)table.getModel();
         int row=table.getSelectedRow();
         if(row<0)
            return;
         String query="delete from schedule where subject= ?";
         try{
            Class.forName(driver);
            con=DriverManager.getConnection(url,"root","12345");
            pstmt=con.prepareStatement(query);
            
            pstmt.setString(1, (String)model2.getValueAt(row, 0));
            int cnt=pstmt.executeUpdate();
            //pstmt.executeUpdate();
            //pstmt.executeQuery();
         }catch(Exception eeee){
            System.out.println(eeee.getMessage());
         }finally{
            try{
               pstmt.close();con.close();
            }catch(Exception e2){}
         }
         model2.removeRow(row);
      }
      });
      jBtnDelRow.setBounds(new Rectangle(332,222,105,35));
      jBtnDelRow.setText("삭제");
      add(jBtnDelRow);
      }

}

