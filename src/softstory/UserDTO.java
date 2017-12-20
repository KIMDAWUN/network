package softstory;

/*
 * UserDTO is used to manage all users currently login
 * get and setter function for id,push
 * id and push are variable value
 */
public class UserDTO {
	String id;
	int push;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public int getPush() {
		return push;
	}

	public void setPush(int push) {
		this.push = push;
	}
}

