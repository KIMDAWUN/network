package softstory;

public class MainProcess {
	   
	   LoginView loginView;
	   MainView mainView;
	   JoiningView joiningView;
	   QuestionView questionView;
	   ScheduleView scheduleView;
	   MessengerView messengerView;
	   SettingView settingView;
	   
	   public static void main(String[] args) {
	      MainProcess main = new MainProcess();
	      main.loginView = new LoginView();
	      main.loginView.setMain(main);
	   }
	   
	   public void showMainView(String ID){
	      loginView.dispose();
	      
	      MainProcess main = new MainProcess();
	      main.mainView = new MainView(ID);
	      main.mainView.setMain(main);
	   }
	   
	   public void showLoginView() {
	      joiningView.dispose();
	   }
	   
	   public void showJoiningView() {
	      MainProcess main = new MainProcess();
	      main.joiningView = new JoiningView();
	      main.joiningView.setMain(main);
	   }
	   
	   public void showQuestion() {
	      MainProcess main = new MainProcess();
	      main.questionView = new QuestionView();
	      main.questionView.setMain(main);
	   }
	   
	   public void exitQuestion() {
	      questionView.dispose();
	   }
	   
	   public void showSchedule() {
	      MainProcess main = new MainProcess();
	      main.scheduleView = new ScheduleView();
	      main.scheduleView.setMain(main);
	   }
	   
	   public void exitSchedule() {
	      //scheduleView.dispose();
	   }
	   
	   public void showMessenger() {
	      MainProcess main = new MainProcess();
	      main.messengerView = new MessengerView();
	      main.messengerView.setMain(main);
	   }
	   
	   public void exitMessenger() {
	      messengerView.dispose();
	   }
	   
	   public void showSetting() {
	      MainProcess main = new MainProcess();
	      main.settingView = new SettingView();
	      main.settingView.setMain(main);
	   }
	   
	   public void exitSetting() {
	      settingView.dispose();
	   }
}