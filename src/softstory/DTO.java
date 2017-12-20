package softstory;

/*
 * DTO is used to manage all users who joined SoftStory 
 * get and setter function for id,password,name,studentNo,year,egg,language,course,push
 */
public class DTO {

	String id;
	String password;
	String name;
	String studentNo;
	String year;
	int egg = 0;
	String language;
	String course;
	int push;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getEgg() {
		return egg;
	}
	public void setEgg(int egg) {
		this.egg = egg;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setPush(int push) {
		this.push=push;
	}
	public int getpush() {
		return push;
	}
	
	
}

