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

/**
 *  @name CalendarDataManager
 *  @content Calculate value of 6*7 array Calendar
 */ 
class CalendarDataManager {
   static final int CAL_WIDTH = 7;
   final static int CAL_HEIGHT = 6;
   int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
   int calYear;
   int calMonth;
   int calDayOfMon;
   final int calLastDateOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
   int calLastDate;
   Calendar today = Calendar.getInstance();
   Calendar cal;

   public CalendarDataManager() {
      setToday();
   }
   
   //Set today's date
   public void setToday() {
      calYear = today.get(Calendar.YEAR);
      calMonth = today.get(Calendar.MONTH);
      calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
      makeCalData(today);
   }

   /**
    * @Function makeCalData
    * @param cal
    * @content Make Calendar 
    */
   private void makeCalData(Calendar cal) {
	   //Find the location of the first day and last day.
      int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
      
      if (calMonth == 1)
         calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
      else
         calLastDate = calLastDateOfMonth[calMonth];
      
      //Initialize Calendar array
      for (int i = 0; i < CAL_HEIGHT; i++) {
         for (int j = 0; j < CAL_WIDTH; j++) {
            calDates[i][j] = 0;
         }
      }

      //Filling the calendar array with values
      for (int i = 0, num = 1, k = 0; i < CAL_HEIGHT; i++) {
         if (i == 0)
            k = calStartingPos;
         else
            k = 0;
         for (int j = k; j < CAL_WIDTH; j++) {
            if (num <= calLastDate)
               calDates[i][j] = num++;
         }
      }
   }
   
  /**
   * @name leapCheck(윤년인지 아닌지)
   * @param year
   * @content Check the year is leap or not 
   * @return 1 or 0
   */
   private int leapCheck(int year) {
      if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
         return 1;
      else
         return 0;
   }

   /**
    * @name moveMonth
    * @param mon
    * @content Creates a calendar array with n months from the current month(1year -> +12 or -12)
    */
   public void moveMonth(int mon) { 
      calMonth += mon;
      if (calMonth > 11)
         while (calMonth > 11) {
            calYear++;
            calMonth -= 12;
         }
      else if (calMonth < 0)
         while (calMonth < 0) {
            calYear--;
            calMonth += 12;
         }
      cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);
      makeCalData(cal);
   }
}

public class ScheduleView extends CalendarDataManager {

   private MainProcess main;

   JFrame mainFrame;

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

   JPanel SchedulePanel;

   private ImageIcon img;
   
   final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };

   public ScheduleView(String ID) {
      img = new ImageIcon("images//background.png");

      Font font = new Font("배달의민족 주아", Font.CENTER_BASELINE, 15);
      Font font2 =new Font("배달의민족 주아", Font.CENTER_BASELINE, 25);
      
      mainFrame = new JFrame("Check your schedule");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setSize(1300, 700);
      mainFrame.setLocationRelativeTo(null);
      mainFrame.setResizable(true);
      
      //Background panel
      JPanel panel = new JPanel() {
         public void paintComponent(Graphics g) {

            g.drawImage(img.getImage(), 0, 0, null);
            setOpaque(false);
            super.paintComponent(g);
         }
      };
      panel.setLayout(null);

      // Today, buttons
      calOpPanel = new JPanel();

      todayBut = new JButton("Today");
      todayBut.setFont(font);
      todayBut.setToolTipText("Today");
      todayBut.addActionListener(lForCalOpButtons);

      todayLab = new JLabel(today.get(Calendar.MONTH) + 1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/"
            + today.get(Calendar.YEAR));
      todayLab.setFont(font2);
      
      lYearBut = new JButton("<<");
      lYearBut.setFont(font);
      lYearBut.setToolTipText("Previous Year");
      lYearBut.addActionListener(lForCalOpButtons);

      lMonBut = new JButton("<");
      lMonBut.setFont(font);
      lMonBut.setToolTipText("Previous Month");
      lMonBut.addActionListener(lForCalOpButtons);

      curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
            + (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
      curMMYYYYLab.setFont(font2);
      
      nMonBut = new JButton(">");
      nMonBut.setFont(font);
      nMonBut.setToolTipText("Next Month");
      nMonBut.addActionListener(lForCalOpButtons);

      nYearBut = new JButton(">>");
      nYearBut.setFont(font);
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
      calOpGC.insets = new Insets(5, 5, 0, 0);
      calOpGC.anchor = GridBagConstraints.WEST;
      calOpGC.fill = GridBagConstraints.NONE;
      calOpPanel.add(todayBut, calOpGC);
      calOpGC.gridwidth = 3;
      calOpGC.gridx = 2;
      calOpGC.gridy = 1;
      calOpPanel.add(todayLab, calOpGC);
      calOpGC.anchor = GridBagConstraints.CENTER;
      calOpGC.gridwidth = 1;
      calOpGC.gridx = 1;
      calOpGC.gridy = 2;
      calOpPanel.add(lYearBut, calOpGC);
      calOpGC.gridwidth = 1;
      calOpGC.gridx = 2;
      calOpGC.gridy = 2;
      calOpPanel.add(lMonBut, calOpGC);
      calOpGC.gridwidth = 2;
      calOpGC.gridx = 3;
      calOpGC.gridy = 2;
      calOpPanel.add(curMMYYYYLab, calOpGC);
      calOpGC.gridwidth = 1;
      calOpGC.gridx = 5;
      calOpGC.gridy = 2;
      calOpPanel.add(nMonBut, calOpGC);
      calOpGC.gridwidth = 1;
      calOpGC.gridx = 6;
      calOpGC.gridy = 2;
      calOpPanel.add(nYearBut, calOpGC);
      
      // Panel showing calendar
      calPanel = new JPanel();
      weekDaysName = new JButton[7];
      for (int i = 0; i < CAL_WIDTH; i++) {
         weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
         weekDaysName[i].setBorderPainted(false);
         weekDaysName[i].setContentAreaFilled(false);
         weekDaysName[i].setForeground(Color.WHITE);

         if (i == 0) 
         {
            weekDaysName[i].setBackground(new Color(200, 50, 50));
            weekDaysName[i].setFont(font);
         }
         else if (i == 6)
         {
            weekDaysName[i].setBackground(new Color(50, 100, 200));
            weekDaysName[i].setFont(font);
         }
            
         else
         {
            weekDaysName[i].setBackground(new Color(150, 150, 150));
            weekDaysName[i].setFont(font);
         }
         
         weekDaysName[i].setOpaque(true);
         weekDaysName[i].setFocusPainted(false);
         calPanel.add(weekDaysName[i]);
      }

      for (int i = 0; i < CAL_HEIGHT; i++) {
         for (int j = 0; j < CAL_WIDTH; j++) {
            dateButs[i][j] = new JButton();
            dateButs[i][j].setBorderPainted(false);
            dateButs[i][j].setContentAreaFilled(false);
            dateButs[i][j].setBackground(Color.WHITE);
            dateButs[i][j].setOpaque(true);
            dateButs[i][j].addActionListener(lForDateButs);
            dateButs[i][j].setFont(font);
            calPanel.add(dateButs[i][j]);
         }
      }
      calPanel.setLayout(new GridLayout(0, 7, 2, 2));
      calPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 0, 1));
      showCal(); // Show calendar

      //Create schedule panel
      JdbcJtableTest01 SchedulePanel = new JdbcJtableTest01(ID);

      calOpPanel.setBounds(0, 0, 600, 100);
      calOpPanel.setOpaque(false);
      panel.add(calOpPanel);

      calPanel.setBounds(0, 100, 600, 550);
      calPanel.setOpaque(false);
      panel.add(calPanel);

      SchedulePanel.setBounds(650, 100, 600, 600);
      SchedulePanel.setOpaque(false);
      panel.add(SchedulePanel);

      JButton btnReturn = new JButton(new ImageIcon("images//back.png"));
      btnReturn.setBorderPainted(false);
      btnReturn.setFocusPainted(false);
      btnReturn.setContentAreaFilled(false);
      btnReturn.setBounds(1200, 600, 60, 60);

      panel.add(btnReturn);
      btnReturn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            main.exitSchedule();
         }
      });

      mainFrame.add(panel);
      mainFrame.setLocation(300, 100);
      mainFrame.setVisible(true);

      focusToday(); //Focus on current date
   }
   
   /**
    * @name focusToday
    * @content Focusing on current date
    */
   private void focusToday() {
      if (today.get(Calendar.DAY_OF_WEEK) == 1)
         dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
      else
         dateButs[today.get(Calendar.WEEK_OF_MONTH) - 1][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
   }

   /**
    * @name showCal
    * @content Show calendar
    */
   private void showCal() {
      for (int i = 0; i < CAL_HEIGHT; i++) {
         for (int j = 0; j < CAL_WIDTH; j++) {
            String fontColor = "black";
            if (j == 0)
               fontColor = "red";
            else if (j == 6)
               fontColor = "blue";

            File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
                  + (calDates[i][j] < 10 ? "0" : "") + calDates[i][j] + ".txt");
            if (f.exists()) {
               dateButs[i][j]
                     .setText("<html><b><font color=" + fontColor + ">" + calDates[i][j] + "</font></b></html>");
            } else
               dateButs[i][j].setText("<html><font color=" + fontColor + ">" + calDates[i][j] + "</font></html>");

            dateButs[i][j].removeAll();
            if (calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR)
                  && calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
               dateButs[i][j].setText("<html><font color=" + "Green" + ">" + calDates[i][j] + "</font></html>");
            }

            if (calDates[i][j] == 0)
               dateButs[i][j].setVisible(false);
            else
               dateButs[i][j].setVisible(true);
         }
      }
   }
   
   //Set buttons
   private class ListenForCalOpButtons implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == todayBut) {
            setToday();
            lForDateButs.actionPerformed(e);
            focusToday();
         } else if (e.getSource() == lYearBut)
            moveMonth(-12);
         else if (e.getSource() == lMonBut)
            moveMonth(-1);
         else if (e.getSource() == nMonBut)
            moveMonth(1);
         else if (e.getSource() == nYearBut)
            moveMonth(12);

         curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
               + (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
         showCal();
      }
   }
   
   private class listenForDateButs implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int k = 0, l = 0;
         for (int i = 0; i < CAL_HEIGHT; i++) {
            for (int j = 0; j < CAL_WIDTH; j++) {
               if (e.getSource() == dateButs[i][j]) {
                  k = i;
                  l = j;
               }
            }
         }

         if (!(k == 0 && l == 0))
            calDayOfMon = calDates[k][l]; //If push today buttons, Perform this actionPerformed function

         //Get current year,month,day
         cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);
      }
   }
   
   /**
    * @name setMain
    * @param main
    * @content Take objects created in Mainprocess over
    */
   public void setMain(MainProcess main) {
      this.main = main;
   }

   /**
    * @name dispose
    * @content Close the window
    */
   public void dispose() {
      // TODO Auto-generated method stub
      mainFrame.dispose();
   }
}