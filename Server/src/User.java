
public class User {
	
	private String username;
	private String password;
	private Vote userVote;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Vote getUserVote() {
		return userVote;
	}

	public void setUserVote(Vote userVote) {
		this.userVote = userVote;
	}

	public boolean equals(Object obj){
		if(((User)obj).username.equals(username) && ((User)obj).password.equals(password))
			return true;
		return false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
