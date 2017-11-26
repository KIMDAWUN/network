package softstory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class CalendarDataManager{ // 6*7�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
   static final int CAL_WIDTH = 7;
   final static int CAL_HEIGHT = 6;
   int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
   int calYear;
   int calMonth;
   int calDayOfMon;
   final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
   int calLastDate;
   Calendar today = Calendar.getInstance();
   Calendar cal;
   
   public CalendarDataManager(){ 
      setToday(); 
   }
   public void setToday(){
      calYear = today.get(Calendar.YEAR); 
      calMonth = today.get(Calendar.MONTH);
      calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
      makeCalData(today);
   }
   private void makeCalData(Calendar cal){
      // 1���� ��ġ�� ������ ��¥�� ���� 
      int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
      if(calMonth == 1) 
         calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
      else 
         calLastDate = calLastDateOfMonth[calMonth];
      // �޷� �迭 �ʱ�ȭ
      for(int i = 0 ; i<CAL_HEIGHT ; i++){
         for(int j = 0 ; j<CAL_WIDTH ; j++){
            calDates[i][j] = 0;
         }
      }
      // �޷� �迭�� �� ä���ֱ�
      for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
         if(i == 0) k = calStartingPos;
         else k = 0;
         for(int j = k ; j<CAL_WIDTH ; j++){
            if(num <= calLastDate) 
               calDates[i][j]=num++;
         }
      }
   }
   private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�
      if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
      else return 0;
   }
   public void moveMonth(int mon){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
      calMonth += mon;
      if(calMonth>11) while(calMonth>11){
         calYear++;
         calMonth -= 12;
      } else if (calMonth<0) while(calMonth<0){
         calYear--;
         calMonth += 12;
      }
      cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
      makeCalData(cal);
   }
}

public class ScheduleView extends CalendarDataManager{ // CalendarDataManager�� GUI + �޸��� + �ð�
   private MainProcess main;
   // â ������ҿ� ��ġ��
   JFrame mainFrame;
   //   ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
   
   JPanel calOpPanel;
      JButton todayBut;
      JLabel todayLab;
      JButton lYearBut;
      JButton lMonBut;
      JLabel curMMYYYYLab;
      JButton nMonBut;
      JButton nYearBut;
      ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
   
   JPanel calPanel;
      JButton weekDaysName[];
      JButton dateButs[][] = new JButton[6][7];
      listenForDateButs lForDateButs = new listenForDateButs(); 
   
   JPanel infoPanel;
      JLabel infoClock;
   
   JPanel frameBottomPanel;
   
   JPanel SchedulePanel;
      JLabel selectedDate;
      JTextArea memoArea;
      JPanel memoSchedule;
      JScrollPane memoAreaSP;
      JPanel memoSubPanel;
      JButton saveBut; 
      JButton delBut; 
      JButton clearBut;
   
   JLabel bottomInfo = new JLabel("Welcome to Memo Calendar!");
   //���, �޼���
   final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
   final String title = "Calendar";
   final String SaveButMsg1 = "�� MemoData������ �����Ͽ����ϴ�.";
   final String SaveButMsg2 = "�޸� ���� �ۼ��� �ּ���.";
   final String SaveButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
   final String DelButMsg1 = "�޸� �����Ͽ����ϴ�.";
   final String DelButMsg2 = "�ۼ����� �ʾҰų� �̹� ������ memo�Դϴ�.";
   final String DelButMsg3 = "<html><font color=red>ERROR : ���� ���� ����</html>";
   final String ClrButMsg1 = "�Էµ� �޸� ������ϴ�.";

   public static void main(String[] args){
      SwingUtilities.invokeLater(new Runnable(){
         public void run(){
            new ScheduleView();
         }
      });
   }
   
   public ScheduleView(){ //������� ������ ���ĵǾ� ����. �� �ǳ� ���̿� ���ٷ� ����
      
      mainFrame = new JFrame(title);
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setSize(1300, 700);
      mainFrame.setLocation(200, 70);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setResizable(true);
      
      calOpPanel = new JPanel();
         todayBut = new JButton("Today");
         todayBut.setToolTipText("Today");
         todayBut.addActionListener(lForCalOpButtons);
         todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
         lYearBut = new JButton("<<");
         lYearBut.setToolTipText("Previous Year");
         lYearBut.addActionListener(lForCalOpButtons);
         lMonBut = new JButton("<");
         lMonBut.setToolTipText("Previous Month");
         lMonBut.addActionListener(lForCalOpButtons);
         curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
         nMonBut = new JButton(">");
         nMonBut.setToolTipText("Next Month");
         nMonBut.addActionListener(lForCalOpButtons);
         nYearBut = new JButton(">>");
         nYearBut.setToolTipText("Next Year");
         nYearBut.addActionListener(lForCalOpButtons);
         calOpPanel.setLayout(new GridBagLayout());
         GridBagConstraints calOpGC = new GridBagConstraints();
         calOpGC.gridx = 1;
         calOpGC.gridy = 1;
         calOpGC.gridwidth = 2;
         calOpGC.gridheight = 1;
         calOpGC.weightx = 1;
         calOpGC.weighty = 1;
         calOpGC.insets = new Insets(5,5,0,0);
         calOpGC.anchor = GridBagConstraints.WEST;
         calOpGC.fill = GridBagConstraints.NONE;
         calOpPanel.add(todayBut,calOpGC);
         calOpGC.gridwidth = 3;
         calOpGC.gridx = 2;
         calOpGC.gridy = 1;
         calOpPanel.add(todayLab,calOpGC);
         calOpGC.anchor = GridBagConstraints.CENTER;
         calOpGC.gridwidth = 1;
         calOpGC.gridx = 1;
         calOpGC.gridy = 2;
         calOpPanel.add(lYearBut,calOpGC);
         calOpGC.gridwidth = 1;
         calOpGC.gridx = 2;
         calOpGC.gridy = 2;
         calOpPanel.add(lMonBut,calOpGC);
         calOpGC.gridwidth = 2;
         calOpGC.gridx = 3;
         calOpGC.gridy = 2;
         calOpPanel.add(curMMYYYYLab,calOpGC);
         calOpGC.gridwidth = 1;
         calOpGC.gridx = 5;
         calOpGC.gridy = 2;
         calOpPanel.add(nMonBut,calOpGC);
         calOpGC.gridwidth = 1;
         calOpGC.gridx = 6;
         calOpGC.gridy = 2;
         calOpPanel.add(nYearBut,calOpGC);
      calPanel=new JPanel();
         weekDaysName = new JButton[7];
         for(int i=0 ; i<CAL_WIDTH ; i++){
            weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
            weekDaysName[i].setBorderPainted(false);
            weekDaysName[i].setContentAreaFilled(false);
            weekDaysName[i].setForeground(Color.WHITE);
            
            if(i == 0) 
               weekDaysName[i].setBackground(new Color(200, 50, 50));
            else if (i == 6) 
               weekDaysName[i].setBackground(new Color(50, 100, 200));
            else 
               weekDaysName[i].setBackground(new Color(150, 150, 150));
            weekDaysName[i].setOpaque(true);
            weekDaysName[i].setFocusPainted(false);
            calPanel.add(weekDaysName[i]);   
         }
         for(int i=0 ; i<CAL_HEIGHT ; i++){
            for(int j=0 ; j<CAL_WIDTH ; j++){
               dateButs[i][j]=new JButton();
               dateButs[i][j].setBorderPainted(false);
               dateButs[i][j].setContentAreaFilled(false);
               dateButs[i][j].setBackground(Color.WHITE);
               dateButs[i][j].setOpaque(true);
               dateButs[i][j].addActionListener(lForDateButs);
               calPanel.add(dateButs[i][j]);
            }
         }
         calPanel.setLayout(new GridLayout(0,7,2,2));
         calPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
         showCal(); // �޷��� ǥ��
                  
      infoPanel = new JPanel();
         infoPanel.setLayout(new BorderLayout());
         infoClock = new JLabel("", SwingConstants.RIGHT);
         infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
         infoPanel.add(infoClock, BorderLayout.NORTH);
         selectedDate = new JLabel("<Html><font size=4>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
         selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
               
         infoPanel.add(infoClock, BorderLayout.EAST);
         
         JdbcJtableTest01 SchedulePanel=new JdbcJtableTest01();
         
         memoAreaSP = new JScrollPane(SchedulePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         memoSubPanel=new JPanel();   
         
      //calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
      JPanel frameSubPanelWest = new JPanel();
      Dimension calOpPanelSize = calOpPanel.getPreferredSize();
      calOpPanelSize.height =100;
      calOpPanel.setPreferredSize(calOpPanelSize);
      frameSubPanelWest.setLayout(new BorderLayout());
      frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
      frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

      //infoPanel, memoPanel��  frameSubPanelEast�� ��ġ
      JPanel frameSubPanelEast = new JPanel();
      Dimension infoPanelSize=infoPanel.getPreferredSize();
      infoPanelSize.height = 65;
      infoPanel.setPreferredSize(infoPanelSize);
      frameSubPanelEast.setLayout(new BorderLayout());
      frameSubPanelEast.add(infoPanel,BorderLayout.NORTH);
      frameSubPanelEast.add(SchedulePanel,BorderLayout.CENTER);
      
      Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
      frameSubPanelWestSize.width = 600;
      frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
      
      //�ڴʰ� �߰��� bottom Panel..
      frameBottomPanel = new JPanel();
      frameBottomPanel.add(bottomInfo);
      
      //frame�� ���� ��ġ
      mainFrame.setLayout(new BorderLayout());
      mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
      mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
      mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
      mainFrame.setVisible(true);

      focusToday(); //���� ��¥�� focus�� �� (mainFrame.setVisible(true) ���Ŀ� ��ġ�ؾ���)
   }
   
   
   private void focusToday(){
      if(today.get(Calendar.DAY_OF_WEEK) == 1)
         dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
      else
         dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
   }
   
   
   private void showCal(){
      for(int i=0;i<CAL_HEIGHT;i++){
         for(int j=0;j<CAL_WIDTH;j++){
            String fontColor="black";
            if(j==0) 
               fontColor="red";
            else if(j==6) 
               fontColor="blue";
            
            File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
            if(f.exists()){
               dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
               //dateButs[i][j].setText("<html><b><font color="+"Green"+">"+calDates[i][j]+"</font></b></html>");
            }
            else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

            dateButs[i][j].removeAll();
            if(calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR) &&    calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
               dateButs[i][j].setText("<html><font color="+"Green"+">"+calDates[i][j]+"</font></html>");
            }
            
            
            if(calDates[i][j] == 0) 
               dateButs[i][j].setVisible(false);
            else
               dateButs[i][j].setVisible(true);
         }
      }
   }
   
   
   private class ListenForCalOpButtons implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         if(e.getSource() == todayBut){
            setToday();
            lForDateButs.actionPerformed(e);
            focusToday();
         }
         else if(e.getSource() == lYearBut) moveMonth(-12);
         else if(e.getSource() == lMonBut) moveMonth(-1);
         else if(e.getSource() == nMonBut) moveMonth(1);
         else if(e.getSource() == nYearBut) moveMonth(12);
         
         curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
         showCal();
      }
   }
   
   
   private class listenForDateButs implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         int k=0,l=0;
         for(int i=0 ; i<CAL_HEIGHT ; i++){
            for(int j=0 ; j<CAL_WIDTH ; j++){
               if(e.getSource() == dateButs[i][j]){ 
                  k=i;
                  l=j;
               }
            }
         }
   
         if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������ ���� �κ�

         cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
         
         String dDayString = new String();
         int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
         if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
               && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
               && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
         else if(dDay >=0) dDayString = "D-"+(dDay+1);
         else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);
         
         selectedDate.setText("<Html><font size=3>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");
      }
   }

   public void setMain(MainProcess main) {
      this.main = main;
   }
}