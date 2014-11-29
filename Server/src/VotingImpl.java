import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VotingImpl extends UnicastRemoteObject implements VotingInterface {

	public boolean isVotingOpen;
	public List<User> users = new ArrayList<User>();
	public List<User> votes = new ArrayList<User>();
	public User admin = new User("admin", "adminpass");
	public Options options = new Options();
	
	public VotingImpl() throws RemoteException {
		super();
		addUsers();
		isVotingOpen=true;
	}
	
	public void addUsers(){
		users.add(new User("blake", "blakepass"));
		users.add(new User("tom", "tompass"));
		users.add(new User("mike", "mikepass"));
		users.add(admin);
	}
	
	public boolean isAdmin(String username, String password){
		if(username.equals("admin")  && password.equals("adminpass"))
			return true;
		return false;
	}
	
	public boolean validateUser(String username, String password){
		if (users.contains(new User(username, password)))
			return true;
		return false;
	}
	
	public boolean castVote(String username, String password, String choice){
		if(!isVotingOpen)
			return false;
		User checkUser = new User(username, password);
		if(votes.contains(checkUser)){
			for(int i=0; i<votes.size(); i++){
				if(votes.get(i).getUsername().equals(username)){
					votes.remove(i);
				}
			}
		}
		checkUser.setUserVote(new Vote(choice));
		votes.add(checkUser);
		return true;
	}
}
